from pathlib import Path
import os
import json
import sales_proto_definition.sales_pb2 as sales__pb2


def sale_files2iterator():
    result = list(Path("./data").rglob("*.[jJ][sS][oO][nN]"))

    for f in result:
        with open(f) as json_file:
            sale = sale_json2message_mapping(json.load(json_file))
            yield sale

    return sale


def sale_json2message_mapping(json_data):
    return sales__pb2.Sale(
        item=json_data["item"],
        quantity=json_data["quantity"],
        price=json_data["price"],
        date=json_data["date"],
    )
