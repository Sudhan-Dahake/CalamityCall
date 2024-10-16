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
    -- Table 1: Notification
    CREATE TABLE IF NOT EXISTS Notification (
        NotifID SERIAL PRIMARY KEY,
        NotifOrigin VARCHAR(30) NOT NULL,
        Longitude FLOAT NOT NULL,
        Latitude FLOAT NOT NULL,
        City VARCHAR(30) NOT NULL,
        DisasterType VARCHAR(30) NOT NULL,
        DisasterLevel INTEGER NOT NULL,
        NotifDate DATE NOT NULL
    );

    -- Table 2: Preferences
    CREATE TABLE IF NOT EXISTS Preferences (
        PreferenceID SERIAL PRIMARY KEY,
        NotificationType VARCHAR(30) NOT NULL,
        DisasterType VARCHAR(30) NOT NULL,
        SeverityType INTEGER NOT NULL,
        NotifFlashing BOOLEAN NOT NULL,
        TextToSpeech BOOLEAN NOT NULL,
        NotifTimeFrame VARCHAR(30) NOT NULL
    );

    -- Table 3: Credentials
    CREATE TABLE IF NOT EXISTS Credentials (
        UserID SERIAL PRIMARY KEY,
        Username VARCHAR(30) NOT NULL,
        Password VARCHAR(30) NOT NULL,
        PreferenceID INTEGER REFERENCES Preferences(PreferenceID)
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
