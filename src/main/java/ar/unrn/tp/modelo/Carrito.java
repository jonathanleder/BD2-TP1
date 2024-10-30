package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import ar.unrn.tp.excepciones.TarjetaInvalidaExcepcion;
import ar.unrn.tp.modelo.Descuento;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.ServicioPago;
import ar.unrn.tp.modelo.Tarjeta;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Carrito {

    private Long id;
    private Cliente cliente;
    private List<Producto> items;
    private List<Descuento> descuentosDeVentas;
    @Setter
    private Tarjeta tarjetaSeleccionada;
    private ServicioPago servicioPago;



    public Carrito(Cliente cliente, List<Producto> productos, List<Descuento> promociones, ServicioPago servicioTarjetas, Tarjeta tarjetaSeleccionada) {
        this.cliente = cliente;
        this.items = productos;
        this.descuentosDeVentas = promociones;
        this.servicioPago = servicioTarjetas;
        this.tarjetaSeleccionada = tarjetaSeleccionada;
    }

    public Carrito(List<Producto> listaProductos, List<Descuento> promociones, ServicioPago servicioValidadorTarjetas) {
        this.items = listaProductos;
        this.descuentosDeVentas = promociones;
        this.servicioPago = servicioValidadorTarjetas;
    }




    public void agregarAlCarrito(Producto unProducto){
        this.items.add(unProducto);
    }

    public double calcularMontoSinPromos(){
        double monto=0;
        for (Producto unProducto : items){
            monto+= unProducto.getPrecio();
        }
        return monto;
    }
    public float calcularMontoConPromos() {
        float montoTotal = 0;
        float descuentoDeCompra = 0;


        for (Producto producto : items) {
            float precioConDescuento = producto.getPrecio();
            for (Descuento descuento : descuentosDeVentas) {
                //System.out.println(" Descripcion del descuento: "+ descuento.getDescripcion() +
                 //       "\n el descuento es de: "+ descuento.getDescuento() + "\n La marca es: "+descuento.marca());
                System.out.println("nombre del descuento: "+descuento.marca() + "nombre de producto: "+producto.obtenerMarca());
                if (descuento.tienePromo(producto.getDescripcion()) && descuento.estaVigente()) {
                    System.out.println("\nel precio que tiene descuento es: "+precioConDescuento+
                            "que es el producto: "+producto.obtenerMarca());
                    precioConDescuento = descuento.aplicarDescuento(precioConDescuento);
                    System.out.println("\nPrecio con descuento despues de aplicarlo: "+precioConDescuento);
                }
                if (descuento.tienePromo(tarjetaSeleccionada.getTipoTarjeta()) && descuento.estaVigente()) {

                    descuentoDeCompra = descuento.descuento();
                }
            }
            montoTotal += precioConDescuento;
        }

        if (descuentoDeCompra != 0)
            montoTotal = montoTotal - (montoTotal * descuentoDeCompra); // Aplicar el descuento como porcentaje
       // System.out.println("El monto total es: "+ montoTotal);
        return montoTotal;
    }

    public Venta realizarPago() throws TarjetaInvalidaExcepcion, ProductoInvalidoExcepcion {

        if (servicioPago.validarTarjeta(this.tarjetaSeleccionada))
            return new Venta(this.cliente,this.items,this.calcularMontoConPromos(),descuentosDeVentas, this.tarjetaSeleccionada);
        throw new TarjetaInvalidaExcepcion();
    }

}
