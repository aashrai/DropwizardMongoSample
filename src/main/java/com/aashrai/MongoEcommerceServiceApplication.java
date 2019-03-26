package com.aashrai;

import com.meltmedia.dropwizard.mongo.MongoBundle;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jongo.Jongo;

public class MongoEcommerceServiceApplication extends Application<MongoEcommerceServiceConfiguration> {

    MongoBundle<MongoEcommerceServiceConfiguration> mongoBundle;

    public static void main(final String[] args) throws Exception {
        new MongoEcommerceServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "MongoEcommerceService";
    }

    @Override
    public void initialize(final Bootstrap<MongoEcommerceServiceConfiguration> bootstrap) {
        mongoBundle = MongoBundle.<MongoEcommerceServiceConfiguration>builder()
                .withConfiguration(MongoEcommerceServiceConfiguration::getMongoDBConfiguration)
                .build();
        bootstrap.addBundle(mongoBundle);
    }

    @Override
    public void run(final MongoEcommerceServiceConfiguration configuration,
                    final Environment environment) {
        MongoClient client = mongoBundle.getClient();
        DB db = mongoBundle.getDB();
        Jongo jongo = new Jongo(db);

    }

}
