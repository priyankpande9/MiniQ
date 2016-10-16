package com.pp.miniq.action;

import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.pp.miniq.MiniQBroker;
import com.pp.miniq.core.Message;
import com.pp.miniq.datasource.DbDataSource;
import com.pp.miniq.res.MiniQClient;

public class ProducerAction extends Action {

    public ProducerAction(DBI jdbi, MiniQClient miniQClient) {
        super(jdbi, miniQClient);
    }

    @Override
    public Response createNewMessage(Message msg) {
        try {
            msg.setId(generateUniqueMsgId());

            MiniQBroker broker = MiniQBroker.getInstance(miniQClient);
            DbDataSource dataSource = new DbDataSource(jdbi);

            broker.send(msg, dataSource);
            return Response.status(Response.Status.OK).entity(msg).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

}
