package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductoRequestDTO {
    // @NotBlank verifica que no sea nulo y que no esté vacío (ni lleno de espacios)
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    private String descripcion;

    // @NotNull verifica que el campo venga en el JSON
    @NotNull(message = "El precio es obligatorio")
    // @Min asegura que no existan precios negativos
    @Min(value = 0, message = "El precio no puede ser menor a 0")
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stockActual;

    private String imagenUrl;

    private Boolean enOferta;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Integer idCategoria;
}
