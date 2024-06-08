package com.aluracursos.challengeliteralura.repository;

import com.aluracursos.challengeliteralura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombreIgnoreCase(String nombreAutor);

    List<Autor> findAll();

    @Query("SELECT a FROM Autor a WHERE :anioInicio <= a.anioNacimiento AND a.anioMuerte <= :anioFin ORDER BY a.anioNacimiento ASC")
    List<Autor> findAutorBetweenYears(int anioInicio, int anioFin);

    @Query("SELECT a FROM Autor a WHERE a.nombre ILIKE '%' || :autorBuscado || '%'")
    Autor buscarAutorPorNombre(String autorBuscado);
}
