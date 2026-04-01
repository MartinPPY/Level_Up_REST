-- Inserción de Regiones
INSERT INTO region (name) VALUES
('Región de Arica y Parinacota'),
('Región de Tarapacá'),
('Región de Antofagasta'),
('Región Metropolitana de Santiago'),
('Región de Valparaíso');

-- Inserción de Comunas
INSERT INTO comuna (name, region_id) VALUES
('Arica', (SELECT id FROM region WHERE name='Región de Arica y Parinacota')),
('Camarones', (SELECT id FROM region WHERE name='Región de Arica y Parinacota')),
('Iquique', (SELECT id FROM region WHERE name='Región de Tarapacá')),
('Alto Hospicio', (SELECT id FROM region WHERE name='Región de Tarapacá')),
('Antofagasta', (SELECT id FROM region WHERE name='Región de Antofagasta')),
('Calama', (SELECT id FROM region WHERE name='Región de Antofagasta')),
('Santiago', (SELECT id FROM region WHERE name='Región Metropolitana de Santiago')),
('Providencia', (SELECT id FROM region WHERE name='Región Metropolitana de Santiago')),
('Las Condes', (SELECT id FROM region WHERE name='Región Metropolitana de Santiago')),
('Maipú', (SELECT id FROM region WHERE name='Región Metropolitana de Santiago')),
('Valparaíso', (SELECT id FROM region WHERE name='Región de Valparaíso')),
('Viña del Mar', (SELECT id FROM region WHERE name='Región de Valparaíso')),
('Quilpué', (SELECT id FROM region WHERE name='Región de Valparaíso'));

-- Inserción de Roles
INSERT INTO role(name) VALUES
('ROLE_USER'),
('ROLE_VENDEDOR'),
('ROLE_ADMIN');

-- Inserción de Categorías
INSERT INTO category (name) VALUES
('Juegos de Mesa'),
('Accesorios'),
('Consolas'),
('Computadores Gamers'),
('Sillas Gamers'),
('Mouse'),
('Mousepad'),
('Poleras Personalizadas');

-- Inserción de Productos
INSERT INTO product (code, name, description, precio, stock, stock_critico, image, category_id) VALUES
('JM001', 'Catan', 'Un clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.', 29990, 10, 3, '/images/products/catan.webp', (SELECT id FROM category WHERE name = 'Juegos de Mesa')),
('JM002', 'Carcassonne', 'Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.', 24990, 10, 3, '/images/products/carcassonne.jpg', (SELECT id FROM category WHERE name = 'Juegos de Mesa')),
('AC001', 'Controlador Inalámbrico Xbox Series X', 'Ofrece una experiencia de juego cómoda con botones mapeables y una respuesta táctil mejorada. Compatible con consolas Xbox y PC.', 59990, 10, 3, '/images/products/control-xbox-series-x.jpg', (SELECT id FROM category WHERE name = 'Accesorios')),
('AC002', 'Auriculares Gamer HyperX Cloud II', 'Proporcionan un sonido envolvente de calidad con un micrófono desmontable y almohadillas de espuma viscoelástica para mayor comodidad durante largas sesiones de juego.', 79990, 10, 3, '/images/products/Auriculares-Gamer-HyperX-Cloud-II.jpg', (SELECT id FROM category WHERE name = 'Accesorios')),
('C0001', 'PlayStation 5', 'La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.', 549990, 10, 2, '/images/products/PlayStation-5.jpg', (SELECT id FROM category WHERE name = 'Consolas')),
('CG001', 'PC Gamer ASUS ROG Strix', 'Un potente equipo diseñado para los gamers más exigentes, equipado con los últimos componentes para ofrecer un rendimiento excepcional en cualquier juego.', 1299990, 10, 1, '/images/products/PC-Gamer-ASUS-ROG-Strix.jpg', (SELECT id FROM category WHERE name = 'Computadores Gamers')),
('SG001', 'Silla Gamer Secretlab Titan', 'Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable para sesiones de juego prolongadas.', 349990, 10, 2, '/images/products/Silla-Gamer-Secretlab-Titan.jpg', (SELECT id FROM category WHERE name = 'Sillas Gamers')),
('MS001', 'Mouse Gamer Logitech G502 HERO', 'Con sensor de alta precisión y botones personalizables, este mouse es ideal for gamers que buscan un control preciso y personalización.', 49990, 10, 3, '/images/products/Mouse-Gamer-Logitech-G502-HERO.jpg', (SELECT id FROM category WHERE name = 'Mouse')),
('MP001', 'Mousepad Razer Goliathus Extended Chroma', 'Ofrece un área de juego amplia con iluminación RGB personalizable, asegurando una superficie suave y uniforme para el movimiento del mouse.', 29990, 10, 3, '/images/products/Mousepad-Razer-Goliathus-Extended-Chroma.jpg', (SELECT id FROM category WHERE name = 'Mousepad')),
('PP001', 'Polera Gamer Personalizada Level-Up', 'Una camiseta cómoda y estilizada, con la posibilidad de personalizarla con tu gamer tag o diseño favorito.', 14990, 10, 5, '/images/products/Polera-Gamer-Personalizada-Level-Up.jpg', (SELECT id FROM category WHERE name = 'Poleras Personalizadas'));

-- Inserción de Usuarios Iniciales (desde DataInitializer)
INSERT INTO users (run, name, lastname, email, birthday, password, addres, comuna_id, role_id) VALUES
('21340282K', 'admin', 'admin', 'admin@gmail.com', '1990-01-01', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyAY2G6snWyz1lbJlZUiK0fba0u.', 'Cerrillos #213', 1, 3),
('111111111', 'vendedor', 'vendedor', 'vendedor@gmail.com', '1990-01-01', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyAY2G6snWyz1lbJlZUiK0fba0u.', 'Cerrillos #213', 1, 2);
