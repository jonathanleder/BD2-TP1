package model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Venta {
    private LocalDateTime fechaHora;
    private Cliente cliente;
    private List<Producto> items;
    private double montoTotal;
    private List<Promocion> promocionesAplicadas;

    public Venta(Cliente unCliente,List<Producto> productos,double montoFinal,List<Promocion> promos){
        this.cliente= Objects.requireNonNull(unCliente);
        this.fechaHora=LocalDateTime.now();
        this.items=productos;
        this.montoTotal=montoFinal;
        this.promocionesAplicadas=promos;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public List<Producto> getItems() {
        return items;
    }

    public List<Promocion> getPromocionesAplicadas() {
        return promocionesAplicadas;
    }

    public Cliente getCliente() {
        return cliente;
    }
}