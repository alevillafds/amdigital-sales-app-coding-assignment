from flask import Flask, render_template

import configuration
from api import ApiClient

app = Flask(__name__)

app.config["api"] = ApiClient(
    f"{configuration.BACKEND_HOST}:{configuration.BACKEND_PORT}"
)


@app.route("/")
def home():
    api = app.config["api"]
    return render_template("index.html", name=api.getStatistic())
