# sales-server

This is a server application for sales tracking.

## Pre-requisites

* Java 11
* Maven 3.9.5
* Spring Framework 2.7.16
* Apache Cassandra 3.10 (GROUP BY clause)



## Package Organization

```
sales
	-- src/main/java
		-- SalesApplication.java
		-- dao
		-- model
		-- service
		-- stream
		-- utils
```

* SalesApplication.java: Main App
* dao: Repository interfaces to manage Cassandra queries.
* model: Object that represent Cassandra table or result queries.
* service: Grpc service.
* stream: Implementation manage streaming messages.
* utils: Miscellaneous classes. 

## Features

* Receive streaming Sales messages with grpc.
* Send Statistic message with grpc.

## Development

Generate grpc code:

```
mvn clean package
```

Run test:

```
mvn clean test
```

Generate docker image:

```
mvn clean package docker:build
```

