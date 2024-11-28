-- Drop existing columns
ALTER TABLE attendance DROP COLUMN IF EXISTS subject;
ALTER TABLE attendance DROP COLUMN IF EXISTS present;
ALTER TABLE attendance DROP COLUMN IF EXISTS academic_year;

-- Add status column if it doesn't exist
DO $$ 
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns 
                  WHERE table_name = 'attendance' AND column_name = 'status') THEN
        ALTER TABLE attendance ADD COLUMN status VARCHAR(10) NOT NULL;
    END IF;
END $$;
