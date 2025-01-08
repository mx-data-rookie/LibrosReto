package com.aluracursos.Amoxtli.model;

import com.aluracursos.Amoxtli.model.record.DatosLibro;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libro")

public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long libroID;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "autorID")
    private Autor autor;
    private List<String> traduccionesDisponibles;
    private Integer conteoDeDescargas;

    public Libro(){
    }

    public Libro(DatosLibro datosLibro){
        this.libroID = datosLibro.libroID();
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autor().get(0));
        this.traduccionesDisponibles = datosLibro.traduccionesDisponibles();
        this.conteoDeDescargas = datosLibro.conteoDeDescargas();
    }

    public Libro(Libro libro){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLibroID() {
        return libroID;
    }

    public void setLibroID(Long libroID) {
        this.libroID = libroID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getTraduccionesDisponibles() {
        return traduccionesDisponibles;
    }

    public void setTraduccionesDisponibles(List<String> traduccionesDisponibles) {
        this.traduccionesDisponibles = traduccionesDisponibles;
    }

    public Integer getConteoDeDescargas() {
        return conteoDeDescargas;
    }

    public void setConteoDeDescargas(Integer conteoDeDescargas) {
        this.conteoDeDescargas = conteoDeDescargas;
    }

    @Override
    public String toString(){
        return
                '\n' + "++++++++++Libro++++++++++" + '\n' +
                "Titulo: " + titulo + '\n' +
                "No. de Catálogo: " + libroID + '\n'+
                "Autor: " + autor.getNombre() + '\n' +
                "Idioma: " + traduccionesDisponibles + '\n' +
                "No. de Descargas: " + conteoDeDescargas + '\n' +
                "+++++++++++++++++++++++++" + '\n';
    }

}
