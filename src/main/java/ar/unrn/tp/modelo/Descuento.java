package model;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Promocion {

    protected LocalDateTime fechaInicio;
    protected LocalDateTime fechaFin;
    protected float descuento;

    public static String FECHA_INVALIDA="Las fechas no se pueden superponer o la fecha inicio no puede ser superior a la fecha de fin";

    public Promocion(LocalDateTime fechaInicio,LocalDateTime fechaFin,float descuentoEnPorcentaje){
        if(fechaInicio.isAfter(fechaFin)){
            throw new IllegalArgumentException(FECHA_INVALIDA);
        }
        if (fechaInicio.isEqual(fechaFin))
            throw new IllegalArgumentException(FECHA_INVALIDA);
        this.fechaInicio= Objects.requireNonNull(fechaInicio);
        this.fechaFin=Objects.requireNonNull(fechaFin);
        if(descuentoEnPorcentaje < 0 || descuentoEnPorcentaje > 100)
            throw new IllegalArgumentException("El valor del descuento no puede superar el 100% o ser menor a 1");
        this.descuento= (Objects.requireNonNull(descuentoEnPorcentaje)/100);
    }

    protected abstract boolean tienePromo(String dato);

    public double aplicarPromocion(double precio){
        return aplicarDescuento(precio);
    }


    protected abstract double aplicarDescuento(double precio);

    protected boolean estaVigente(){
        return LocalDateTime.now().isAfter(this.fechaInicio) && LocalDateTime.now().isBefore(this.fechaFin);
    }
    private double obtenerDescuento(double precio){
        return precio-(precio*this.descuento);
    }

}
