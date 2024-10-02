package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Categoria;

import java.util.List;

public interface CategoriaService {
    void crearCategoria(String categoria);

    List<Categoria> listarCategorias();
}
