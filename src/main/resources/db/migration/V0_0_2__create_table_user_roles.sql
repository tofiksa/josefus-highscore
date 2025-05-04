CREATE TABLE IF NOT EXISTS josefushighscore.user_roles (
    user_id bigint NOT NULL,
    roles varchar(255),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES josefushighscore."user" (user_id)
);