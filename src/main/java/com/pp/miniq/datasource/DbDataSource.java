package com.pp.miniq.datasource;

import java.util.Calendar;
import java.util.List;

import org.skife.jdbi.v2.DBI;

import com.pp.miniq.core.Message;
import com.pp.miniq.db.MessageDAO;

public class DbDataSource implements IDataSource {

    private DBI jdbi;

    public DbDataSource(DBI jdbi) {
        super();
        this.jdbi = jdbi;
    }

    public List<Message> getAllMessages() {
        MessageDAO msgDao = jdbi.onDemand(MessageDAO.class);
        return msgDao.retrieveAll();
    }

    public void send(Message msg) {
        MessageDAO msgDao = jdbi.onDemand(MessageDAO.class);
        msg.setDescInBytes();
        msgDao.insert(msg);
        msg.setTimeStamp(Calendar.getInstance().getTime());

    }

    /**
     * 
     * @param id
     */
    public void deleteMsg(String id) {
        MessageDAO msgDao = jdbi.onDemand(MessageDAO.class);
        Message existingMsg = msgDao.retrieve(id);

        if (existingMsg == null) {
            throw new RuntimeException("No message exists with given id : " + id);
        }
        msgDao.delete(id);
    }

    public Message acknowledgeMsg(String id) {
        MessageDAO msgDao = jdbi.onDemand(MessageDAO.class);
        Message existingMsg = msgDao.retrieve(id);

        if (existingMsg == null) {
            throw new RuntimeException("No message exists with given id : " + id);
        }
        msgDao.delete(id);
        msgDao.insertAcks(existingMsg);
        return existingMsg;
    }

    public void updateTs(Message msg) {
        MessageDAO msgDao = jdbi.onDemand(MessageDAO.class);
        msg.setDescInBytes();
        msgDao.update(msg.getTimeStamp(), msg.getId());
    }
}
