package com.aluracursos.Amoxtli.repository;

import com.aluracursos.Amoxtli.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    boolean existsByLibroID(Long libroID);
}
