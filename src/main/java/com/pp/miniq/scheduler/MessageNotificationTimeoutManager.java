package com.pp.miniq.scheduler;

import org.skife.jdbi.v2.DBI;

import com.pp.miniq.MiniQBroker;
import com.pp.miniq.res.MiniQClient;

public class MessageNotificationTimeoutManager implements Runnable {

    private MiniQClient miniQClient;
    private DBI         jdbi;

    public MessageNotificationTimeoutManager(DBI jdbi, MiniQClient miniQClient) {
        this.jdbi = jdbi;
        this.miniQClient = miniQClient;

    }

    public void run() {
        try {
            MiniQBroker broker = MiniQBroker.getInstance(miniQClient);
            broker.handleTimeout(jdbi);
        } catch (Throwable t) {
        }
    }

}
