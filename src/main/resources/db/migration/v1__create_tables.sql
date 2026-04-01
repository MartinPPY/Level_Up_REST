-- V1: Create tables for Level Up REST application
-- Generated based on JPA Entity Analysis

-- 1. Create independent tables (no foreign keys)
CREATE TABLE region (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

CREATE TABLE role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- 2. Create tables with simple foreign keys
CREATE TABLE comuna (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    region_id BIGINT,
    CONSTRAINT fk_comuna_region FOREIGN KEY (region_id) REFERENCES region(id)
);

CREATE TABLE product (
    code VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    precio DOUBLE NOT NULL,
    stock BIGINT NOT NULL,
    stock_critico BIGINT,
    image VARCHAR(255),
    category_id BIGINT,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id)
);

-- 3. Create users table (depends on comuna and role)
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    run VARCHAR(9) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    birthday DATE,
    password VARCHAR(255) NOT NULL,
    addres VARCHAR(300) NOT NULL,
    comuna_id BIGINT,
    role_id BIGINT,
    CONSTRAINT fk_users_comuna FOREIGN KEY (comuna_id) REFERENCES comuna(id),
    CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES role(id)
);

-- 4. Create transaction tables
CREATE TABLE venta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    user_id BIGINT,
    CONSTRAINT fk_venta_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE venta_detalle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    venta_id BIGINT,
    product_code VARCHAR(255),
    cantidad INT,
    precio DOUBLE,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (venta_id) REFERENCES venta(id),
    CONSTRAINT fk_detalle_product FOREIGN KEY (product_code) REFERENCES product(code)
);
