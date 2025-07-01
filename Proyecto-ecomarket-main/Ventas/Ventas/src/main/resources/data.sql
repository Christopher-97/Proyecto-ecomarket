-- Ventas de ejemplo (asegúrate que los IDs coincidan con tus datos reales)
INSERT INTO ventas (cliente_id, total) VALUES 
(1, 974.90),  -- María González
(2, 100.49);   -- Juan Pérez

-- Ítems de venta
INSERT INTO items_venta (venta_id, producto_id, nombre_producto, cantidad, precio_unitario, subtotal) VALUES 
(1, 1, 'Laptop HP Pavilion', 1, 899.90, 899.90),
(1, 3, 'Mouse Inalámbrico', 3, 24.99, 74.97),
(2, 2, 'Teclado Mecánico RGB', 1, 75.50, 75.50),
(2, 5, 'Cable HDMI 2.0', 2, 8.50, 17.00),
(2, 6, 'Funda para Laptop 15.6"', 1, 19.99, 19.99);