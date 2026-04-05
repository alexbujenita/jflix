CREATE TABLE IF NOT EXISTS user_account (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name text NOT NULL,
    last_name text NOT NULL,
    email text NOT NULL UNIQUE,
    password_hash text NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);