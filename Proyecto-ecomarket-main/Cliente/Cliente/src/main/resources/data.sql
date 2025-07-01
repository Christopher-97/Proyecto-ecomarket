-- Clientes de ejemplo
INSERT INTO clientes (nombre, contrasenia, run, telefono) VALUES 
('María González', 'maria.12345678', '12489234', '+56912345678'),
('Juan Pérez', 'juan.21467645773', '145682464', '+56987654321'),
('Sofía Ramírez', 'sofia.47734368345', '204573465', '+56956781234');

-- Actualización para demostración
UPDATE clientes SET contrasenia = 'Nueva contrasenia 1' WHERE contrasenia = 'maria.12345678';
