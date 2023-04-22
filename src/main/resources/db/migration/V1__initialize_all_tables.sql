CREATE TABLE IF NOT EXISTS categories (
                                id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(255),
                                description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS posts (
                                id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                title  VARCHAR(255) NOT NULL,
                                description VARCHAR(255) NOT NULL,
                                content TEXT NOT NULL,
                                category_id BIGINT UNSIGNED,
                                CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE,
                                CONSTRAINT unique_title UNIQUE (title)
);

CREATE TABLE IF NOT EXISTS comments (
                                id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(255),
                                email VARCHAR(255),
                                body TEXT,
                                post_id BIGINT UNSIGNED NOT NULL,
                                CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users (
                                id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(255) NOT NULL,
                                username VARCHAR(255) NOT NULL UNIQUE,
                                email VARCHAR(255) NOT NULL UNIQUE,
                                password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
                                 id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                                 name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_roles (
                                user_id BIGINT UNSIGNED NOT NULL,
                                role_id BIGINT UNSIGNED NOT NULL,
                                PRIMARY KEY (user_id, role_id),
                                CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO categories (name, description) VALUES ('IT', 'Blog for IT professionals');
INSERT INTO categories (name, description) VALUES ('Java', 'Blog for Java developers');
