package com.pp.miniq;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.skife.jdbi.v2.DBI;

import com.pp.miniq.res.MiniQClient;
import com.pp.miniq.res.MiniQConfiguration;
import com.pp.miniq.scheduler.MessageNotificationTimeoutManager;
import com.pp.miniq.websvc.MiniQWebservice;

public class MiniQApplication extends Application<MiniQConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MiniQApplication().run(args);
    }

    @Override
    public String getName() {
        return "MiniQ";
    }

    @Override
    public void initialize(final Bootstrap<MiniQConfiguration> bootstrap) {
    }

    @Override
    public void run(final MiniQConfiguration configuration, final Environment environment) {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        MiniQClient miniQClient = configuration.getMinQConfigFactory().build(environment);

        environment.jersey().register(new MiniQWebservice(jdbi, miniQClient));

        // scheduler to check received messages timeout
        ScheduledExecutorService ses = environment.lifecycle().scheduledExecutorService("", true).build();
        Runnable task = new MessageNotificationTimeoutManager(jdbi, miniQClient);
        ses.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }
}
