package com.aluracursos.challengeliteralura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String nombreAutor;
    @Enumerated(EnumType.STRING)
    private Categoria idioma;
    private Integer descargas;
    @ManyToOne
    private Autor autor;

    public Libro(){}

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();
        this.nombreAutor = autor.getNombre();
        this.autor = autor;
        if (datosLibro.idioma() != null && !datosLibro.idioma().isEmpty()) {
            try {
                this.idioma = Categoria.fromString(datosLibro.idioma().getFirst());
            } catch (IllegalArgumentException e) {
                this.idioma = Categoria.valueOf("Desconocido"); // O maneja el caso de idioma no encontrado de otra manera
                System.out.println("El idioma " + datosLibro.idioma().getFirst() + " no es soportado.");
            }
        } else {
            this.idioma = Categoria.valueOf("Desconocido"); // O algún valor por defecto
        }
        this.descargas = datosLibro.descargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return nombreAutor;
    }

    public void setAutor(String autor) {
        this.nombreAutor = nombreAutor;
    }

    public Categoria getIdioma() {
        return idioma;
    }

    public void setIdioma(Categoria idioma) {
        this.idioma = idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }

    @Override
    public String toString() {
        return "\n------------LIBRO------------\n" +
                "Titulo: " + titulo + '\n' +
                "Autor: " + autor.getNombre() + '\n' +
                "Idioma: " + idioma + '\n' +
                "Número de descargas: " + descargas + '\n' +
                "------------------------------\n";
    }
}