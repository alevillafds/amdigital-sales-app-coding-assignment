# amdigital-sales-app-coding-assignment

This is an application for sales tracking.

## Motivation

This application has been created as part of the selection process of a company to demonstrate technical skills.

* Demonstrate how to solve it.
* Alternatives evaluated.
* Alternatives discarded.
* Assumptions been made.

## The Problem

This is a copy of the original problem text provided by the company:

You have been asked to develop an application that allows to keep track of the items sold in a store. This application
will receive messages that contain the information of the items sold and will provide an API for clients to query the
statistics of the sales.

A message will contain the following information:

```json
{
    "item": "apple",
	"quantity": 15,
    "price": 2.5,
	"date": "2020-03-20T01:30:08.180856"
}
```

You will have to develop a client application that simulates the sending of messages to the server. The client will read
the messages from a folder. The messages are provided to you in JSON format in this repository under the folder `data`.

The server will provide an endpoint to query the statistics of the sales. For each item sold you have to provide the
total quantity sold, the average quantity per sale and the total revenue. Moreover, you have to provide the same
statistics for each month. This is an example of the expected output (partial):

```
{
    "apple": {
        "total_quantity": 600,
        "average_per_sale": 2,
        "total_revenue": 234534,
        "monthly": {
            "2023-01": {
                "total_quantity": 8,
                "average_per_sale": 2,
                "total_revenue": 234534,
            }
            ...
        }
    },
    "banana": {
        "total_quantity": 400,
        "average_per_sale": 6,
        "total_revenue": 456870,
        "monthly": {
            ...
        }
}
```

## Analysis of the problem

### Assumptions

* Chose open-source technologies.
* Chose technologies with more familiarization because of time, but explain other alternatives.
* Chose the most simple solution because of time, but explain a more professional and enterprise solution.
* No-optimized solution.
* Data saved in a database in real time.
* No batch solution.
* No streaming solution.
* On-Premise. 

### Data architecture

In this problem there are two types of data:

**Sales**

There are sales messages that are sent from the client side repetitively and that must be inserted into the database in real time. This type of data fits into an OLTP system. A database optimized for insertion should be used.

The OLTP system is typically composed of a relational or non-relational database. The types of queries are usually not very complex, frequent and involve a small amount of data.

For this example application where the data is well structured and there is not much quantity, it could be done with relational databases. If the problem scaled to a very large amount of data and taking into account that there are no relationships, a non-relational approach would be better. Relational databases which could be used are PostgresSQL or MariaDB, for example. As for non-relational databases, Apache Cassandra or MongoDB would also be good alternatives as there are no relationships. In this case, Apache Cassandra will be selected because of having greater knowledge about it. 



**Statistics of the sales**

On the other hand, there are statistical messages of historical and two-dimensional summary that involve non complex database queries. It is needed to group the data by item and date. This type of operation is analytical but not very complex, so a OLTP database fits well too. In case of data evolving to more than two dimensions queries it would need a more complex OLAP oriented solution.

For this type of result an optimized database should be used for querying. In this case, there are different solutions: 

* Optimize the way raw data is stored for statistical queries. This is the simplest form and can be used for less complex data. This is the solution that will be developed for this problem.
* Store the raw sales data in a database and execute a batch process on top of a database to perform the statistical aggregations. In this case there would be a day delay to show the results. This is the best solution for an enterprise-level solution with lots of data and that doesn't require real time statistics.
* Store the raw sales data in a database and calculate the statistics for the current month in real time using streaming solution. On the other hand, a batch solution on top of a database performs statistical aggregations required and stores them in another database. With this approach, results would be obtained in "real time" but involve more infrastructure, more cost and more complexity. This is the most suitable solution for a scalable enterprise-level solution that requires real time statistics.

For simplicity, because is selected for raw sales data and allows the required queries to be carried out, Apache Cassandra will be selected. The option of using a processing engine on top of a database is what would be used in a professional solution, such as Apache Spark batch processing on top of Cassandra or Apache Flink streaming on top of Cassandra.

### Solutions Architecture

Because of time consumption to solve this problem in a real-enterprise way, the final architecture might be simplified. Anyway, a theorical analysis will be done for a full solution.

#### Enterprise Solution

This is a overall agnostic diagram from a complete Enterprise Solution:

![architecture_diagram-entreprise solution](https://github.com/alevillafds/amdigital-sales-app-coding-assignment/assets/53422023/d5030c78-7946-4a6e-a51c-8d92e353ed14)


* Real-Time System for report in real time based on a streaming solution (Windowed aggregations).
* Historic System with one-day delay as a batch process for huge aggregations an statistics (all table aggregations, lots of records involved yearly-monthly).
* Raw data System of sales optimized for INSERT operation and scalable.

#### Problem Solution

This is a overall diagram from a reduced solution to solve the problem:

![architecture_diagram-Simple Solution](https://github.com/alevillafds/amdigital-sales-app-coding-assignment/assets/53422023/a651a05b-9c56-4939-b8c2-302fe9103072)


* Database to INSERT data optimized to generate report in real time by server queries.

### Data Modeling

The design of the database will be done for a NoSQL type database, specifically for the chosen technology, which is Apache Cassandra. In this case there is a single entity involved. The design will be done in two phases: conceptual design and physical design. The logical design will be ignored due to the simplicity of the problem.

The conceptual design is independent of the type of database. Entities are represented by rectangles and their attributes by ovals. The following figure shows the sales entity that has the attributes item, quantity, price and date:

![architecture_diagram-data_conceptual](https://github.com/alevillafds/amdigital-sales-app-coding-assignment/assets/53422023/dbbcf2d6-ad21-4937-a27d-75f06730b4cb)


The physical design is the conceptual design once adapted to the Apache Cassandra table design along with the type that each column will have and the keyspace in which it will be. The correct way to define a table in Cassandra is taking into account the queries that are going to be made. In this case we have two queries:

```
Select the total sales amount, average sales and total revenue for each item.
```

```
Select the total amount of sales, average sales and total income for each item grouped by month.
```

For this specific case, the optimal thing would be to have two tables for the total aggregations and for the monthly aggregations. For simplicity, a single optimized table will be made for monthly aggregations:

![architecture_diagram-data_physical](https://github.com/alevillafds/amdigital-sales-app-coding-assignment/assets/53422023/d92b07f4-95b6-4ffd-938f-44ab3d299c95)


The table will be in a keyspace called amdigital. It will have a Simple Partition Key (K) and two simple grouping keys (C). In Cassandra the Partition Key corresponds to the WHERE clause and the grouping keys to the GROUP BY clause. In the sales table the data will be distributed according to the item and in each partition the data will be ordered according to the month and these in turn according to the exact date.

### Server Design

TO-DO



### Client Design

TO-DO

## Repository organization

It is needed to organize all the project in one public repository for simplification so i decided to organized the repository based in a **monorepo** with the following structure:

```
* amdigital-sales-app-coding-assignment
	-- client
	-- server
	-- infra
	-- docs
```

* client: folder for the client application.
* server: folder for the server application.
* infra: folder for the IaC code.
* docs: folder for the documentation.

## Tech/framework used

* Backend server: Java 11 + Spring Framework
* Frontend client: Python 3.8 + Flask
* Database: Apache Cassandra 3.11
* Infrastructure: Docker containers

## Features

Client side:

* Simulate sending messages of the items sold to the server from a local folder.
* Show statistics of the sales.
* Messages are in JSON format.
* Communication with server using gRPC.

Server side:

* Endpoint to save items sold in Apache Cassandra database.
* Endpoint to send statistics of the sales from Apache Cassandra database.

## How to use?

TO-DO: Step-by-Step how to deploy all.

## References

Monorepo: https://monorepo.tools/#what-is-a-monorepo
