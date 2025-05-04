-- Create a table to track user sign-ins
CREATE TABLE user_sign_in_tracking (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    sign_in_count INT DEFAULT 0,
    last_sign_in TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES josefushighscore.user (user_id) ON DELETE CASCADE
);
