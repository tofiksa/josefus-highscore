-- Add supabase_id column to User table in josefushighscore schema
ALTER TABLE josefushighscore."user" ADD COLUMN supabase_id UUID;

-- Add foreign key constraint
ALTER TABLE josefushighscore."user" ADD CONSTRAINT fk_user_supabase_id
    FOREIGN KEY (supabase_id)
    REFERENCES auth.users(id);