package com.aashrai;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meltmedia.dropwizard.mongo.MongoConfiguration;
import io.dropwizard.Configuration;

public class MongoEcommerceServiceConfiguration extends Configuration {

    @JsonProperty
    MongoConfiguration mongoDBConfiguration;

    public MongoConfiguration getMongoDBConfiguration() {
        return mongoDBConfiguration;
    }

    public void setMongoDBConfiguration(MongoConfiguration mongoDBConfiguration) {
        this.mongoDBConfiguration = mongoDBConfiguration;
    }
}
