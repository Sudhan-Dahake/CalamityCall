import sys
import os
import pytest
from fastapi.testclient import TestClient
from datetime import datetime, timezone

sys.path.append(os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))))

from Api.main import app
from Api.schemas.forum_schemas import TopicCreateRequest, PostCreateRequest

client = TestClient(app)

@pytest.fixture
def sample_topic():
    return {
        "user_id": 1,
        "title": "Test Forum Topic",
        "description": "This is a test forum topic"
    }

@pytest.fixture
def sample_post():
    return {
        "user_id": 1,
        "topic_id": 1,
        "content": "This is a test forum post",
        "image_url": "http://example.com/image.jpg"
    }

def test_create_topic(sample_topic):
    response = client.post("/topics", json=sample_topic)
    assert response.status_code == 200
    assert "topic_id" in response.json()

def test_create_post(sample_post):
    response = client.post("/posts", json=sample_post)
    assert response.status_code == 200
    assert "post_id" in response.json()

def test_read_topics():
    response = client.get("/topics")
    assert response.status_code == 200
    assert isinstance(response.json(), list)

def test_read_posts():
    response = client.get("/posts")
    assert response.status_code == 200
    assert isinstance(response.json(), list)

def test_read_topics_by_user():
    response = client.get("/topics?user_id=1")
    assert response.status_code == 200
    assert isinstance(response.json(), list)

def test_read_posts_by_topic():
    response = client.get("/posts?topic_id=1")
    assert response.status_code == 200
    assert isinstance(response.json(), list)

def test_create_topic_invalid_data():
    invalid_topic = {"user_id": "not_an_integer", "title": "", "description": "Test"}
    response = client.post("/topics", json=invalid_topic)
    assert response.status_code == 422

def test_create_post_invalid_data():
    invalid_post = {"user_id": "not_an_integer", "topic_id": "not_an_integer", "content": ""}
    response = client.post("/posts", json=invalid_post)
    assert response.status_code == 422

# Edge Test Cases
def test_create_topic_with_empty_description():
    empty_desc_topic = {
        "user_id": 1,
        "title": "Empty Description Topic",
        "description": ""
    }
    response = client.post("/topics", json=empty_desc_topic)
    assert response.status_code == 200  # Assuming empty descriptions are allowed

def test_create_post_with_very_long_content():
    long_content = "A" * 100000  # Very long content
    long_content_post = {
        "user_id": 1,
        "topic_id": 1,
        "content": long_content,
        "image_url": "http://example.com/image.jpg"
    }
    response = client.post("/posts", json=long_content_post)
    assert response.status_code == 422  # Assuming there's a content length limit

def test_read_topics_with_negative_user_id():
    response = client.get("/topics?user_id=-1")
    assert response.status_code == 422  # Assuming negative user IDs are not allowed

def test_read_posts_with_non_existent_topic_id():
    response = client.get("/posts?topic_id=99999")  # Assuming this topic doesn't exist
    assert response.status_code == 200
    assert len(response.json()) == 0  # Should return an empty list