package com.aluracursos.challengeliteralura.model;

public enum Categoria {
    ALEMAN("de", "Alemán"),
    INGLES("en", "Inglés"),
    PORTUGUES("pt", "Portugués"),
    ESPANOL("es", "Español"),
    FRANCES("fr", "Francés"),
    DESCONOCIDO("unknown", "Desconocido");

    private String categoriaGutendex;
    private String categoriaEspanol;

    Categoria(String categoriaGutendex, String categoriaEspanol) {
        this.categoriaGutendex = categoriaGutendex;
        this.categoriaEspanol = categoriaEspanol;
    }

    public String getCategoriaGutendex() {
        return categoriaGutendex;
    }

    public String getCategoriaEspanol() {
        return categoriaEspanol;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaGutendex.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    public static Categoria fromIndex(int index) {
        Categoria[] categorias = Categoria.values();
        if (index >= 0 && index < categorias.length) {
            return categorias[index];
        } else {
            throw new IllegalArgumentException("Índice fuera de rango: " + index);
        }
    }

    @Override
    public String toString() {
        return categoriaEspanol;
    }
}
