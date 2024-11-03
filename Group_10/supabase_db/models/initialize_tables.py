import os
from supabase import create_client, Client
from dotenv import load_dotenv

# Load Supabase URL and API key from .env file
load_dotenv()

supabase_url = os.getenv("SUPABASE_URL")
supabase_key = os.getenv("SUPABASE_KEY")

# Initialize the Supabase client
supabase: Client = create_client(supabase_url, supabase_key)

# Function to create tables
def CreateTables():
    table_Collection = """
    -- Table 1: Topics
    CREATE TABLE IF NOT EXISTS topics (
        topic_id SERIAL PRIMARY KEY,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        title TEXT NOT NULL,
        description TEXT
    );

    -- Table 2: Posts
    CREATE TABLE IF NOT EXISTS posts (
        post_id SERIAL PRIMARY KEY,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        content TEXT NOT NULL,
        image_url TEXT,
        topic_id INT REFERENCES topics(topic_id) ON DELETE CASCADE
    );

    -- Table 3: Reactions
    CREATE TABLE IF NOT EXISTS reactions (
        reaction_id SERIAL PRIMARY KEY,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        emoji_type INT NOT NULL,
        post_id INT REFERENCES posts(post_id) ON DELETE CASCADE
    );
    """

    response = supabase.rpc('execute_sql', {'sql': table_Collection}).execute()

    if response.data is None:
        print(f"Error creating tables: {response.get('error', 'Unknown error')}")
    else:
        print("Tables created successfully")

# Call the function to create tables
CreateTables()
