-- Agregar la columna category_id a la tabla topics si no existe
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name='topics' AND column_name='category_id'
    ) THEN
        ALTER TABLE topics
        ADD COLUMN category_id BIGINT ,
        ADD FOREIGN KEY (category_id) REFERENCES courses(id);
    END IF;
END $$;