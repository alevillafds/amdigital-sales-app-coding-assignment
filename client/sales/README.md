# sales-client

This is a client application for sales tracking.

## Pre-requisites

* Python 3.8
* Flask 1.1.2
* protobuf 4.21.6
* gunicorn 20.0.4



## Code Organization

```
sales
	-- data
	-- proto
	-- sales_proto_definition
	-- templates
	api.py
	app.py
	config.py
	Dockerfile
	requirements.txt
	sale_service.py
```

* data: folder that contains all messages that client sends.
* proto: folder that contains sales proto definition.
* sales_proto_definition: folder that contains generated python code for grpc.
* templates: folder that contains html templates for Flask.
* api.py: Wrapper of python server grpc code.
* app.py: Flask main app
* config.py: Manage environment variables
* Dockerfile: To dockerize app
* requirements.txt: File with all dependencies
* sale_service.py: Code to get all json data from data folder and convert to grpc message.

## Features

* Send streaming Sales messages with grpc.
* Receive Statistic message with grpc.

## Paths

localhost:5000

* Send sales: /sales
* Show statistics: /statistics

## Development

Generate grpc code:

```
python -m grpc_tools.protoc -I./proto --python_out=sales_proto_definition --grpc_python_out=sales_proto_definition ./proto/sales.proto
```

Run app:

```
python gunicorn -w 4 app:app -b 0.0.0.0:5000
```

