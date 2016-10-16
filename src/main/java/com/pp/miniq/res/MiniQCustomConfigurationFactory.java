package com.pp.miniq.res;

import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MiniQCustomConfigurationFactory {

    private int timeout;

    @JsonProperty
    public int getTimeout() {
        return timeout;
    }

    @JsonProperty
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public MiniQClient build(Environment environment) {
        final MiniQClient client = new MiniQClient(getTimeout());
        environment.lifecycle().manage(new Managed() {
            public void stop() throws Exception {
            }

            public void start() throws Exception {
                client.close();

            }
        });

        return client;
    }
}
