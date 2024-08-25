package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Carrito {
    private Cliente cliente;
    private List<Producto> items;
    private Promocion promocionDeCompra;
    private Promocion promocionDeProducto;
    private Tarjeta tarjetaSeleccionada;


    public Carrito(Cliente cliente, Tarjeta tarjetaAUtilizar){
        this.cliente= Objects.requireNonNull(cliente);
        this.items= new ArrayList<>();
        this.promocionDeCompra=null;
        this.promocionDeProducto=null;
        if (!ServicioPago.validarTarjeta(tarjetaAUtilizar))
            throw new RuntimeException("La tarjeta debe ser valida");
        this.tarjetaSeleccionada=tarjetaAUtilizar;
    }

    public void agregarPromoDeCompra(PromocionDeCompra promocion){
        this.promocionDeCompra=Objects.requireNonNull(promocion);
    }
    public void agregarPromoDeProducto(PromocionDeProducto promocion){
        this.promocionDeProducto=Objects.requireNonNull(promocion);
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
    public double calcularMontoConPromos() {
        double montoTotal = 0;

        for (Producto producto : items) {
            double precioConDescuento = producto.getPrecio();

            if (promocionDeProducto.tienePromo(producto.obtenerMarca()) && promocionDeProducto.estaVigente()) {
                precioConDescuento = promocionDeProducto.aplicarPromocion(precioConDescuento);
            }
            montoTotal += precioConDescuento;
        }
        if (promocionDeCompra.tienePromo(tarjetaSeleccionada.tipoDeTarjeta()) && promocionDeCompra.estaVigente()) {

            montoTotal = promocionDeCompra.aplicarPromocion(montoTotal);
        }
        return montoTotal;
    }
    public double calcularMontoConPromoDeProducto(){
        double montoTotal = 0;

        for (Producto producto : items) {
            double precioConDescuento = producto.getPrecio();

            if (promocionDeProducto.tienePromo(producto.obtenerMarca())) {
                precioConDescuento = promocionDeProducto.aplicarPromocion(precioConDescuento);
            }
            montoTotal += precioConDescuento;
        }
        return montoTotal;
    }
    public double calcularMontoConPromoDeCompra(){
        double montoTotal = calcularMontoSinPromos();

        return this.promocionDeCompra.aplicarPromocion(montoTotal);
    }

    public Venta realizarPago(){
        this.cliente.realizarPago(this.calcularMontoConPromos(),this.tarjetaSeleccionada);
        List<Promocion> promos= new ArrayList<>();
        promos.add(this.promocionDeProducto);
        promos.add(this.promocionDeCompra);
        return new Venta(this.cliente,this.items,this.calcularMontoConPromos(),promos);
    }
}
