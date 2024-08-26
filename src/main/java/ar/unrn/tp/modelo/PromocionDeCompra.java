package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class PromocionDeCompra extends Promocion{
    private Tarjeta tardejaDeDescuento;
    private static double DESCUENTO_DE_COMPRA = 8.0;


    public PromocionDeCompra(LocalDateTime fechaInicio, LocalDateTime fechaFin,Tarjeta unaTarjeta) {
        super(fechaInicio, fechaFin, new Descuento(DESCUENTO_DE_COMPRA));
        this.tardejaDeDescuento= unaTarjeta;
    }

    @Override
    protected boolean tienePromo(String dato) {
        return dato.equals(this.tardejaDeDescuento.tipoDeTarjeta());
    }

    @Override
    protected double aplicarDescuento(double precio) {
        return descuento.obtenerDescuento(precio);
    }
}
