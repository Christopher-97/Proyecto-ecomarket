-- Productos básicos
INSERT INTO productos (nombre, descripcion, precio, stock, categoria) VALUES 
('Laptop HP Pavilion', '15.6 pulgadas, Core i5, 8GB RAM', 899.90, 15, 'Electrónicos'),
('Teclado Mecánico RGB', 'Switches Blue, retroiluminación RGB', 75.50, 30, 'Periféricos'),
('Mouse Inalámbrico', '2400 DPI, ergonómico', 24.99, 50, 'Periféricos'),
('Monitor 24" Full HD', 'Panel IPS, 75Hz, HDMI/VGA', 159.00, 8, 'Electrónicos');

-- Productos con descuento (para demostrar filtros)
INSERT INTO productos (nombre, precio, stock, categoria) VALUES 
('Cable HDMI 2.0', 8.50, 100, 'Accesorios'),
('Funda para Laptop 15.6"', 19.99, 25, 'Accesorios');