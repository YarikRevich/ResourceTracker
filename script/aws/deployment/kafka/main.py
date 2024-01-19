import os
from flask import request
from flask import Flask

app = Flask(__name__)

@app.route("/")
def info():
    """Represents informational endpoint."""

    return "ResourceTracker Kafka deployment server!"

@app.route("/deploy", methods=["POST"])
def deploy():
    """Represents endpoint used for Kafka deployment."""
   
    host = request.args.get('host')

    os.spawnl(os.P_DETACH, f'KRAFT_CONTAINER_HOST_NAME={host} bash /opt/docker-entrypoint.sh')

    return "", 201

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=8080)