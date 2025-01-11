-- Crear la tabla roles si no existe
CREATE TABLE IF NOT EXISTS roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Crear la tabla users si no existe
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear la tabla courses si no existe
CREATE TABLE IF NOT EXISTS courses (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    category VARCHAR(50) NOT NULL
);

-- Crear la tabla topics si no existe
CREATE TABLE IF NOT EXISTS topics (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    author_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES courses(id)
);

-- Crear la tabla comments si no existe
CREATE TABLE IF NOT EXISTS comments (
    id BIGSERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    author_id BIGINT NOT NULL,
    topic_id BIGINT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (topic_id) REFERENCES topics(id)
);

-- Crear la tabla user_roles para la relación muchos a muchos entre users y roles si no existe
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Asegúrate de que la columna 'status' en la tabla 'topics' solo acepte valores válidos
ALTER TABLE topics
ADD CONSTRAINT check_topic_status CHECK (status IN ('OPEN', 'CLOSED', 'SOLVED'));

-- Asegúrate de que la columna 'category' en la tabla 'courses' solo acepte valores válidos
ALTER TABLE courses
ADD CONSTRAINT check_course_category CHECK (category IN ('BACKEND', 'FRONTEND', 'DATA_SCIENCE', 'MACHINE_LEARNING', 'DEVELOPMENT_OPS'));