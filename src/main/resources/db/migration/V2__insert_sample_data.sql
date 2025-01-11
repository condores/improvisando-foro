-- Insertar roles de ejemplo
INSERT INTO roles (name) 
VALUES ('USER'), ('ADMIN')
ON CONFLICT (name) DO NOTHING;

-- Insertar Usuarios
INSERT INTO users (name, username, email, password, is_active) 
VALUES 
    ('Adrian Daconte', 'adrian', 'adrian@example.com', '$2a$10$glQsfBUJbY7YbjnxwEsL8uX5nXgqueaETvib//q41cbsvXrmoaEb2', TRUE),
    ('Dako Dev', 'dakoDev', 'dako@example.com', '$2a$10$glQsfBUJbY7YbjnxwEsL8uX5nXgqueaETvib//q41cbsvXrmoaEb2', TRUE)
ON CONFLICT (username) DO NOTHING;

-- Asignar rol USER a los usuarios
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.username IN ('adrian', 'dakoDev') AND r.name = 'USER'
ON CONFLICT (user_id, role_id) DO NOTHING;

-- Insertar cursos de ejemplo
INSERT INTO courses (name, category) 
VALUES 
    ('Java Basics', 'BACKEND'),
    ('Frontend Development', 'FRONTEND'),
    ('Python for Data Science', 'DATA_SCIENCE'),
    ('DevOps Fundamentals', 'DEVELOPMENT_OPS'),
    ('Machine Learning Essentials', 'MACHINE_LEARNING')
ON CONFLICT (name) DO NOTHING;

-- Insertar tópicos de ejemplo
INSERT INTO topics (title, message, status, author_id, category_id) 
VALUES 
    ('Welcome to Java', 'This is a topic about Java basics.', 'OPEN',
        (SELECT id FROM users WHERE username = 'adrian'),
        (SELECT id FROM courses WHERE name = 'Java Basics')),
    ('HTML & CSS Tips', 'Share your best practices for frontend development.', 'OPEN', 
        (SELECT id FROM users WHERE username = 'dakoDev'),
        (SELECT id FROM courses WHERE name = 'Frontend Development')),
    ('Python Libraries for Data Analysis', 'What are your favorite Python libraries for data analysis?', 'OPEN', 
        (SELECT id FROM users WHERE username = 'adrian'),
        (SELECT id FROM courses WHERE name = 'Python for Data Science')),
    ('CI/CD Pipeline Setup', 'How to set up an efficient CI/CD pipeline?', 'OPEN', 
        (SELECT id FROM users WHERE username = 'dakoDev'),
        (SELECT id FROM courses WHERE name = 'DevOps Fundamentals')),
    ('Intro to Neural Networks', 'Let''s discuss the basics of neural networks.', 'OPEN', 
        (SELECT id FROM users WHERE username = 'adrian'),
        (SELECT id FROM courses WHERE name = 'Machine Learning Essentials'));

-- Insertar comentarios de ejemplo
INSERT INTO comments (message, author_id, topic_id)
VALUES 
    -- Comentarios para "Welcome to Java"
    ('Great introduction to Java!',
        (SELECT id FROM users WHERE username = 'dakoDev'),
        (SELECT id FROM topics WHERE title = 'Welcome to Java')),
    ('I found the section on OOP particularly helpful.',
        (SELECT id FROM users WHERE username = 'adrian'),
        (SELECT id FROM topics WHERE title = 'Welcome to Java')),
    ('Could you recommend some advanced Java resources?',
        (SELECT id FROM users WHERE username = 'dakoDev'),
        (SELECT id FROM topics WHERE title = 'Welcome to Java')),

    -- Comentarios para "HTML & CSS Tips"
    ('I find flexbox really useful for layouts.',
        (SELECT id FROM users WHERE username = 'adrian'),
        (SELECT id FROM topics WHERE title = 'HTML & CSS Tips')),
    ('Have you tried CSS Grid? It''s great for complex layouts.',
        (SELECT id FROM users WHERE username = 'dakoDev'),
        (SELECT id FROM topics WHERE title = 'HTML & CSS Tips')),

    -- Comentarios para "Python Libraries for Data Analysis"
    ('Pandas and NumPy are essential for data analysis in Python.',
        (SELECT id FROM users WHERE username = 'dakoDev'),
        (SELECT id FROM topics WHERE title = 'Python Libraries for Data Analysis')),
    ('I''ve been using Matplotlib for data visualization. Any other recommendations?',
        (SELECT id FROM users WHERE username = 'adrian'),
        (SELECT id FROM topics WHERE title = 'Python Libraries for Data Analysis')),
    ('Seaborn is great for statistical data visualization!',
        (SELECT id FROM users WHERE username = 'dakoDev'),
        (SELECT id FROM topics WHERE title = 'Python Libraries for Data Analysis')),

    -- Comentarios para "CI/CD Pipeline Setup"
    ('Jenkins is a great tool for setting up CI/CD pipelines.',
        (SELECT id FROM users WHERE username = 'adrian'),
        (SELECT id FROM topics WHERE title = 'CI/CD Pipeline Setup')),
    ('I prefer GitLab CI for its integration with GitLab repositories.',
        (SELECT id FROM users WHERE username = 'dakoDev'),
        (SELECT id FROM topics WHERE title = 'CI/CD Pipeline Setup')),

    -- Comentarios para "Intro to Neural Networks"
    ('TensorFlow and PyTorch are popular frameworks for neural networks.',
        (SELECT id FROM users WHERE username = 'dakoDev'),
        (SELECT id FROM topics WHERE title = 'Intro to Neural Networks')),
    ('What''s a good resource for beginners to start with neural networks?',
        (SELECT id FROM users WHERE username = 'adrian'),
        (SELECT id FROM topics WHERE title = 'Intro to Neural Networks')),
    ('I recommend "Deep Learning" by Goodfellow, Bengio, and Courville as a comprehensive resource.',
        (SELECT id FROM users WHERE username = 'dakoDev'),
        (SELECT id FROM topics WHERE title = 'Intro to Neural Networks'));