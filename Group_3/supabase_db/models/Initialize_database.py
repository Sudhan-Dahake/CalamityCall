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
        notifdate DATE NOT NULL,
        preparationsteps TEXT,
        activesteps TEXT,
        recoverysteps TEXT
    );

    -- Table 2: preferences
    CREATE TABLE IF NOT EXISTS preferences (
        preferenceid SERIAL PRIMARY KEY
    );

    -- Table 3: notification_on
    CREATE TABLE IF NOT EXISTS notification_on (
        notification_on_id SERIAL PRIMARY KEY,
        preferenceid INTEGER REFERENCES preferences(preferenceid),
        watch BOOLEAN NOT NULL,
        warning BOOLEAN NOT NULL,
        urgent BOOLEAN NOT NULL,
        critical BOOLEAN NOT NULL
    );

    -- Table 4: flashing
    CREATE TABLE IF NOT EXISTS flashing (
        flashingid SERIAL PRIMARY KEY,
        preferenceid INTEGER REFERENCES preferences(preferenceid),
        watch BOOLEAN NOT NULL,
        warning BOOLEAN NOT NULL,
        urgent BOOLEAN NOT NULL,
        critical BOOLEAN NOT NULL
    );

    -- Table 5: noise
    CREATE TABLE IF NOT EXISTS noise (
        noiseid SERIAL PRIMARY KEY,
        preferenceid INTEGER REFERENCES preferences(preferenceid),
        watch BOOLEAN NOT NULL,
        warning BOOLEAN NOT NULL,
        urgent BOOLEAN NOT NULL,
        critical BOOLEAN NOT NULL
    );

    -- Table 6: notification_alert_type
    CREATE TABLE IF NOT EXISTS notification_alert_type (
        notificationalerttypeid SERIAL PRIMARY KEY,
        preferenceid INTEGER REFERENCES preferences(preferenceid),
        watch VARCHAR(10) CHECK (watch IN ('Push', 'Pop-up')) NOT NULL,
        warning VARCHAR(10) CHECK (warning IN ('Push', 'Pop-up')) NOT NULL,
        urgent VARCHAR(10) CHECK (urgent IN ('Push', 'Pop-up')) NOT NULL,
        critical VARCHAR(10) CHECK (critical IN ('Push', 'Pop-up')) NOT NULL
    );

    -- Table 7: text_to_speech
    CREATE TABLE IF NOT EXISTS text_to_speech (
        texttospeechid SERIAL PRIMARY KEY,
        preferenceid INTEGER REFERENCES preferences(preferenceid),
        watch BOOLEAN NOT NULL,
        warning BOOLEAN NOT NULL,
        urgent BOOLEAN NOT NULL,
        critical BOOLEAN NOT NULL
    );

    -- Table 8: useraccounts
    CREATE TABLE IF NOT EXISTS useraccounts (
        userid SERIAL PRIMARY KEY,
        username VARCHAR(30) NOT NULL UNIQUE,
        password VARCHAR(30) NOT NULL,
        preferenceid INTEGER REFERENCES preferences(preferenceid),
        age INTEGER NOT NULL,
        address TEXT NOT NULL,
        zip_code TEXT NOT NULL,
        city TEXT NOT NULL
    );

    -- Table 9: FirebaseTokenStorage
    CREATE TABLE IF NOT EXISTS firebasetokenstorage (
        tokenid uuid PRIMARY KEY DEFAULT gen_random_uuid(),
        fcmtoken TEXT NOT NULL,
        userid INTEGER REFERENCES useraccounts(userid) ON DELETE SET NULL,
        deviceid TEXT NOT NULL,
        notificationtype TEXT DEFAULT 'push',
        created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
        updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
        UNIQUE(userid, deviceid)
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
