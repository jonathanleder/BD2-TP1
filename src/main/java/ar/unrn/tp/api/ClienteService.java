package ar.unrn.tp.api;

import ar.unrn.tp.modelo.Tarjeta;

import java.util.List;

public interface ClienteService {
    void crearCliente(String nombre, String apellido, String dni, String email);
    void modificarCliente(Long idCliente, String nombre);
    void agregarTarjeta(Long idCliente, String nro, String marca);
    List<Tarjeta> listarTarjetas(Long idCliente);
    public void datosCliente(Long idCliente);
}


