package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Marca;

import java.util.List;

public interface MarcaService {
    void crearMarca(String acme);

    List<Marca> listarMarcas();
}
