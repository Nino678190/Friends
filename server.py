from flask import Flask, request, send_from_directory

app = Flask(__name__)


@app.route("/<path:path>", methods=["GET", "POST"])
def success(path):
    return send_from_directory(".", path)


@app.route("/api/<path>", methods=["GET", "POST"])
def api(path):
    print(request.__dict__)
    return "", 200


@app.after_request
def after_request(response):
    response.headers.add("Access-Control-Allow-Origin", "*")
    response.headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization")
    response.headers.add(
        "Access-Control-Allow-Methods", "GET, POST, PATCH, DELETE, OPTIONS"
    )
    return response
