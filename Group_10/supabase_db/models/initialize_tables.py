# import os
# from supabase import create_client, Client
# from dotenv import load_dotenv

# # Load environment variables from .env file in the root directory
# load_dotenv(dotenv_path='../../.env')

# # Get Supabase URL and key from environment variables
# supabase_url = os.getenv('SUPABASE_URL')
# supabase_key = os.getenv('SUPABASE_KEY')

# # Create a Supabase client
# supabase: Client = create_client(supabase_url, supabase_key)

# # Function to drop tables if they exist (for clean setup)
# def drop_tables():
#     drop_statement = """
#     DROP TABLE IF EXISTS reactions;
#     DROP TABLE IF EXISTS posts;
#     DROP TABLE IF EXISTS topics;
#     DROP TABLE IF EXISTS disaster_reports;
#     """
#     response = supabase.rpc("execute_sql", {"sql": drop_statement}).execute()
#     if response.data is None:
#         print(f"Error dropping tables: {response.get('error', 'Unknown error')}")
#     else:
#         print("Tables dropped successfully")

# # Function to create tables
# def create_tables():
#     table_collection = """
#     -- Table 1: Topics
#     CREATE TABLE IF NOT EXISTS topics (
#         topic_id SERIAL PRIMARY KEY,
#         user_id SERIAL REFERENCES useraccounts(userid) ON DELETE SET NULL,
#         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
#         title TEXT NOT NULL,
#         description TEXT
#     );

#     -- Table 2: Posts
#     CREATE TABLE IF NOT EXISTS posts (
#         post_id SERIAL PRIMARY KEY,
#         user_id SERIAL REFERENCES useraccounts(userid) ON DELETE SET NULL,
#         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
#         content TEXT NOT NULL,
#         image_url TEXT,
#         topic_id INT REFERENCES topics(topic_id) ON DELETE CASCADE
#     );

#     -- Table 3: Reactions
#     CREATE TABLE IF NOT EXISTS reactions (
#         reaction_id SERIAL PRIMARY KEY,
#         user_id SERIAL REFERENCES useraccounts(userid) ON DELETE SET NULL,
#         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
#         emoji_type INT,
#         post_id INT REFERENCES posts(post_id) ON DELETE CASCADE,
#         UNIQUE(user_id, post_id)  -- Ensures each user can react only once per post
#     );

#     -- Table 4: Disaster Reports
#     CREATE TABLE IF NOT EXISTS disaster_reports (
#         report_id UUID PRIMARY KEY,
#         user_id SERIAL NOT NULL REFERENCES useraccounts(userid),
#         timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
#         latitude DECIMAL(9, 6) NOT NULL,
#         longitude DECIMAL(9, 6) NOT NULL,
#         address VARCHAR(255) NOT NULL,
#         weather_event_type VARCHAR(50) NOT NULL,
#         weather_event_severity VARCHAR(20) CHECK (weather_event_severity IN ('moderate', 'severe', 'extreme')),
#         weather_event_description TEXT NOT NULL,
#         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
#     );
#     """
#     response = supabase.rpc("execute_sql", {"sql": table_collection}).execute()
#     if response.data is None:
#         print(f"Error creating tables: {response.get('error', 'Unknown error')}")
#     else:
#         print("Tables created successfully")

# # Drop and recreate tables
# #drop_tables()
# create_tables()
