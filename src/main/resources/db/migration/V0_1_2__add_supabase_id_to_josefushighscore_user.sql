-- Add supabase_id column to User table in josefushighscore schema
ALTER TABLE josefushighscore."user" ADD COLUMN IF NOT EXISTS supabase_id UUID;

-- Add foreign key constraint if it doesn't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_user_supabase_id') THEN
        ALTER TABLE josefushighscore."user" ADD CONSTRAINT fk_user_supabase_id
            FOREIGN KEY (supabase_id)
            REFERENCES auth.users(id);
    END IF;
END $$;