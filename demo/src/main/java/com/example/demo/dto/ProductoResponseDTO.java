package com.example.demo.dto;

public class ProductoResponseDTO {

    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stockActual;
    private String imagenUrl;
    private Boolean enOferta;
    private Boolean estado;

    // Aquí está la magia: solo enviamos el nombre de la categoría, no el objeto entero.
    private String nombreCategoria;

    public ProductoResponseDTO() {
    }

    public ProductoResponseDTO(Integer idProducto, String nombre, String descripcion, Double precio, Integer stockActual, String imagenUrl, Boolean enOferta, Boolean estado, String nombreCategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stockActual = stockActual;
        this.imagenUrl = imagenUrl;
        this.enOferta = enOferta;
        this.estado = estado;
        this.nombreCategoria = nombreCategoria;
    }

    // ==========================================
    // Getters y Setters
    // ==========================================
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Boolean getEnOferta() {
        return enOferta;
    }

    public void setEnOferta(Boolean enOferta) {
        this.enOferta = enOferta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}