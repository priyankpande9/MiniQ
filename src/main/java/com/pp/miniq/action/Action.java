package com.pp.miniq.action;

import java.util.UUID;

import javax.ws.rs.core.Response;

import org.skife.jdbi.v2.DBI;

import com.pp.miniq.core.Message;
import com.pp.miniq.res.MiniQClient;

public class Action {

    protected DBI         jdbi;
    protected MiniQClient miniQClient;

    public Action(DBI jdbi, MiniQClient miniQClient) {
        this.jdbi = jdbi;
        this.miniQClient = miniQClient;
    }

    /**
     * @return the jdbi
     */
    public DBI getJdbi() {
        return jdbi;
    }

    /**
     * @param jdbi the jdbi to set
     */
    public void setJdbi(DBI jdbi) {
        this.jdbi = jdbi;
    }

    /**
     * @return the miniQClient
     */
    public MiniQClient getMiniQClient() {
        return miniQClient;
    }

    /**
     * @param miniQClient the miniQClient to set
     */
    public void setMiniQClient(MiniQClient miniQClient) {
        this.miniQClient = miniQClient;
    }

    public Response getAllMessages() {
        return null;
    }

    public Response createNewMessage(Message msg) {
        return null;
    }

    public Response acknowledgeMsg(String id) {
        return null;
    }

    public String generateUniqueMsgId() {
        return UUID.randomUUID().toString();
    }
}
