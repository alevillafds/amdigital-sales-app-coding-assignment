CREATE KEYSPACE IF NOT EXISTS amdigital WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE IF NOT EXISTS amdigital.sales (month timestamp, item text, quantity int,price double,date timestamp,PRIMARY KEY (item, month, date))   WITH CLUSTERING ORDER BY (month ASC, date ASC);

INSERT INTO amdigital.sales (month, item, quantity, price, date) VALUES (1601510400000, 'apple', 2, 3.4, 1601546400000);
INSERT INTO amdigital.sales (month, item, quantity, price, date) VALUES (1601510400000, 'apple', 2, 3.4, 1601546410000);
INSERT INTO amdigital.sales (month, item, quantity, price, date) VALUES (1601510400000, 'apple', 2, 3.4, 1601546420000);
INSERT INTO amdigital.sales (month, item, quantity, price, date) VALUES (1601510400000, 'apple', 2, 3.4, 1601546430000);
INSERT INTO amdigital.sales (month, item, quantity, price, date) VALUES (1601510400000, 'orange', 2, 3.4, 1601546400000);
INSERT INTO amdigital.sales (month, item, quantity, price, date) VALUES (1601510400000, 'orange', 2, 3.4, 1601546410000);
INSERT INTO amdigital.sales (month, item, quantity, price, date) VALUES (1601510400000, 'banana', 2, 3.4, 1601546420000);
INSERT INTO amdigital.sales (month, item, quantity, price, date) VALUES (1601510400000, 'avocado', 2, 3.4, 1601546430000);