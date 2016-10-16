package com.pp.miniq.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class MiniQConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory               dataSourceFactory = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty("messageQueue")
    private MiniQCustomConfigurationFactory miniQConfigFactory      = new MiniQCustomConfigurationFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    @JsonProperty("messageQueue")
    public MiniQCustomConfigurationFactory getMinQConfigFactory() {
        return miniQConfigFactory;
    }
}
