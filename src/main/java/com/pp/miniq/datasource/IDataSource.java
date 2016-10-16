package com.pp.miniq.datasource;

import java.util.List;

import com.pp.miniq.core.Message;

public interface IDataSource {

    public List<Message> getAllMessages();

    public void send(Message msg);
}
