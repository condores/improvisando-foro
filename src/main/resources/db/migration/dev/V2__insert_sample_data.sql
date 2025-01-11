-- Insertar roles de ejemplo
INSERT INTO roles (name) 
VALUES ('USER'), ('ADMIN')
ON CONFLICT (name) DO NOTHING;

-- Insertar Usuario regular
INSERT INTO users (name, username, email, password, is_active) 
VALUES ('Adrian Daconte', 'adrian', 'user@user.co', '$2a$10$XURPShQNCsLjp1ESc2laoObo9QZDhxz73hJPaEv7/cBha4pk0AgP.', TRUE)
ON CONFLICT (username) DO NOTHING;

-- Asignar rol USER al usuario regular
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username = 'adrian' AND r.name = 'USER'
ON CONFLICT (user_id, role_id) DO NOTHING;

-- Insertar cursos de ejemplo
INSERT INTO courses (name, category) 
VALUES ('Java Basics', 'BACKEND'),
       ('Frontend Development', 'FRONTEND')
ON CONFLICT (name) DO NOTHING;

-- Insertar tópicos de ejemplo
INSERT INTO topics (title, message, status, author_id, category_id) 
VALUES ('Welcome to Java', 'This is a topic about Java basics.', 'OPEN', 
        (SELECT id FROM users WHERE username = 'adrian'),
        (SELECT id FROM courses WHERE name = 'Java Basics'));

-- Insertar comentarios de ejemplo
INSERT INTO comments (message, author_id, topic_id)
VALUES ('Great introduction to Java!',
        (SELECT id FROM users WHERE username = 'adrian'),
        (SELECT id FROM topics WHERE title = 'Welcome to Java'));