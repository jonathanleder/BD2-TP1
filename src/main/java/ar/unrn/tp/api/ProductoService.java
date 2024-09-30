package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Producto;

import java.util.List;

public interface ProductoService {
    void crearProducto(String codigo, String descripcion, float precio, Long idCategoría, Long idMarca);
    void modificarProducto(Long idProducto, String descripcion, float precio);
    List<Producto> listarProductos();
}

