-- ============================================
-- Script de inicialización de la base de datos
-- ============================================

-- Este script se ejecuta automáticamente cuando
-- se crea el contenedor de PostgreSQL por primera vez.

-- Las tablas se crean automáticamente por JPA/Hibernate
-- Este script es para datos iniciales de prueba.

-- Nota: Las contraseñas están hasheadas con BCrypt
-- Password: "admin123" -> $2a$10$...
-- Password: "user123" -> $2a$10$...

-- Los datos de prueba se insertan desde DataInitializer.java
-- para mayor flexibilidad y control.

-- Si necesitas datos SQL directos, descomenta las siguientes líneas:

/*
-- Insertar categorías de ejemplo
INSERT INTO categories (name, description, created_at, updated_at) VALUES
('Electronics', 'Electronic devices and gadgets', NOW(), NOW()),
('Clothing', 'Apparel and fashion items', NOW(), NOW()),
('Books', 'Physical and digital books', NOW(), NOW()),
('Home & Garden', 'Home improvement and garden supplies', NOW(), NOW());

-- Nota: Los usuarios deben crearse a través de la API
-- para que las contraseñas sean hasheadas correctamente.
*/

-- Grant permissions (por si acaso)
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO dev;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO dev;
