echo "Setting up mongo DB data"
mongo ecomm setup.js

echo "Downloading dependencies"
mvn dependency:go-offline

echo "Starting application"
mvn clean install
java -jar target/mongo-ecom-1.0.0-SNAPSHOT.jar server config.yml