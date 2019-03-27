db.accounts.drop()
db.inventories.drop()

db.accounts.insert({"name": "Peter Griffin", "address": "Spooner Street", "email": "pumpkineater@gmail.com"});
db.accounts.insert({"name": "Glenn Quagmire", "address": "Spooner Street", "email": "glenn@gmail.com"});

db.inventories.insert({"name" : "42-Inch TV", "stock" : 3, "cost" : 300})
db.inventories.insert({"name" : "Refrigerator", "stock" : 3, "cost" : 600})