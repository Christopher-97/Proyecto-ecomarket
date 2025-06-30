-- Clientes de ejemplo
INSERT INTO clientes (nombre, email, direccion, telefono) VALUES 
('María González', 'maria.gonzalez@example.com', 'Calle Primavera 123', '+56912345678'),
('Juan Pérez', 'juan.perez@example.com', 'Avenida Libertad 456', '+56987654321'),
('Sofía Ramírez', 'sofia.ramirez@example.com', 'Pasaje Los Olivos 789', '+56956781234');

-- Actualización para demostración
UPDATE clientes SET direccion = 'Nueva Dirección 100' WHERE email = 'maria.gonzalez@example.com';