-- Insertar un usuario administrador con contraseña encriptada
INSERT INTO users (name, username, email, password, is_active) 
VALUES ('admin', 'admin', 'admin@admin.co', '$2a$10$rPiEAgSYQfJGrAhDfDlZjOtVKlEZj7vqP8YzUVL4j6yfXLQSF1yPG', TRUE);

-- Obtener el ID del usuario admin recién insertado
DO $$
DECLARE
    admin_user_id INT;
BEGIN
    SELECT id INTO admin_user_id FROM users WHERE username = 'admin';

    -- Insertar el rol de ADMIN si no existe
    INSERT INTO roles (name) 
    VALUES ('ADMIN')
    ON CONFLICT (name) DO NOTHING;

    -- Asignar el rol de ADMIN al usuario admin
    INSERT INTO user_roles (user_id, role_id)
    VALUES (admin_user_id, (SELECT id FROM roles WHERE name = 'ADMIN'));
END $$;