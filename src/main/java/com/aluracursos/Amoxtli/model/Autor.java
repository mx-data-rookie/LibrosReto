package com.aluracursos.Amoxtli.model;

import com.aluracursos.Amoxtli.model.record.DatosAutor;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autor")

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libro;

    public Autor(){
    }

    public Autor(DatosAutor datosAutorAutor){
        this.nombre = datosAutorAutor.nombre();
        this.fechaNacimiento = datosAutorAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutorAutor.fechaFallecimiento();
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

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibro() {
        return libro;
    }

    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }

    @Override
    public String toString() {
        return
                '\n' + "++++++++++Autor++++++++++" + '\n' +
                        "Nombre: " + nombre + '\n' +
                        "Año de Nacimiento: " + fechaNacimiento + '\n'+
                        "Año de Muerte: " + fechaFallecimiento + '\n' +
                        "+++++++++++++++++++++++++" + '\n';
    }
}
