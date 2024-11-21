import os
import sys
from datetime import datetime, timedelta, timezone
import jwt

sys.path.append(os.path.dirname(os.path.dirname(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))))))

from Group_3 import AuthService

def test_login_user():
    auth_service = AuthService()
    response = auth_service.LoginUser(
        username="Sudhan1221", password="ActuallySecure")

    # Check that both access and refresh tokens are returned
    assert "access_token" in response, "Access token missing in response"
    assert "refresh_token" in response, "Refresh token missing in response"
    assert response["token_type"] == "bearer", "Unexpected token type"
    print("LoginUser test passed. Tokens received:", response)


def test_create_access_token():
    auth_service = AuthService()
    data = {"sub": "test_user"}
    token = auth_service.CreateAccessToken(data=data)

    # Decode token to check its structure
    decoded = jwt.decode(token, auth_service.JWT_SECRET_KEY,
                         algorithms=[auth_service.ALGORITHM])
    assert decoded["sub"] == "test_user", "Incorrect subject in token"
    assert "exp" in decoded, "Expiration claim missing in token"
    print("CreateAccessToken test passed. Token created:", token)


def test_create_refresh_token():
    # Initialize AuthService
    auth_service = AuthService()

    # Data to encode in the token
    data = {"sub": "test_user"}

    # Generate refresh token
    refresh_token = auth_service.CreateRefreshToken(data=data)

    # Decode the token to validate its structure
    decoded_token = jwt.decode(
        refresh_token, auth_service.REFRESH_SECRET_KEY, algorithms=[
            auth_service.ALGORITHM]
    )

    # Assertions
    assert decoded_token["sub"] == "test_user", "Subject does not match"
    assert "exp" in decoded_token, "Expiration claim missing"

    # Verify the expiration time
    expected_expire_time = datetime.now(
        timezone.utc) + timedelta(days=auth_service.REFRESH_TOKEN_EXPIRE_DAYS)
    actual_expire_time = datetime.fromtimestamp(
        decoded_token["exp"], timezone.utc)

    # Allow a small margin of error in the expiration time due to processing time
    assert abs((expected_expire_time - actual_expire_time).total_seconds()
               ) < 5, "Expiration time mismatch"

    print("CreateRefreshToken test passed. Token:", refresh_token)



def test_verify_jwt():
    auth_service = AuthService()
    data = {"sub": "test_user"}
    token = auth_service.CreateAccessToken(data=data)

    # Manually setting token for testing
    def oauth2_scheme(): return token  # Mock the Depends function to return the token
    verified_user = auth_service.VerifyJWT(token=oauth2_scheme())

    assert verified_user == "test_user", "User verification failed"
    print("VerifyJWT test passed. User verified:", verified_user)


def test_refresh_token():
    auth_service = AuthService()
    data = {"sub": "test_user"}
    refresh_token = auth_service.CreateRefreshToken(data=data)

    # Use refresh token to generate a new access token
    response = auth_service.RefreshToken(refresh_token=refresh_token)

    assert "access_token" in response, "Access token not in refresh response"
    assert response["token_type"] == "bearer", "Unexpected token type in response"
    print("RefreshToken test passed. New access token generated:", response)
