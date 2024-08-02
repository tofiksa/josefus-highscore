-- Modify foreign key constraint on score table to add CASCADE DELETE
ALTER TABLE josefushighscore."score"
DROP CONSTRAINT IF EXISTS fk_score_game_id;

ALTER TABLE josefushighscore."score"
ADD CONSTRAINT fk_score_game_id
    FOREIGN KEY (game_id)
    REFERENCES josefushighscore.game(game_id)
    ON DELETE CASCADE;