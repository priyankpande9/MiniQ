package com.pp.miniq.action;

import java.util.List;

import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.pp.miniq.MiniQBroker;
import com.pp.miniq.core.Message;
import com.pp.miniq.datasource.DbDataSource;
import com.pp.miniq.res.MiniQClient;

public class ConsumerAction extends Action {
    public ConsumerAction(DBI jdbi, MiniQClient miniQClient) {
        super(jdbi, miniQClient);
    }

    @Override
    public Response getAllMessages() {
        try {
            MiniQBroker broker = MiniQBroker.getInstance(miniQClient);
            DbDataSource dataSource = new DbDataSource(jdbi);

            List<Message> msgList = broker.receiveAll(dataSource);

            return Response.status(Response.Status.OK).entity(msgList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response acknowledgeMsg(String id) {
        try {
            MiniQBroker broker = MiniQBroker.getInstance(miniQClient);
            DbDataSource dataSource = new DbDataSource(jdbi);

            String msg = broker.acknowledgeMsg(dataSource, id);

            return Response.status(Response.Status.OK).entity(msg).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
