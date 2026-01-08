from fastapi import FastAPI
from .routes import router

app = FastAPI(title="Android restraunt application")
app.include_router(router)
