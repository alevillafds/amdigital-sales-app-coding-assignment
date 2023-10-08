import os

BACKEND_PORT = int(os.getenv("BACKEND_PORT", "9090"))
BACKEND_HOST = os.getenv("BACKEND_HOST", "localhost")
