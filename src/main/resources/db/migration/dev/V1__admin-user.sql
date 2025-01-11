-- Insertar un usuario administrador con contraseña encriptada si no existe
DO $$
DECLARE
    admin_user_id BIGINT;
BEGIN
    -- Verificar si el usuario 'admin' ya existe
    IF NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin') THEN
        -- Insertar el usuario 'admin' con la contraseña encriptada
        INSERT INTO users (name, username, email, password, is_active) 
        VALUES ('admin', 'admin', 'admin@admin.co', '$2a$10$glQsfBUJbY7YbjnxwEsL8uX5nXgqueaETvib//q41cbsvXrmoaEb2', TRUE)
        RETURNING id INTO admin_user_id;

        RAISE NOTICE 'Admin user created with ID: %', admin_user_id;
    ELSE
        SELECT id INTO admin_user_id FROM users WHERE username = 'admin';
        RAISE NOTICE 'Admin user already exists with ID: %', admin_user_id;
    END IF;

    -- Insertar el rol de ADMIN si no existe
    INSERT INTO roles (name) 
    VALUES ('ADMIN')
    ON CONFLICT (name) DO NOTHING;

    -- Asignar el rol de ADMIN al usuario admin
    INSERT INTO user_roles (user_id, role_id)
    VALUES (admin_user_id, (SELECT id FROM roles WHERE name = 'ADMIN'))
    ON CONFLICT (user_id, role_id) DO NOTHING;

    RAISE NOTICE 'Admin role assigned to user with ID: %', admin_user_id;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION 'Error in admin user creation: %', SQLERRM;
END $$;