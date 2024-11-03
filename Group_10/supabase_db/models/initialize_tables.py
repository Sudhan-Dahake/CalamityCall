import os
from supabase import create_client, Client
from dotenv import load_dotenv

# Change the working directory to the root directory
os.chdir('..\\..\\..')
# Load Supabase URL and API key from .env file
load_dotenv()

# Print to verify
print("Supabase URL:", os.getenv('SUPABASE_URL'))
print("Supabase Key:", os.getenv('SUPABASE_KEY'))

# Get Supabase URL and key from environment variables
supabase_url = os.getenv('SUPABASE_URL')
supabase_key = os.getenv('SUPABASE_KEY')

# Create a Supabase client
supabase: Client = create_client(supabase_url, supabase_key)

    # Function to create tables
def create_tables():
        table_collection = """
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

        response = supabase.rpc('execute_sql', {'sql': table_collection}).execute()

        if response.data is None:
            print(f"Error creating tables: {response.get('error', 'Unknown error')}")
        else:
            print("Tables created successfully")

    # Call the function to create tables
create_tables()




# supabase_url = "https://xihorjyukkfakwrdajkb.supabase.co"
# supabase_key = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InhpaG9yanl1a2tmYWt3cmRhamtiIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTcyODU3NDQzOCwiZXhwIjoyMDQ0MTUwNDM4fQ.fAtnbZ6lTvRVi5q5CtzCCvB1hFf5L8k3QygKziCLk-Y"