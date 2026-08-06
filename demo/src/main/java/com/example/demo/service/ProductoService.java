package com.example.demo.service;

import com.example.demo.dto.ProductoRequestDTO;
import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.entity.Categoria;
import com.example.demo.entity.Producto;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository; // Añadimos el repositorio de Categoría

    // Actualizamos el constructor para inyectar ambos repositorios
    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // Reemplaza el método obtenerTodosLosProductos() por esta versión:
    public Page<ProductoResponseDTO> obtenerTodosLosProductos(int page, int size, String sortBy) {

        // Creamos el objeto de paginación y ordenamiento
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        // Ejecutamos la consulta paginada
        Page<Producto> productosPage = productoRepository.findAllByEstadoTrue(pageable);

        // Mapeamos de Entidad a DTO directamente desde el objeto Page
        return productosPage.map(prod -> {
            String nombreCategoria = prod.getCategoria() != null ? prod.getCategoria().getNombre() : null;
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
        });
    }

    // NUEVO MÉTODO PARA POST
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

    // NUEVO MÉTODO PARA PUT (Actualizar)
    public ProductoResponseDTO actualizarProducto(Integer idProducto, ProductoRequestDTO requestDTO) {
        // 1. Buscar el producto existente. Si no existe, lanzamos un error.
        Producto productoExistente = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + idProducto));

        // 2. Buscar la categoría solicitada por ID.
        Categoria categoria = categoriaRepository.findById(requestDTO.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el ID: " + requestDTO.getIdCategoria()));

        // 3. Actualizar los datos de la entidad con los datos del DTO
        productoExistente.setNombre(requestDTO.getNombre());
        productoExistente.setDescripcion(requestDTO.getDescripcion());
        productoExistente.setPrecio(requestDTO.getPrecio());
        productoExistente.setStockActual(requestDTO.getStockActual());
        productoExistente.setImagenUrl(requestDTO.getImagenUrl());
        productoExistente.setEnOferta(requestDTO.getEnOferta());
        productoExistente.setCategoria(categoria);
        // Nota: No actualizamos el 'estado' aquí, eso lo haremos en la eliminación lógica.

        // 4. Guardar los cambios en la base de datos
        Producto productoActualizado = productoRepository.save(productoExistente);

        // 5. Retornar el DTO de respuesta
        return new ProductoResponseDTO(
                productoActualizado.getIdProducto(),
                productoActualizado.getNombre(),
                productoActualizado.getDescripcion(),
                productoActualizado.getPrecio(),
                productoActualizado.getStockActual(),
                productoActualizado.getImagenUrl(),
                productoActualizado.getEnOferta(),
                productoActualizado.getEstado(),
                productoActualizado.getCategoria().getNombre()
        );
    }

    // NUEVO MÉTODO PARA ELIMINACIÓN LÓGICA (DELETE)
    public void eliminarProducto(Integer idProducto) {
        // 1. Buscar el producto existente
        Producto productoExistente = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + idProducto));

        // 2. Cambiar el estado a inactivo (false) en lugar de eliminar el registro
        productoExistente.setEstado(false);

        // 3. Guardar los cambios
        productoRepository.save(productoExistente);
    }

    // NUEVO MÉTODO PARA ACTIVAR UN PRODUCTO QUE ELIMINAMOS
    public void activarProducto(Integer idProducto) {
        // 1. Buscar el producto existente
        Producto productoExistente = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + idProducto));

        // 2. Cambiar el estado a inactivo (false) en lugar de eliminar el registro
        productoExistente.setEstado(true);

        // 3. Guardar los cambios
        productoRepository.save(productoExistente);
    }

    // NUEVO MÉTODO PARA FILTRAR POR CATEGORÍA
    public List<ProductoResponseDTO> obtenerProductosPorCategoria(Integer idCategoria) {
        // Usamos el nuevo Query Method de nuestro repositorio
        List<Producto> productos = productoRepository.findByCategoria_IdCategoriaAndEstadoTrue(idCategoria);

        // Reutilizamos la misma lógica de mapeo que ya conoces
        return productos.stream().map(prod -> {
            String nombreCategoria = prod.getCategoria() != null ? prod.getCategoria().getNombre() : null;
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

    public List<ProductoResponseDTO> buscarProductosPorNombre(String termino) {
        List<Producto> productos = productoRepository.buscarPorNombreActivo(termino);

        return productos.stream().map(prod -> {
            String nombreCategoria = prod.getCategoria() != null ? prod.getCategoria().getNombre() : null;
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
}