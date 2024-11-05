# This will be a wrapper for the UserModel class.
# The purpose of this is to introduce extra security stuff like buffer overflow checks, type checks, etc.
import os
from supabase import create_client, Client
from dotenv import load_dotenv
from ...supabase_db import UserModel

load_dotenv()

class UserServices:
    def __init__(self, userModelObj: UserModel):
        self.userModelObj = userModelObj

    def __VerifyPassword(self, plainPassword: str, hashedPassword: str):     # This hashed password is retrieved from the database, but currently hasn't been implemented. So it is infact a plain password
        if (plainPassword == hashedPassword):
            return True

        return False

    def AuthenticateUser(self, username: str, password: str):
        user = self.userModelObj.GetUser(username=username)

        if not user:
            return False

        elif not self.__VerifyPassword(password=password, hashedPassword=user["password"]):
            return False

        return True