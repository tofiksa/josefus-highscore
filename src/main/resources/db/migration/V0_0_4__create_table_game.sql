CREATE TABLE IF NOT EXISTS josefushighscore.game (
    game_id bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    PRIMARY KEY (game_id)
);