package com.aluracursos.challengeliteralura.repository;

import com.aluracursos.challengeliteralura.model.Categoria;
import com.aluracursos.challengeliteralura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Libro findByTituloIgnoreCase(String titulo);

    List<Libro> findAll();

    @Query("SELECT l FROM Libro l WHERE l.titulo ILIKE %:tituloLibro% ")
    Libro buscarLibroPorTitulo(String tituloLibro);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :categoriaSeleccionada ORDER BY l.titulo ASC")
    List<Libro> buscarLibroPorIdioma(Categoria categoriaSeleccionada);

    @Query("SELECT l FROM Libro l WHERE l.descargas > 1000")
    List<Libro> buscarMejoresLibros();

    @Query("SELECT l FROM Libro l JOIN l.autor a WHERE a.nombre ILIKE %:autorElegido%")
    List<Libro> buscarLibroPorAutor(String autorElegido);

    @Query("SELECT l FROM Libro l ORDER BY l.descargas DESC LIMIT 10")
    List<Libro> Top10LibrosMasDescargados();
}

