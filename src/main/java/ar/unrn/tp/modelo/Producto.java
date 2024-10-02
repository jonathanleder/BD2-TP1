package ar.unrn.tp.modelo;
import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.jdo.annotations.Unique;
import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue
    private long id;


    @Column( name="codigo",unique = true)
    private String codigo;
    @ManyToOne
    private Marca marca;
    @ManyToOne
    private Categoria categoria;

    private String descripcion;
    private float precio;


    public Producto(String codigo,String descripcion, Categoria unaCategoria,Marca marca, float precio) throws ProductoInvalidoExcepcion {

        this.codigo = Objects.requireNonNull(codigo,"El codigo no debe estar repetido");
        this.descripcion=Objects.requireNonNull(descripcion,"Debe ingresar una descripcion Valida");
        this.categoria=Objects.requireNonNull(unaCategoria,"Debe ingresar una categoria valida");
        if(precio<=0) {
            throw new IllegalArgumentException("El precio debe ser un valor valido");
        }
        this.precio=precio;
        this.marca=Objects.requireNonNull(marca);
    }




    public float getPrecio(){
        return this.precio;
    }
    public String obtenerMarca(){
        return this.marca.marca();
    }
    public String descripcion(){
        return this.descripcion;
    }
    public Categoria categoria(){
        return this.categoria;
    }

    public void actualizarCodigo(String codigo) {
        this.codigo = codigo;
    }


    public void actualizarDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void actualizarMarca(Marca marca) {
        this.marca = marca;
    }

    public void actualizarPrecio(float precio) {
        this.precio = precio;
    }

    public void actualizarCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    public Long id(){
        return this.id;
    }


    public boolean esIgualA(Producto productoResultado) {
        return this.codigo.equals(productoResultado.codigo);
    }


}
