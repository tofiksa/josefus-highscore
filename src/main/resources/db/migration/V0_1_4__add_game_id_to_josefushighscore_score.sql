-- Add game_id column to Score table in josefushighscore schema
ALTER TABLE josefushighscore."score" ADD COLUMN IF NOT EXISTS game_id bigint;

-- Add foreign key constraint if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_score_game_id') THEN
        ALTER TABLE josefushighscore."score" ADD CONSTRAINT fk_score_game_id
            FOREIGN KEY (game_id)
            REFERENCES josefushighscore.game(game_id);
    END IF;
END $$;