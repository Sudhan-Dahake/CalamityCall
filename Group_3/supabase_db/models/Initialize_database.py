import os
from supabase import create_client, Client
from dotenv import load_dotenv

# This loads the supabase URL and API key from .env file.
load_dotenv()

# Secret values loaded into the variables.
supabase_url = os.getenv("SUPABASE_URL")
supabase_key = os.getenv("SUPABASE_KEY")

# Initializing the Supabase client
supabase: Client = create_client(supabase_url, supabase_key)


# creating tables
def CreateTables():
    table_Collection = """
    -- Table 1: notifications
    CREATE TABLE IF NOT EXISTS notifications (
        notifid SERIAL PRIMARY KEY,
        notiforigin VARCHAR(30) NOT NULL,
        longitude FLOAT NOT NULL,
        latitude FLOAT NOT NULL,
        city VARCHAR(30) NOT NULL,
        disastertype VARCHAR(30) NOT NULL,
        disasterlevel INTEGER NOT NULL,
        notifdate DATE NOT NULL
    );

    -- Table 2: preferences
    CREATE TABLE IF NOT EXISTS preferences (
        preferenceid SERIAL PRIMARY KEY,
        notificationtype VARCHAR(30) NOT NULL,
        disastertype VARCHAR(30) NOT NULL,
        severitytype INTEGER NOT NULL,
        notifflashing BOOLEAN NOT NULL,
        texttospeech BOOLEAN NOT NULL,
        notiftimeframe VARCHAR(30) NOT NULL
    );

    -- Table 3: useraccounts
    CREATE TABLE IF NOT EXISTS useraccounts (
        userid SERIAL PRIMARY KEY,
        username VARCHAR(30) NOT NULL,
        password VARCHAR(30) NOT NULL,
        preferenceid INTEGER REFERENCES preferences(preferenceid),
        age INTEGER NOT NULL,
        address TEXT NOT NULL,
        zip_code TEXT NOT NULL,
        city TEXT NOT NULL
    );
    """

    response = supabase.rpc('execute_sql', {'sql': table_Collection}).execute()

    if response.data is None:
        print(
            f"Error creating tables: {response.get('error', 'Unknown error')}")
    else:
        print("Tables created successfully")


# calling the function to create tables
CreateTables()
