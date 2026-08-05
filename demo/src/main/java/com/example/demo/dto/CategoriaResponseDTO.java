package com.example.demo.dto;

public class CategoriaResponseDTO {

    private Integer idCategoria;
    private String nombre;
    private String descripcion;
    private String nombreCategoriaPadre; // Mostramos solo el nombre en texto del padre, no el objeto entero
    private Boolean estado;

    // Constructor vacío
    public CategoriaResponseDTO() {
    }

    // Constructor para mapear fácilmente desde la Entidad
    public CategoriaResponseDTO(Integer idCategoria, String nombre, String descripcion, String nombreCategoriaPadre, Boolean estado) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nombreCategoriaPadre = nombreCategoriaPadre;
        this.estado = estado;
    }

    // ==========================================
    // Getters y Setters
    // ==========================================
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreCategoriaPadre() {
        return nombreCategoriaPadre;
    }

    public void setNombreCategoriaPadre(String nombreCategoriaPadre) {
        this.nombreCategoriaPadre = nombreCategoriaPadre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}