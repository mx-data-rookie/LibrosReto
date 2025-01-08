package com.aluracursos.Amoxtli.repository;

import com.aluracursos.Amoxtli.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE :fechaBuscada BETWEEN a.fechaNacimiento AND a.fechaFallecimiento")
    List<Autor> autoresPorFechaActividad(int fechaBuscada);
}
