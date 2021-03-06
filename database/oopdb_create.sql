DROP DATABASE IF EXISTS oopdb;
DROP TABLESPACE IF EXISTS oopspace;
DROP ROLE IF EXISTS oopdb;

CREATE USER oopdb WITH PASSWORD 'ut2021';

CREATE TABLESPACE oopspace
    OWNER postgres
    LOCATION '/data/oopspace';

CREATE DATABASE oopdb
    WITH OWNER postgres
    TEMPLATE template0
    TABLESPACE oopspace
    ENCODING 'UTF8'
    LC_COLLATE 'en_US.UTF-8'
    LC_CTYPE 'en_US.UTF-8';

-- This line is necessary to run the following commands on the right database. If you remove it, things break.
-- This will trip up pgsanity, so ignore the error it throws.
\connect oopdb

-- Create tables and indexes in our tablespace for easier resetting if needed.
SET default_tablespace = 'oopspace';

-- In all honesty, I have no idea what this does, but pg_dump added it so I will too. <3
SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Set default owner of schema to postgres.
CREATE SCHEMA todoapp AUTHORIZATION postgres;
CREATE SCHEMA todoapp_history AUTHORIZATION postgres;
CREATE SCHEMA todoapp_private AUTHORIZATION postgres;

-- Import pgcrypto to generate UUIDs on insert.
-- Added to public schema, so use public.gen_random_uuid() to generate UUIDs.
CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;
COMMENT ON EXTENSION "pgcrypto" IS 'cryptographic functions';

-- Sensitive user info is stored in todoapp_private to maintain separation.
CREATE TABLE todoapp.users (
    uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    name text NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    avatar text
);

CREATE TABLE todoapp.settings_users (
    uuid_user uuid NOT NULL,
    notify_email_noncritical boolean DEFAULT false NOT NULL,
    FOREIGN KEY (uuid_user) REFERENCES todoapp.users (uuid) ON DELETE CASCADE ON UPDATE CASCADE
);
COMMENT ON COLUMN todoapp.settings_users.notify_email_noncritical IS 'Global override for noncritical email.';

-- Create some way to keep email in sync with GitHub / other OAUTH methods?
CREATE TABLE todoapp_private.users (
    uuid_user uuid NOT NULL,
    FOREIGN KEY (uuid_user) REFERENCES todoapp.users (uuid) ON DELETE CASCADE ON UPDATE CASCADE,
    email text,
    github_id text
);

-- Create a table for all tags which can be added to a tasks. Can also be thought of as categories.
CREATE TABLE todoapp.tags (
    uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    name text NOT NULL,
    color character(8) NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    creator uuid,
    FOREIGN KEY (creator) REFERENCES todoapp.users (uuid) ON DELETE SET NULL ON UPDATE CASCADE
);

-- Essentially the stages of kanban boards.
CREATE TABLE todoapp.states (
    uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    title text NOT NULL,
    created_at timestamp with time zone NOT NULL
);

-- Self explanatory, stores basic task information. Historical changes stored separately.
CREATE TABLE todoapp.tasks (
    uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    title text NOT NULL,
    start_at timestamp with time zone NOT NULL,
    end_at timestamp with time zone NOT NULL,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    creator uuid NOT NULL,
    FOREIGN KEY (creator) REFERENCES todoapp.users (uuid) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE todoapp.tasks_states (
    uuid_task uuid NOT NULL,
    FOREIGN KEY (uuid_task) REFERENCES todoapp.tasks (uuid),
    uuid_state uuid NOT NULL,
    FOREIGN KEY (uuid_state) REFERENCES todoapp.states (uuid)
);

CREATE TABLE todoapp.tasks_tags (
    uuid_task uuid NOT NULL,
    FOREIGN KEY (uuid_task) REFERENCES todoapp.tasks (uuid),
    uuid_tag uuid NOT NULL,
    FOREIGN KEY (uuid_tag) REFERENCES todoapp.tags (uuid)
);

CREATE TABLE todoapp.discussions (
    uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    content text NOT NULL,
    created_at timestamp(0) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    creator uuid,
    FOREIGN KEY (creator) REFERENCES todoapp.users (uuid),
    parent_task uuid,
    FOREIGN KEY (parent_task) REFERENCES todoapp.tasks (uuid),
    parent_discussion uuid,
    FOREIGN KEY (parent_discussion) REFERENCES todoapp.discussions (uuid)
);

CREATE TABLE todoapp.followers_tasks (
    uuid_task uuid NOT NULL,
    FOREIGN KEY (uuid_task) REFERENCES todoapp.tasks (uuid),
    uuid_user uuid NOT NULL,
    FOREIGN KEY (uuid_user) REFERENCES todoapp.users (uuid),

    notify_email_like boolean DEFAULT false NOT NULL,
    notify_email_discussion boolean DEFAULT false NOT NULL,
    notify_email_following boolean DEFAULT false NOT NULL
);

CREATE TABLE todoapp.followers_discussions (
    uuid_user uuid NOT NULL PRIMARY KEY,
    uuid_discussion uuid NOT NULL,
    notify_email_like boolean DEFAULT false NOT NULL,
    notify_email_discussion boolean DEFAULT false NOT NULL,
    notify_email_following boolean DEFAULT false NOT NULL,
    FOREIGN KEY (uuid_user) REFERENCES todoapp.users (uuid),
    FOREIGN KEY (uuid_discussion) REFERENCES todoapp.discussions (uuid)
);

-- This has to be created before any tables in the history tables are created, otherwise the script breaks.
CREATE TYPE todoapp_history.update_type AS ENUM (
    'INSERT',
    'UPDATE',
    'DELETE'
);

ALTER TYPE todoapp_history.update_type OWNER TO postgres;

CREATE TABLE todoapp_history.users (
    _entry_uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    _entry_type todoapp_history.update_type NOT NULL,
    _entry_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
)
INHERITS (todoapp.users);

CREATE TABLE todoapp_history.tags (
    _entry_uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    _entry_type todoapp_history.update_type NOT NULL,
    _entry_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
)
INHERITS (todoapp.tags);

CREATE TABLE todoapp_history.states (
    _entry_uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    _entry_type todoapp_history.update_type NOT NULL,
    _entry_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
)
INHERITS (todoapp.states);

CREATE TABLE todoapp_history.tasks (
    _entry_uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    _entry_type todoapp_history.update_type NOT NULL,
    _entry_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
)
INHERITS (todoapp.tasks);

CREATE TABLE todoapp_history.tasks_tags (
    _entry_uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    _entry_type todoapp_history.update_type NOT NULL,
    _entry_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
)
INHERITS (todoapp.tasks_tags);

CREATE TABLE todoapp_history.tasks_states (
    _entry_uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    _entry_type todoapp_history.update_type NOT NULL,
    _entry_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
)
INHERITS (todoapp.tasks_states);

CREATE TABLE todoapp_history.discussions (
    _entry_uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    _entry_type todoapp_history.update_type NOT NULL,
    _entry_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
)
INHERITS (todoapp.discussions);

CREATE TABLE todoapp_history.followers_tasks (
    _entry_uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    _entry_type todoapp_history.update_type NOT NULL,
    _entry_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
)
INHERITS (todoapp.followers_tasks);

CREATE TABLE todoapp_history.followers_discussions (
    _entry_uuid uuid DEFAULT public.gen_random_uuid() NOT NULL PRIMARY KEY,
    _entry_type todoapp_history.update_type NOT NULL,
    _entry_timestamp timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
)
INHERITS (todoapp.followers_discussions);

-- I couldn't find a simple method to change the default owner for tables, so I'm setting them all here.
ALTER TABLE todoapp.users OWNER TO postgres;
ALTER TABLE todoapp.settings_users OWNER TO postgres;
ALTER TABLE todoapp.tags OWNER TO postgres;
ALTER TABLE todoapp.states OWNER TO postgres;
ALTER TABLE todoapp.tasks OWNER TO postgres;
ALTER TABLE todoapp.tasks_tags OWNER TO postgres;
ALTER TABLE todoapp.tasks_states OWNER TO postgres;
ALTER TABLE todoapp.discussions OWNER TO postgres;
ALTER TABLE todoapp.followers_tasks OWNER TO postgres;
ALTER TABLE todoapp.followers_discussions OWNER TO postgres;

ALTER TABLE todoapp_private.users OWNER TO postgres;

ALTER TABLE todoapp_history.users OWNER TO postgres;
ALTER TABLE todoapp_history.tags OWNER TO postgres;
ALTER TABLE todoapp_history.states OWNER TO postgres;
ALTER TABLE todoapp_history.tasks OWNER TO postgres;
ALTER TABLE todoapp_history.tasks_tags OWNER TO postgres;
ALTER TABLE todoapp_history.tasks_states OWNER TO postgres;
ALTER TABLE todoapp_history.discussions OWNER TO postgres;
ALTER TABLE todoapp_history.followers_tasks OWNER TO postgres;
ALTER TABLE todoapp_history.followers_discussions OWNER TO postgres;

GRANT USAGE ON SCHEMA todoapp TO oopdb;
-- This should be separated out into a separate role.
GRANT USAGE ON SCHEMA todoapp_private TO oopdb;
GRANT USAGE ON SCHEMA todoapp_history TO oopdb;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA todoapp TO oopdb;
-- This should be separated out into a separate role.
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA todoapp_private TO oopdb;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA todoapp_history TO oopdb;
