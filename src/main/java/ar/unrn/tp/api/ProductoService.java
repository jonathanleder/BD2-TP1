package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Producto;

import java.util.List;

public interface ProductoService {
    void crearProducto(String codigo, String descripcion, float precio, Long idCategoría, Long idMarca);
    void modificarProducto(Long idProducto, String codigo, String descripcion, float precio, Long idCategoría, Long idMarca, Integer version);
    List<Producto> listarProductos();
    Producto buscarProducto(Long idProducto);
    void eliminarTodosLosProductos();
}

