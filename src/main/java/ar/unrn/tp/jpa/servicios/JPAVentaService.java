package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import ar.unrn.tp.excepciones.TarjetaInvalidaExcepcion;
import ar.unrn.tp.modelo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class JPAVentaService extends JPAGenericService implements VentaService {
    private ServicioPago servicioValidadorTarjetas;

    private DescuentoService descuentoService;


    public JPAVentaService(ServicioPago servicioValidadorTarjetas, DescuentoService descuentoService, EntityManagerFactory emf){
        super(emf);
        this.descuentoService = descuentoService;
        this.servicioValidadorTarjetas = servicioValidadorTarjetas;
    }
    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {
        inTransactionExecute((em) -> {
            List<Descuento> promociones = this.descuentoService.recuperarDescuentos();
            List<Producto> listaProductos = em.createQuery("SELECT o FROM Producto o WHERE o.id IN :ids", Producto.class).setParameter("ids", productos).getResultList();
            System.out.println("productos: " +listaProductos.size());
            try {
                Venta venta = new Carrito(em.getReference(Cliente.class,idCliente),listaProductos,promociones,servicioValidadorTarjetas,em.getReference(Tarjeta.class,idTarjeta)).realizarPago();
                System.out.println(venta.cantidadDeProductos());
                em.persist(venta);

            } catch (TarjetaInvalidaExcepcion | ProductoInvalidoExcepcion e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public float calcularMonto(List<Long> productos, Long idTarjeta) {
        AtomicReference<Float> monto = new AtomicReference<>(0F);
        inTransactionExecute((em) -> {
            Tarjeta tarjetaCredito = em.find(Tarjeta.class, idTarjeta);
            List<Producto> listaProductos = em.createQuery("SELECT o FROM Producto o WHERE o.id IN :ids", Producto.class).setParameter("ids", productos).getResultList();
            List<Descuento> promociones = this.descuentoService.recuperarDescuentos();
            Carrito carrito = new Carrito(listaProductos, promociones, this.servicioValidadorTarjetas);
            carrito.setTarjetaSeleccionada(tarjetaCredito);
            monto.set(carrito.calcularMontoConPromos());
        });
        return monto.get();
    }
/*
    @Override
    public float calcularMonto(List<Long> productos, Long idTarjeta) {
        AtomicReference<Float> monto = new AtomicReference<>(0F);
        inTransactionExecute((em) -> {
            Tarjeta tarjetaCredito = em.find(Tarjeta.class, idTarjeta);
            List<Producto> listaProductos = em.createQuery("SELECT o FROM Producto o WHERE o.id IN :ids", Producto.class).setParameter("ids", productos).getResultList();
            List<Descuento> promociones = this.descuentoService.recuperarDescuentos();
            monto.set(montoTotal(tarjetaCredito,listaProductos,promociones));
        });
        return monto.get();
    }*/

    private float montoTotal(Tarjeta tarjeta, List<Producto> productos, List<Descuento> descuentos){

        float montoTotal = 0;
        float descuentoDeCompra=0;

        for (Producto producto : productos) {
            float precioConDescuento = producto.getPrecio();
            for (Descuento descuento : descuentos) {
                if (descuento.tienePromo(producto.obtenerMarca()) && descuento.estaVigente()) {
                    precioConDescuento = descuento.aplicarDescuento(precioConDescuento);
                }
                if (descuento.tienePromo(tarjeta.tipoDeTarjeta()) && descuento.estaVigente()) {
                    descuentoDeCompra += descuento.descuento();
                }
            }
            montoTotal += precioConDescuento;
        }

        if (descuentoDeCompra!=0)
            montoTotal = montoTotal*descuentoDeCompra;
        return montoTotal;

    }

    @Override
    public List<Venta> ventas() {
        List<Venta> ventas = new ArrayList<>();
        inTransactionExecute((em) -> {
            ventas.addAll(em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList());
        });
        return ventas;
    }

    @Override
    public Long realizarVentaId(long idCliente, List<Long> productos, long idTarjeta) {
        AtomicReference<Long> ventaId = new AtomicReference<>(null);

        inTransactionExecute((em) -> {
            List<Descuento> promociones = this.descuentoService.recuperarDescuentos();
            List<Producto> listaProductos = em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
            System.out.println("productos: " +listaProductos.size());
            try {
                Venta venta = new Carrito(em.getReference(Cliente.class, idCliente),
                        listaProductos,
                        promociones,
                        servicioValidadorTarjetas,
                        em.getReference(Tarjeta.class, idTarjeta)).realizarPago();
                em.persist(venta);
                ventaId.set(venta.id()); // Asegúrate de que Venta tenga un método getId()
            } catch (TarjetaInvalidaExcepcion | ProductoInvalidoExcepcion e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("id de venta: "+ventaId.get());
        return ventaId.get();
    }


}