package com.pp.miniq.action;

import org.skife.jdbi.v2.DBI;

import com.pp.miniq.res.MiniQClient;

public abstract class ActionsFactory {

    public static Action getAction(ActionType type, DBI jdbi, MiniQClient miniQClient) {
        if (type == ActionType.PRODUCER) {
            return new ProducerAction(jdbi, miniQClient);
        } else if (type == ActionType.CONSUMER) {
            return new ConsumerAction(jdbi, miniQClient);
        }
        return null;
    }
}
