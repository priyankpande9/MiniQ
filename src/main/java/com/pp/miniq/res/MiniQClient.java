package com.pp.miniq.res;

import io.dropwizard.lifecycle.Managed;

public class MiniQClient implements Managed {

    public int timeout;

    public MiniQClient(int timeout) {
        this.timeout = timeout;
    }

    public void close() {
        // TODO Auto-generated method stub

    }

    public void start() throws Exception {
        // TODO Auto-generated method stub

    }

    public void stop() throws Exception {
        // TODO Auto-generated method stub

    }

}
