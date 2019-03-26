package com.aashrai;

import com.aashrai.client.OrderClient;
import com.aashrai.core.OrderExceptionMapper;
import com.aashrai.db.dao.AccountDao;
import com.aashrai.db.dao.InventoryDao;
import com.aashrai.db.dao.OrderDao;
import com.aashrai.db.dao.OrderInfoDao;
import com.aashrai.resources.OrderRes;
import com.meltmedia.dropwizard.mongo.MongoBundle;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jongo.Jongo;

public class MongoEcommerceServiceApplication extends Application<MongoEcommerceServiceConfiguration> {

    private MongoBundle<MongoEcommerceServiceConfiguration> mongoBundle;

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
        Jongo jongo = new Jongo(mongoBundle.getDB());
        environment.jersey().register(new OrderExceptionMapper());

        InventoryDao inventoryDao = new InventoryDao(jongo.getCollection("inventories"));
        AccountDao accountDao = new AccountDao(jongo.getCollection("accounts"));
        OrderDao orderDao = new OrderDao(jongo.getCollection("orders"));
        OrderInfoDao orderInfoDao = new OrderInfoDao(jongo.getCollection("orderInfos"));
        OrderClient orderClient = new OrderClient(orderDao, accountDao, inventoryDao, orderInfoDao);
        environment.jersey().register(new OrderRes(orderClient));
    }
}
