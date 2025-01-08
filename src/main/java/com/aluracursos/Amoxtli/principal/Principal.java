package com.aluracursos.Amoxtli.principal;

import com.aluracursos.Amoxtli.model.Autor;
import com.aluracursos.Amoxtli.model.Libro;
import com.aluracursos.Amoxtli.model.record.DatosGenerales;
import com.aluracursos.Amoxtli.model.record.DatosLibro;
import com.aluracursos.Amoxtli.repository.AutorRepository;
import com.aluracursos.Amoxtli.repository.LibroRepository;
import com.aluracursos.Amoxtli.service.ConsumoDatosAPI;
import com.aluracursos.Amoxtli.service.ConversionDatos;

import java.util.*;

public class Principal {

    private Scanner entradaUsuario = new Scanner(System.in);
    private ConsumoDatosAPI consumoDatosApi = new ConsumoDatosAPI();
    private final String URL_INICIAL = "https://gutendex.com/books/?search=";
    private String URL_FINAL;
    private ConversionDatos conversionDatos = new ConversionDatos();
    private List<DatosLibro> datosLibro = new ArrayList<>();
    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;
    private List<Libro> librosLocales;
    private List<Autor> autoresLocales;
    private Optional<Libro> libroBuscado;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepositorio = libroRepository;
        this.autorRepositorio = autorRepository;
    }

    public void desplegarMenu() {
        var opcionMenu = -1;
        while (opcionMenu != 0){
            var menuPrincipal = """
                    1 - Buscar libros por Título
                    2 - Lista de Libros almacenados Localmente
                    3 - Lista de Autores almacenados Localmente
                    4 - Lista de Autores, según Fechas de Actividad
                    5 - Lista de Libros Locales, separados por Idioma
                    0 - SALIR
                    """;
            System.out.println(menuPrincipal);
            opcionMenu = entradaUsuario.nextInt();
            entradaUsuario.nextLine();

            switch (opcionMenu){
                case 1:
                    almacenarLibroLocalmente();
                    break;
                case 2:
                    mostrarLibrosLocales();
                    break;
                case 3:
                    mostrarAutoresLocales();
                    break;
                case 4:
                    buscarAutoresPorFecha();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("\nCerrando la aplicación");
                    System.out.println("...\n");
                    break;
                default:
                    System.out.println("""
                            \nXXXXXXXXXXXXXXXXXXXX
                            Opción
                            NO VÁLIDA
                            XXXXXXXXXXXXXXXXXXXX\n
                            """);
            }
        }
    }

    private Libro buscarLibroEnAPI(){
        System.out.println("¿De qué libro deseas conocer?");
        var nombreLibro = entradaUsuario.nextLine();
        URL_FINAL = nombreLibro.toLowerCase().replace(" ","%20");
        var json =  consumoDatosApi.obtenerDatos(URL_INICIAL+URL_FINAL);
        DatosGenerales datosBusqueda = conversionDatos.obtenerDatos(json, DatosGenerales.class);

        if (datosBusqueda.getResultados().isEmpty()){
            System.out.println("Lo sentimos, ese libro no está disponible en nuestro catálogo");
            return null;
        } else {
            System.out.println("¡Aquí Lo Tienes!");
            DatosLibro libroBuscado = datosBusqueda.getResultados().get(0);
            return new Libro(libroBuscado);
        }
    }

    private void almacenarLibroLocalmente(){
        Libro libroBuscado = buscarLibroEnAPI();

        if (libroBuscado == null) {
            System.out.println("Título NO DISPONIBLE");
        } else {
            boolean duplicado = libroRepositorio.existsByLibroID(libroBuscado.getLibroID());

            if(duplicado){
                System.out.println("Libro disponible LOCALMENTE");
            } else {
                libroRepositorio.save(libroBuscado);
                System.out.println(libroBuscado.toString());
            }
        }
    }

    private void mostrarLibrosLocales(){
        librosLocales = libroRepositorio.findAll();

        librosLocales.stream()
                .forEach(System.out::println);
    }

    private void mostrarAutoresLocales(){
        autoresLocales = autorRepositorio.findAll();

        autoresLocales.stream()
                .forEach(System.out::println);
    }

    private void buscarAutoresPorFecha(){
        System.out.println("¿De qué año buscas al autor?");
        var fechaBuscada = entradaUsuario.nextInt();
        entradaUsuario.nextLine();
        List<Autor> autoresPorFecha = autorRepositorio.autoresPorFechaActividad(fechaBuscada);
        autoresPorFecha.stream()
                .forEach(System.out::println);
    }

    private void buscarLibrosPorIdioma(){
        var opcionMenu = -1;
        while (opcionMenu != 0){
            var menuPrincipal = """
                    1 - Libros en Inglés    [en]
                    2 - Libros en Español   [es]
                    3 - Libros en Francés   [fr]
                    4 - Libros en Portugués [pt]
                    0 - REGRESAR
                    """;
            System.out.println(menuPrincipal);
            opcionMenu = entradaUsuario.nextInt();
            entradaUsuario.nextLine();

            switch (opcionMenu){
                case 1:
                    mostrarTitulosIngles();
                    break;
                case 2:
                    mostrarTitulosSpanish();
                    break;
                case 3:
                    mostrarTitulosFrench();
                    break;
                case 4:
                    mostrarTitulosPort();
                    break;
                case 0:
                    System.out.println("\nRegresando al menú anterior");
                    System.out.println("<---\n");
                    break;
                default:
                    System.out.println("""
                            \nXXXXXXXXXXXXXXXXXXXX
                            Opción
                            NO VÁLIDA
                            XXXXXXXXXXXXXXXXXXXX\n
                            """);
            }
        }
    }

    private void mostrarTitulosIngles(){
        librosLocales = libroRepositorio.findAll();

        System.out.println("\nHave a go with our english titles!");
        librosLocales.stream()
                .filter(L -> L.getTraduccionesDisponibles().contains("en"))
                .forEach(System.out::println);
    }

    private void mostrarTitulosSpanish(){
        librosLocales = libroRepositorio.findAll();

        System.out.println("\n¡Disfruta lo mejor en el idioma de Cervantes!");
        librosLocales.stream()
                .filter(L -> L.getTraduccionesDisponibles().contains("es"))
                .forEach(System.out::println);
    }

    private void mostrarTitulosFrench(){
        librosLocales = libroRepositorio.findAll();

        System.out.println("\nTítulos en Francés");
        librosLocales.stream()
                .filter(L -> L.getTraduccionesDisponibles().contains("fr"))
                .forEach(System.out::println);
    }

    private void mostrarTitulosPort(){
        librosLocales = libroRepositorio.findAll();

        System.out.println("\nTítulos en Portugués");
        librosLocales.stream()
                .filter(L -> L.getTraduccionesDisponibles().contains("pt"))
                .forEach(System.out::println);
    }

}
