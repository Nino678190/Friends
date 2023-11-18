from flask import Flask, request, send_from_directory

app = Flask(__name__)

@app.route("/<path:path>", methods=["GET","POST"])
def success(path):
    return send_from_directory(".",path)


@app.route("/api/<path>", methods=["GET","POST"])
def api(path):#
    filename='test.json'
    datei = open(filename,'r')
    print(datei.read())
    return send_from_directory(".", filename),200
