CREATE TABLE IF NOT EXISTS SCORE (
    score_id bigint NOT NULL,
    score bigint NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    PRIMARY KEY (score_id)
);