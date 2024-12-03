import sys
import os
import pytest
from fastapi.testclient import TestClient
from datetime import datetime, timezone

sys.path.append(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))))

from Api.main import app
from Api.schemas.discussion import TopicCreate, PostCreate

client = TestClient(app)

@pytest.fixture
def sample_topic():
    return {
        "title": "Test Topic",
        "description": "This is a test topic",
        "user_id": 1
    }

@pytest.fixture
def sample_post():
    return {
        "content": "This is a test post",
        "user_id": 1,
        "topic_id": 1
    }

def test_create_topic(sample_topic):
    response = client.post("/topics/", json=sample_topic)
    assert response.status_code == 200
    assert "topic_id" in response.json()

def test_read_topic():
    # First, create a topic
    create_response = client.post("/topics/", json={"title": "Read Test Topic", "description": "Test", "user_id": 1})
    assert create_response.status_code == 200
    topic_id = create_response.json()["topic_id"]

    # Now, read the topic
    response = client.get(f"/topics/{topic_id}")
    assert response.status_code == 200
    assert response.json()["title"] == "Read Test Topic"

def test_create_topic_invalid_data():
    invalid_topic = {"title": "", "description": "Test", "user_id": "not_an_integer"}
    response = client.post("/topics/", json=invalid_topic)
    assert response.status_code == 422

def test_read_nonexistent_topic():
    response = client.get("/topics/99999")  # Assuming this ID doesn't exist
    assert response.status_code == 404

# Edge Test Cases
def test_create_topic_with_very_long_title():
    long_title = "A" * 1000  # Very long title
    long_title_topic = {
        "title": long_title,
        "description": "This is a test topic with a very long title",
        "user_id": 1
    }
    response = client.post("/topics/", json=long_title_topic)
    assert response.status_code == 422  # Assuming there's a title length limit

def test_create_topic_with_special_characters():
    special_char_topic = {
        "title": "!@#$%^&*()_+{}|:<>?",
        "description": "This topic has special characters in the title",
        "user_id": 1
    }
    response = client.post("/topics/", json=special_char_topic)
    assert response.status_code == 200

def test_read_topic_with_non_integer_id():
    response = client.get("/topics/not_an_integer")
    assert response.status_code == 422  # Assuming the API validates the ID type