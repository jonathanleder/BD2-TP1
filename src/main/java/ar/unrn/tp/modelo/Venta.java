package ar.unrn.tp.modelo;


import ar.unrn.tp.modelo.Descuento;
import ar.unrn.tp.modelo.Producto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime fechaHora;
    @OneToOne
    Cliente cliente;
    @OneToOne
    Tarjeta tarjeta;
    @OneToMany(cascade={CascadeType.ALL})
    private List<Producto> items;

    @OneToMany(cascade={CascadeType.ALL})
    private List<Descuento> promos;

    private float montoTotal;



    public Venta(Cliente unCliente,List<Producto> productos,float montoFinal,List<Descuento> promos, Tarjeta TarjetaSeleccionada){
        this.cliente= Objects.requireNonNull(unCliente);
        this.fechaHora=LocalDateTime.now();
        this.items=productos;
        this.montoTotal=montoFinal;
        this.tarjeta=TarjetaSeleccionada;

    }

    public List<Producto>getProductos(){
        return items;
    }

    public int cantidadDeProductos(){
        return items.size();
    }
    public Long id(){
        return this.id;
    }

    public float montoTotal(){
        return this.montoTotal;
    }



}