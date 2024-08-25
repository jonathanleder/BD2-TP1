package model;

import java.util.Objects;

public class Descuento {
    private double descuento;

    public Descuento(double descuentoEnPorcentaje){
        if(descuentoEnPorcentaje < 0 || descuentoEnPorcentaje > 100)
            throw new IllegalArgumentException("El valor del descuento no puede superar el 100% o ser menor a 1");
        this.descuento= (Objects.requireNonNull(descuentoEnPorcentaje)/100);
    }


    protected double obtenerDescuento(double precio){
        return precio-(precio*this.descuento);
    }
}
