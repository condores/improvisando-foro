-- Insertar un usuario administrador con contraseña encriptada si no existe
DO $$
DECLARE
    admin_user_id INT;
BEGIN
    -- Verificar si el usuario 'admin' ya existe
    IF NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin') THEN
        -- Insertar el usuario 'admin'
        INSERT INTO users (name, username, email, password, is_active) 
        VALUES ('admin', 'admin', 'admin@admin.cos', 'dako', TRUE);
    END IF;

    -- Obtener el ID del usuario admin
    SELECT id INTO admin_user_id FROM users WHERE username = 'admin';

    -- Insertar el rol de ADMIN si no existe
    INSERT INTO roles (name) 
    VALUES ('ADMIN')
    ON CONFLICT (name) DO NOTHING;

    -- Asignar el rol de ADMIN al usuario admin
    INSERT INTO user_roles (user_id, role_id)
    VALUES (admin_user_id, (SELECT id FROM roles WHERE name = 'ADMIN'))
    ON CONFLICT (user_id, role_id) DO NOTHING;
END $$;