package com.example.demo.dto;


import lombok.Data;

@Data
public class ProductoRequestDTO {
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stockActual;
    private String imagenUrl;
    private Boolean enOferta;

    // Solo necesitamos recibir el ID de la categoría a la que pertenecerá
    private Integer idCategoria;
}
