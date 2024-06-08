package com.aluracursos.challengeliteralura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private int anioNacimiento;
    private int anioMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombreAutor();
        try {
            this.anioNacimiento = datosAutor.anioNacimiento();
        } catch (NullPointerException e){
            this.anioNacimiento = 9998;
        }
        try {
            this.anioMuerte = datosAutor.annioMuerte();
        } catch (NullPointerException e){
            this.anioMuerte = 9999;
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioMuerte() {
        return anioMuerte;
    }

    public void setAnioMuerte(Integer anioMuerte) {
        this.anioMuerte = anioMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "\n-------------AUTOR-------------\n" +
                "Nombre: " + nombre + '\n' +
                "Fecha de Nacimiento: " + anioNacimiento + '\n' +
                "Fecha de Defunción: " + anioMuerte + '\n' +
                "-------------------------------";
    }
}
