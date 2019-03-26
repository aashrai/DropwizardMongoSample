package com.aashrai.db.dao;

import com.aashrai.api.Inventory;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

public class InventoryDao {

    private final MongoCollection inventories;

    public InventoryDao(MongoCollection inventories) {
        this.inventories = inventories;
    }

    public Inventory getInventory(ObjectId inventoryId) {
        return inventories.findOne(inventoryId).as(Inventory.class);
    }

    public Boolean decrementStock(ObjectId inventoryId) {
        return inventories.update("{ _id:#, stock:{ $gt: 0 }}", inventoryId)
                .with("{ $inc: { stock: -1 }}")
                .getN() > 0;
    }
}
