from fastapi import FastAPI
from .routers import Notifications
from .routers import Preferences
from .routers import Users
from .routers import Auth
from .routers import ProtectedJWTVerificiation
from .routers import Token
from .routers import Alerts
from .routers import FirebaseToken

app = FastAPI()

# including the routers for each module
app.include_router(Alerts.router, prefix="/alerts", tags=["Alerts"])
app.include_router(Notifications.router, prefix="/notifications", tags=["Notifications"])
app.include_router(Preferences.router, prefix="/preferences", tags=["Preferences"])
app.include_router(Users.router, prefix="/users", tags=["Users"])
app.include_router(Auth.router, prefix="/auth", tags=["Authentication"])
app.include_router(Token.router, prefix="/generate", tags=["GenerateJWT"])
app.include_router(ProtectedJWTVerificiation.router, prefix="/protected", tags=["ProtectedJWTVerification"])
app.include_router(FirebaseToken.router, prefix="/firebase", tags=["Firebase"])