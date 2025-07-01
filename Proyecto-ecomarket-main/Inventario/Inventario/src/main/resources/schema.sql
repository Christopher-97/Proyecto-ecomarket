CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP
);