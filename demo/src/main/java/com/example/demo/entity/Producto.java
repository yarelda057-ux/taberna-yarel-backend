package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    // CORREGIDO: Coincide con tu BD "stock_actual"
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    // NUEVO: Agregado de tu BD
    @Column(name = "imagen_url", length = 255)
    private String imagenUrl;

    // NUEVO: Agregado de tu BD (Spring boot mapea el 0/1 de MySQL a Boolean automáticamente)
    @Column(name = "en_oferta")
    private Boolean enOferta;

    @Column(name = "estado")
    private Boolean estado = true;

    // Relación con Categoria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

}