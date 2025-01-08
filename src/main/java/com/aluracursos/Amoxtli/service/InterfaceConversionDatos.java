package com.aluracursos.Amoxtli.service;

public interface InterfaceConversionDatos {

    <T> T obtenerDatos(String json, Class<T> clase);

}
