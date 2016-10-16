package com.pp.miniq.websvc;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.pp.miniq.action.Action;
import com.pp.miniq.action.ActionType;
import com.pp.miniq.action.ActionsFactory;
import com.pp.miniq.core.Message;
import com.pp.miniq.res.MiniQClient;

@Path("/message")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MiniQWebservice {
    private final DBI         jdbi;
    private final MiniQClient miniQClient;

    public MiniQWebservice(DBI jdbi, MiniQClient miniQClient) {
        this.jdbi = jdbi;
        this.miniQClient = miniQClient;
    }

    @GET
    public Response getAll() {
        Action action = ActionsFactory.getAction(ActionType.CONSUMER, jdbi, miniQClient);
        return action.getAllMessages();
    }

    @POST
    public Response post(@Valid Message msg) {
        Action action = ActionsFactory.getAction(ActionType.PRODUCER, jdbi, miniQClient);
        return action.createNewMessage(msg);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id) {
        Action action = ActionsFactory.getAction(ActionType.CONSUMER, jdbi, miniQClient);
        return action.acknowledgeMsg(id);
    }
}
