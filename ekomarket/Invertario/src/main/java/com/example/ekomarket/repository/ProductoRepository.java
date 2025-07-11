package com.example.ekomarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ekomarket.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}
