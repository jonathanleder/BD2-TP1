package ar.unrn.tp.modelo;
import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import lombok.Getter;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;
import java.util.Objects;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "Producto")
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

    private String descripcion = "";
    private float precio;

    @Version
    private Integer version;//para gestionar las versiones


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


    public Producto(String codigo,String descripcion, Categoria unaCategoria,Marca marca, float precio, Integer version) throws ProductoInvalidoExcepcion {

        this.codigo = Objects.requireNonNull(codigo,"El codigo no debe estar repetido");
        this.descripcion=Objects.requireNonNull(descripcion,"Debe ingresar una descripcion Valida");
        this.categoria=Objects.requireNonNull(unaCategoria,"Debe ingresar una categoria valida");
        if(precio<=0) {
            throw new IllegalArgumentException("El precio debe ser un valor valido");
        }
        this.precio=precio;
        this.version=version;
        this.marca=Objects.requireNonNull(marca);
    }





    public String obtenerMarca(){
        return this.marca.getNombre();
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

    @Override
    public String toString(){
        return "Nombre: "+ this.getDescripcion() + "\n Marca :" + this.getMarca().getNombre() + "\n Categoria: "+this.getCategoria().getNombre();
    }

}
