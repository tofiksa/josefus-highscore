CREATE TABLE IF NOT EXISTS USER (
    user_id bigint NOT NULL,
    email varchar(255),
    firstname varchar(255),
    lastname varchar(255),
    password varchar(255),
    username varchar(255),
    PRIMARY KEY (user_id)
);