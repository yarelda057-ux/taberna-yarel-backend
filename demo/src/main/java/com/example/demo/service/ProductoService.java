package com.example.demo.service;

import com.example.demo.dto.ProductoRequestDTO;
import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.entity.Categoria;
import com.example.demo.entity.Producto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    // Inyección de dependencias por constructor (Buena práctica)
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoResponseDTO> obtenerTodosLosProductos() {
        List<Producto> productos = productoRepository.findAll();

        // Transformamos cada Entidad Producto a un ProductoResponseDTO
        return productos.stream().map(prod -> {
            String nombreCategoria = null;
            // Validamos que la categoría no sea nula antes de obtener su nombre
            if (prod.getCategoria() != null) {
                nombreCategoria = prod.getCategoria().getNombre();
            }

            return new ProductoResponseDTO(
                    prod.getIdProducto(),
                    prod.getNombre(),
                    prod.getDescripcion(),
                    prod.getPrecio(),
                    prod.getStockActual(),
                    prod.getImagenUrl(),
                    prod.getEnOferta(),
                    prod.getEstado(),
                    nombreCategoria
            );
        }).collect(Collectors.toList());
    }
    public ProductoResponseDTO crearProducto(ProductoRequestDTO requestDTO) {
        // 1. Buscar la categoría por ID. Si no existe, lanzamos un error.
        Categoria categoria = categoriaRepository.findById(requestDTO.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el ID: " + requestDTO.getIdCategoria()));

        // 2. Crear la entidad Producto y llenarla con los datos del RequestDTO
        Producto nuevoProducto = new Producto();
        nuevoProducto.setNombre(requestDTO.getNombre());
        nuevoProducto.setDescripcion(requestDTO.getDescripcion());
        nuevoProducto.setPrecio(requestDTO.getPrecio());
        nuevoProducto.setStockActual(requestDTO.getStockActual());
        nuevoProducto.setImagenUrl(requestDTO.getImagenUrl());
        nuevoProducto.setEnOferta(requestDTO.getEnOferta());
        nuevoProducto.setEstado(true); // Por defecto, al crearlo está activo
        nuevoProducto.setCategoria(categoria); // Asignamos la categoría encontrada

        // 3. Guardar en la base de datos
        Producto productoGuardado = productoRepository.save(nuevoProducto);

        // 4. Retornar el ResponseDTO (el JSON limpio) como confirmación
        return new ProductoResponseDTO(
                productoGuardado.getIdProducto(),
                productoGuardado.getNombre(),
                productoGuardado.getDescripcion(),
                productoGuardado.getPrecio(),
                productoGuardado.getStockActual(),
                productoGuardado.getImagenUrl(),
                productoGuardado.getEnOferta(),
                productoGuardado.getEstado(),
                productoGuardado.getCategoria().getNombre()
        );
    }
}