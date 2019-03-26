package com.aashrai.db.dao;

import com.aashrai.api.Inventory;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

public class InventoryDao {

    private final MongoCollection inventories;

    public InventoryDao(MongoCollection inventories) {
        this.inventories = inventories;
    }

    private Inventory getInventory(ObjectId inventoryId) {
        return inventories.findOne(inventoryId).as(Inventory.class);
    }

    public Inventory getInventory(String inventoryId) {
        return getInventory(new ObjectId(inventoryId));
    }

    private Boolean decrementStock(ObjectId inventoryId) {
        return inventories.update("{ _id:#, stock:{ $gt: 0 }}", inventoryId)
                .with("{ $inc: { stock: -1 }}")
                .getN() > 0;
    }

    public Boolean decrementStock(String inventoryId) {
        return decrementStock(new ObjectId(inventoryId));
    }
}
