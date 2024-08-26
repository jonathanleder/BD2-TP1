package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PromocionDeProducto extends Promocion {

    private List<String> marcaDeProductos=new ArrayList<>();
    private static double DESCUENTO_DE_PRODUCTO = 5.0;


    public PromocionDeProducto(LocalDateTime fechaInicio, LocalDateTime fechaFin, String unaMarca) {
        super(fechaInicio, fechaFin, new Descuento(DESCUENTO_DE_PRODUCTO));
        this.marcaDeProductos.add(Objects.requireNonNull(unaMarca));
    }

    public void agregarMarca(String marca){
        if(this.marcaDeProductos.equals(marca)){
            throw new IllegalArgumentException("Esa marca ya tiene promo");
        }
        this.marcaDeProductos.add(marca);
    }
    @Override
    protected boolean tienePromo(String dato) {
        return this.marcaDeProductos.stream().anyMatch(marca -> marca.equals(dato));
    }
    @Override
    protected double aplicarDescuento(double precio) {
        return descuento.obtenerDescuento(precio);
    }


}
