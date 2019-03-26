package com.aashrai.db.dao;

import com.aashrai.api.Inventory;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;

@AllArgsConstructor
public class InventoryDao {

    private final MongoCollection inventories;

    private Inventory getInventory(ObjectId inventoryId) {
        return inventories.findOne(inventoryId).as(Inventory.class);
    }

    public Inventory getInventory(String inventoryId) {
        return getInventory(new ObjectId(inventoryId));
    }

    /**
     * "Write then read" approach for solving race condition in decrementing stock by two parallel API calls,
     * Stock is only decremented if the current stock count is greater than zero, the client can throw an error
     * if decrement stock is a NO-OP
     */
    private Boolean decrementStock(ObjectId inventoryId) {
        return inventories.update("{ _id:#, stock:{ $gt: 0 }}", inventoryId)
                .with("{ $inc: { stock: -1 }}")
                .getN() > 0;
    }

    public Boolean decrementStock(String inventoryId) {
        return decrementStock(new ObjectId(inventoryId));
    }

    public void incrementStock(String inventoryId) {
        inventories.update("{ _id:# }", inventoryId)
                .with("{ $inc: { stock: 1 }}");
    }
}
