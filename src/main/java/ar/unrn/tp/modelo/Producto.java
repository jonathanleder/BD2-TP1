package model;

import java.util.Objects;

public class Producto {
    private int codigo;
    private String descripcion;
    private String categoria;
    private String marca;
    private double precio;

    public Producto(int codigo, String descripcion, String unaCategoria, Double precio, String marca){
        this.codigo= Objects.requireNonNull(codigo);
        this.descripcion=Objects.requireNonNull(descripcion,"Debe ingresar una descripcion Valida");
        this.categoria=Objects.requireNonNull(unaCategoria,"Debe ingresar una categoria valida");
        if(precio<=0 || precio==null) {
            throw new IllegalArgumentException("El precio debe ser un valor valido");
        }
        this.precio=precio;
        this.marca=Objects.requireNonNull(marca);
    }

    protected double getPrecio(){
        return this.precio;
    }
    public String obtenerMarca(){
        return this.marca;
    }
    public String descripcion(){
        return this.descripcion;
    }
    public String categoria(){
        return this.categoria;
    }
}
