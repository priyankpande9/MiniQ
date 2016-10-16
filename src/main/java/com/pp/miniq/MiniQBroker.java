package com.pp.miniq;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.skife.jdbi.v2.DBI;

import com.pp.miniq.core.Message;
import com.pp.miniq.datasource.DbDataSource;
import com.pp.miniq.res.MiniQClient;

public class MiniQBroker {
    private volatile static MiniQBroker  _instance;
    private static int                   capacity = 1024;

    private LinkedBlockingQueue<Message> queue;
    private Map<String, Message>         receviedMessageMap;

    private MiniQClient                  client;

    public MiniQBroker(int capacity, MiniQClient client) {
        queue = new LinkedBlockingQueue<Message>(capacity);
        receviedMessageMap = new ConcurrentHashMap<String, Message>();
        this.client = client;
    }

    public void put(Message data) throws InterruptedException {
        this.queue.put(data);
    }

    public Message get(Message msg) throws InterruptedException {
        return this.queue.poll(1, TimeUnit.SECONDS);
    }

    public static MiniQBroker getInstance(MiniQClient client) {
        if (_instance == null) {
            synchronized (MiniQBroker.class) {
                if (_instance == null) {
                    _instance = new MiniQBroker(capacity, client);
                }
            }
        }

        return _instance;
    }

    public synchronized void send(Message msg, DbDataSource dataSource) {
        queue.add(msg);
        dataSource.send(msg);
    }

    public synchronized List<Message> receiveAll(DbDataSource dataSource) {
        List<Message> allMsgs = dataSource.getAllMessages();
        for (Message msg : allMsgs) {
            queue.remove(msg);
            receviedMessageMap.put(msg.getId(), msg);
        }
        return allMsgs;
    }

    public synchronized String acknowledgeMsg(DbDataSource dataSource, String id) {
        String status = "Failed";
        try {
            if (receviedMessageMap != null && receviedMessageMap.containsKey(id)) {
                Message msg = dataSource.acknowledgeMsg(id);
                status = "Success";
                receviedMessageMap.remove(msg.getId());
            } else {
                status = "Acknowledgement timed out!!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{\"status\" : \"" + status + "\"}";
    }

    public synchronized void handleTimeout(DBI jdbi) {

        Iterator<Entry<String, Message>> itr = receviedMessageMap.entrySet().iterator();
        System.out.println("Handling timeout");
        while (itr.hasNext()) {
            Entry<String, Message> entry = itr.next();
            Message msg = entry.getValue();

            Calendar cal = Calendar.getInstance();
            cal.setTime(msg.getTimeStamp());

            long duration = (System.currentTimeMillis() - cal.getTimeInMillis()) / 1000;
            System.out.println("Removing msg for others :  " + msg.getId() + ", duration = " + duration
                    + ", timeout = " + client.timeout);

            // Message which are not acknowledged within a specific timeout will
            // be added back to the queue
            if (duration > client.timeout) {
                receviedMessageMap.remove(msg.getId());
                System.out.println("msg removed");
                if (!queue.contains(msg)) {
                    // needs time stamp updation here
                    System.out.println("Adding back to the queue");

                    msg.setTimeStamp(Calendar.getInstance().getTime());
                    DbDataSource dataSource = new DbDataSource(jdbi);
                    dataSource.updateTs(msg);

                    queue.add(msg);
                }
            }
        }
    }
}
