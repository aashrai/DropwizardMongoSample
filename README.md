# Mongo E-commerce Application

## Stack Used
1. Dropwizard
2. Jongo
3. Dropwizard-Mongo bundle
4. Lombok

## Handling race conditions
I have used a **"Write Then Read"** approach for handling race conditions, the idea is to decrement the stock of an inventory first only if its current stock count is greater than zero. Then the number of modified documents is checked to verify if the stock actually got decremented, if no document got updated an exception is thrown.

Since updates to a document in MongoDB is atomic parallel decrements to stock is handled safely.

## Prerequisites
1. Java 1.8 installed
2. MongoDB installed and running locally at port **27017**

## Running The Application
```sh setup.sh```

1. Adds some mock data to the inventories and accounts collections
2. Installs dependencies
3. Builds the JAR and runs the application at **8080**

**NOTE:** The API is located in ```OrderRes.java``` and can be the entrypoint for exploring the codebase.
