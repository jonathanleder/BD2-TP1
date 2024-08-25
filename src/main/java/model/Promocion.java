package model;

import model.Descuento;
import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Promocion {

    protected LocalDateTime fechaInicio;
    protected LocalDateTime fechaFin;
    protected Descuento descuento;

    public static String FECHA_INVALIDA="Las fechas no se pueden superponer o la fecha inicio no puede ser superior a la fecha de fin";

    public Promocion(LocalDateTime fechaInicio,LocalDateTime fechaFin,Descuento descuentoPromocion){
        if(fechaInicio.isAfter(fechaFin)){
            throw new IllegalArgumentException(FECHA_INVALIDA);
        }
        if (fechaInicio.isEqual(fechaFin))
            throw new IllegalArgumentException(FECHA_INVALIDA);
        this.fechaInicio= Objects.requireNonNull(fechaInicio);
        this.fechaFin=Objects.requireNonNull(fechaFin);
        this.descuento=Objects.requireNonNull(descuentoPromocion);
    }

    protected abstract boolean tienePromo(String dato);

    public double aplicarPromocion(double precio){
        return aplicarDescuento(precio);
    }
    protected abstract double aplicarDescuento(double precio);

    protected boolean estaVigente(){
        return LocalDateTime.now().isAfter(this.fechaInicio) && LocalDateTime.now().isBefore(this.fechaFin);
    }

}
