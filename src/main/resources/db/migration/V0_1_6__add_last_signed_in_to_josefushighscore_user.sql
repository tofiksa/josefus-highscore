-- V0_1_6__add_last_signed_in_to_josefushighscore_user.sql
ALTER TABLE josefushighscore.user ADD COLUMN IF NOT EXISTS last_signed_in timestamp without time zone;