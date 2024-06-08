package com.aluracursos.challengeliteralura.principal;

import com.aluracursos.challengeliteralura.model.*;
import com.aluracursos.challengeliteralura.repository.AutorRepository;
import com.aluracursos.challengeliteralura.repository.LibroRepository;
import com.aluracursos.challengeliteralura.service.ConsumoAPI;
import com.aluracursos.challengeliteralura.service.ConvierteDatos;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.*;

public class Principal {
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }
    public void mostrarMenu() {
        while (true) {
            var menu = """
                    |------------------------------------------|
                    | 1 - Agregar un libro a la base de datos. |
                    | 2 - Mostrar libros registrados.          |
                    | 3 - Buscar libro por titulo.             |
                    | 4 - Mostrar autores registrados.         |
                    | 5 - Buscar autores vivos entre años.     |
                    | 6 - Mostrar libros por idioma.           |
                    | 7 - Buscar autor por nombre.             |
                    | 8 - ¡Recomiendame un libro!.             |
                    | 9 - Mostrar Libros de un autor.          |
                    | 10- Top 10 libros más descargados.       |
                    |                                          |
                    | 0 - Salir.                               |
                    |------------------------------------------|
                    | Seleccione una opción:                   |""";

            var menuIdiomas = """
                    |-----------------------|
                    | 1 - Alemán.           |
                    | 2 - Inglés.           |
                    | 3 - Portugués.        |
                    | 4 - Español.          |
                    | 5 - Francés.          |
                    |                       |
                    |-----------------------|
                      Seleccione el idioma: """;

            System.out.println(menu);
            try{
            int opcion = teclado.nextInt();
            teclado.nextLine();
                switch (opcion) {
                    case 1:
                        registrarLibro();
                        break;
                    case 2:
                        mostrarLibros();
                        break;
                    case 3:
                        buscarLibroPorTitulo();
                        break;
                    case 4:
                        mostrarAutores();
                        break;
                    case 5:
                        buscarAutorVivoPorFecha();
                        break;
                    case 6:
                        buscarPorIdioma(menuIdiomas);
                        break;
                    case 7:
                        buscarAutorPorNombre();
                        break;
                    case 8:
                        recomendarLibro();
                        break;
                    case 9:
                        buscarLibrosPorAutor();
                        break;
                    case 10:
                        top10Libros();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opción no valida. Intenta otra vez.\n");
                }
            } catch (IllegalArgumentException | InputMismatchException e){
                System.out.println("Ingrese un número valido.\n");
                teclado.nextLine();
            }
        }
    }

    //Extrae los datos de la api
    private Datos getDatosLibro() {
        System.out.print("Ingrese el titulo del Libro que desea agregar: ");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + tituloLibro.toLowerCase().replace(" ", "+"));
        return convierteDatos.obtenerDatos(json, Datos.class);
    }

    private void registrarLibro() {
        Datos datos = getDatosLibro();
        //Realizar opciones si el resultado no es nulo
        if (!datos.libros().isEmpty()) {
            DatosLibro datosLibro = datos.libros().getFirst();
            DatosAutor datosAutor = datosLibro.autor().getFirst();
            Libro libroBD = libroRepository.findByTituloIgnoreCase(datosLibro.titulo());
            //Si el libro está en la base de datos, no se registra
            if (libroBD != null) {
                System.out.println("Ese libro ya ha sido registrado. \n");
            } else {
                Autor autorBD = autorRepository.findByNombreIgnoreCase(datosAutor.nombreAutor());
                // si el autor no está en la base de datos se registra
                if (autorBD == null) {
                    Autor nuevoAutor = new Autor(datosAutor);
                    autorBD = autorRepository.save(nuevoAutor);
                }
                //si el autor está rgistrado, se usa ese registro para ayudar a llenar el libro que se va a registrar
                Libro libro = agregarLibroBD(datosLibro, autorBD);
                System.out.println("Libro agregado: " + libro);
            }
        } else {
            System.out.println("No hay ninguna coincidencia. \n");
        }
    }

    //Agrega el libro mientras el autor no sea nulo
    private Libro agregarLibroBD(DatosLibro datosLibro, Autor autor) {
        if (autor == null) {
            throw new IllegalArgumentException("El autor no puede ser nulo");
        }
        Libro libro = new Libro(datosLibro, autor);
        return libroRepository.save(libro);
    }

    //Muestra todos los libros registrados
    private void mostrarLibros() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void buscarLibroPorTitulo(){
        System.out.print("Ingrese el titulo del Libro que desea buscar: ");
        try {
            String tituloLibro = teclado.nextLine();
            Libro libroBD = libroRepository.buscarLibroPorTitulo(tituloLibro);
            if (libroBD != null && !Objects.equals(tituloLibro, " "))
                System.out.println(libroBD);
            else{
                System.out.println("Titulo no encontrado.\n");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Idioma no valido.\n");
            teclado.nextLine();
        } catch (IncorrectResultSizeDataAccessException e){
            System.out.println("La opción no es válida");
            teclado.nextLine();
        }
    }

    private void mostrarAutores(){
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    public void buscarAutorVivoPorFecha(){
        //Pedimos los años para crear los parametros de busqueda
        try{
            System.out.print("Ingrese el año de inicio: ");
            int anioInicio = teclado.nextInt();
            System.out.print("Ingrese el año de fin: ");
            int anioFin = teclado.nextInt();

            List<Autor> autores = autorRepository.findAutorBetweenYears(anioInicio, anioFin);
            if(autores != null){
                autores.forEach(System.out::println);
            } else {
                System.out.println("No se encontraron autores vivos entre esas fechas.\n");
            }
        } catch (IllegalArgumentException | InputMismatchException e){
            System.out.println("Ingrese un año valido.\n");
            teclado.nextLine();
        }
    }

    public void buscarPorIdioma(String menuIdiomas){
        System.out.println(menuIdiomas);
        try {
            int opcion = teclado.nextInt();
            //Selecciona la categoria para despues realizar la busqueda con ese idioma
            Categoria categoriaSeleccionada = Categoria.fromIndex(opcion-1);
            System.out.println("Libros en idioma: " + categoriaSeleccionada.getCategoriaEspanol());
            List<Libro> libros = libroRepository.buscarLibroPorIdioma(categoriaSeleccionada);
            libros.forEach(System.out::println);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Idioma no valido.\n");
            teclado.nextLine();
        } catch (IncorrectResultSizeDataAccessException e){
            System.out.println("La opción no es válida");
            teclado.nextLine();
        }
    }

    public void buscarAutorPorNombre() {
        System.out.println("Ingrese el nombre del autor que desea buscar: ");
        try{
            String autorBuscado = teclado.nextLine();
            Autor autor = autorRepository.buscarAutorPorNombre(autorBuscado);
            if (autor != null && !Objects.equals(autorBuscado, " ")){
                System.out.println(autor);
            } else {
                System.out.println("Autor no encontrado.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("No hay ningún autor registrado con ese nombre.\n");
            teclado.nextLine();
        } catch (IncorrectResultSizeDataAccessException e){
            System.out.println("La búsqueda no es válida");
            teclado.nextLine();
        }
    }

    public void recomendarLibro(){
        List<Libro> libros = libroRepository.buscarMejoresLibros();
        Random random = new Random();

        //Realizamos la busqueda con los libros con más de mil descargas y de ahí seleccionamos uno random
        //para recomendar al usuario
        Libro libroRecomendado = libros.get(random.nextInt(libros.size()));

        System.out.println("Te recomiendo leer el siguiente libro: \n" + libroRecomendado);
    }

    public void buscarLibrosPorAutor(){
        List<Autor> autores = autorRepository.findAll();
        //Tomamos todos los autores para mostrarlos en pantalla
        System.out.println("-------------AUTORES-------------");
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(a ->
                        System.out.println(a.getNombre()));
        System.out.println("-------------------------------");
        //Buscamos en la base de datos los libros con el id del autor seleccionado
        System.out.println("Ingrese el autor del que desa consultar sus libros: ");
        try{
            String autorElegido = teclado.nextLine();
            List<Libro> libros = libroRepository.buscarLibroPorAutor(autorElegido);
            if (libros != null && !Objects.equals(autorElegido, " ")){
                System.out.println("Libros escritos por: " + libros.getFirst().getAutor());
                libros.forEach(l ->
                        System.out.printf("Titulo: %s\nDescargas: %s\nIdioma: %s\n\n",
                                l.getTitulo(), l.getDescargas(), l.getIdioma()));
            }
            else{
                System.out.println("No hay ningún libro escrito registrado por ese autor.\n");
            }
        } catch (NoSuchElementException e) {
            System.out.println("No hay ningún libro escrito registrado por ese autor.\n");
            teclado.nextLine();
        } catch (IncorrectResultSizeDataAccessException e){
            System.out.println("La búsqueda no es válida");
            teclado.nextLine();
        }

    }

    public void top10Libros(){
        List<Libro> libros = libroRepository.Top10LibrosMasDescargados();
        //Tomamos los 10 libros más descargados y con lamdas mostramos la información relevante de cada libro
        System.out.println("----------TOP 10 Libros más descargados----------");
        libros.forEach(l ->
                System.out.printf("Titulo: %s\nDescargas: %s\nAutor: %s\n\n",
                        l.getTitulo(), l.getDescargas(), l.getAutor()));
    }

}
