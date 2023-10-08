from flask import Flask, render_template
from pathlib import Path
import glob

import config
from api import ApiClient
import sales_proto_definition.sales_pb2 as sales__pb2
from sale_service import sale_files2iterator

app = Flask(__name__)

app.config["api"] = ApiClient(f"{config.BACKEND_HOST}:{config.BACKEND_PORT}")


@app.route("/statistics")
def statistics():
    api = app.config["api"]
    return render_template("statistics.html", statistics=api.getStatistic())


@app.route("/sales")
def sales():
    api = app.config["api"]
    result = list(Path("./data").rglob("*.[jJ][sS][oO][nN]"))
    result = api.importSales(sales=sale_files2iterator())
    return render_template("sales.html", sales=result)
