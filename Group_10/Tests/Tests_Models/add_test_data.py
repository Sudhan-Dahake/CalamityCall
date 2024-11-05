import os
from supabase import create_client, Client
from dotenv import load_dotenv
import random

# Load environment variables for Supabase
load_dotenv()

supabase_url = os.getenv("SUPABASE_URL")
supabase_key = os.getenv("SUPABASE_KEY")
supabase: Client = create_client(supabase_url, supabase_key)

def add_test_data():
    # Step 1: Retrieve an existing user ID
    user_response = supabase.from_("useraccounts").select("userid").limit(1).execute()
    if user_response.data:
        user_id = user_response.data[0]['userid']
        print(f"Using user ID: {user_id}")
    else:
        print("No users found in useraccounts. Please add a user first.")
        return

    # Step 2: Add a topic with user ID
    topic_data = {
        "user_id": user_id,  # Link topic to user ID
        "title": "Flood Reports",
        "description": "Discussions about recent floods and related updates."
    }
    topic_response = supabase.from_("topics").insert(topic_data).execute()

    if topic_response.data:
        topic_id = topic_response.data[0]['topic_id']
        print(f"Added topic with ID: {topic_id}")
    else:
        print("Error adding topic:", topic_response.error)
        return

    # Step 3: Add a post linked to the topic and user
    post_data = {
        "user_id": user_id,  # Link post to user ID
        "content": "Flooding reported near the river banks. Roads are inaccessible.",
       
        "topic_id": topic_id
    }
    post_response = supabase.from_("posts").insert(post_data).execute()

    if post_response.data:
        post_id = post_response.data[0]['post_id']
        print(f"Added post with ID: {post_id}")
    else:
        print("Error adding post:", post_response.error)
        return

    # Step 4: Add a reaction (no changes here)
    reaction_data = {
        "emoji_type": random.randint(1, 5),  # Random emoji type between 1 and 5
        "post_id": post_id
    }
    reaction_response = supabase.from_("reactions").insert(reaction_data).execute()

    if reaction_response.data:
        print(f"Added reaction to post ID: {post_id}")
    else:
        print("Error adding reaction:", reaction_response.error)

# Run the function to add test data
add_test_data()