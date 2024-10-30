package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.excepciones.ProductoInvalidoExcepcion;
import ar.unrn.tp.excepciones.TarjetaInvalidaExcepcion;
import ar.unrn.tp.modelo.*;
import jakarta.persistence.LockModeType;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class JPAVentaService extends JPAGenericService implements VentaService {
    private ServicioPago servicioValidadorTarjetas;

    private DescuentoService descuentoService;


    @Autowired
    public JPAVentaService(ServicioPago servicioValidadorTarjetas, DescuentoService descuentoService, EntityManagerFactory emf){
        super(emf);
        this.descuentoService = descuentoService;
        this.servicioValidadorTarjetas = servicioValidadorTarjetas;
    }
    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {

        inTransactionExecute((em) -> {

            //Busca el cliente y la tarjeta en la bd
            Cliente cliente = em.find(Cliente.class,idCliente);
            Tarjeta tarjeta = em.find(Tarjeta.class,idTarjeta);
            //se evalua la existencia del cliente, la tarjeta y si hay productos para realizar la venta
            if (cliente == null) {
                throw new RuntimeException("El cliente no existe");
            }
            if (tarjeta == null){
                throw new RuntimeException("No existe la tarjeta solicitada");
            }
            if (productos==null || productos.isEmpty()) {
                throw new RuntimeException("No hay productos para esta lista");
            }

            //Debemos evaluar si la tarjeta ingresada pertenece al cliente
            boolean existeTarjeta=false;

            for (Tarjeta tarjetaDeCredito: cliente.getTarjetas()) {
                if (tarjetaDeCredito.getNumero().equalsIgnoreCase(tarjetaDeCredito.getNumero())) {
                    existeTarjeta = true;
                }
            }
            List<Descuento> promociones = this.descuentoService.recuperarDescuentos();
            List<Producto> listaProductos = em.createQuery("SELECT o FROM Producto o WHERE o.id IN :ids", Producto.class).setParameter("ids", productos).getResultList();

            NumeroSiguiente nroSiguiente;
            try {
                nroSiguiente = em.createQuery("SELECT n FROM NumeroSiguiente n WHERE año = :anioActual", NumeroSiguiente.class).setParameter("anioActual", LocalDate.now().getYear()).setLockMode(LockModeType.PESSIMISTIC_WRITE).getSingleResult();
            } catch (NoResultException e) {
                nroSiguiente = new NumeroSiguiente(0, LocalDate.now().getYear());
            }

            try {
                Venta venta = new Carrito(em.getReference(Cliente.class,idCliente),listaProductos,promociones,servicioValidadorTarjetas,em.getReference(Tarjeta.class,idTarjeta)).realizarPago(String.valueOf(nroSiguiente.recuperarSiguiente())+"-"+String.valueOf(nroSiguiente.getAño()));
                System.out.println(venta.cantidadDeProductos());
                em.persist(venta);
                em.persist(nroSiguiente);

            } catch (TarjetaInvalidaExcepcion | ProductoInvalidoExcepcion e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public float calcularMonto(List<Long> productos, Long idTarjeta) {

        System.out.println("entro al JPAVentaService: " +productos.size() +" "+idTarjeta);
        AtomicReference<Float> monto = new AtomicReference<>(0F);
        inTransactionExecute((em) -> {
            Tarjeta tarjetaCredito = em.find(Tarjeta.class, idTarjeta);
            if (tarjetaCredito == null) {
                throw new IllegalArgumentException("Tarjeta no encontrada con ID: " + idTarjeta);
            }

            List<Producto> listaProductos = em.createQuery("SELECT o FROM Producto o WHERE o.id IN :ids", Producto.class)
                    .setParameter("ids", productos)
                    .getResultList();


            // Check for null or invalid products
            for (Producto producto : listaProductos) {
                System.out.println("\nProducto: "+producto.descripcion() + " \nMarca: "+producto.obtenerMarca()+
                        "\n precio: "+producto.getPrecio());
                if (producto == null || producto.getDescripcion() == null) {
                    throw new IllegalStateException("Producto inválido encontrado. ID: " +
                            (producto != null ? producto.id() : "null"));
                }
            }
            System.out.println("paso la validacion de producto");
            List<Descuento> promociones = this.descuentoService.recuperarDescuentos();
            Carrito carrito = new Carrito(listaProductos, promociones, this.servicioValidadorTarjetas);
            System.out.println("la tarjeta es: "+ tarjetaCredito.getTipoTarjeta() + "\n numero: "+tarjetaCredito.getNumero()+
                    "\n otro dato(id): "+ tarjetaCredito.getId());
            carrito.setTarjetaSeleccionada(tarjetaCredito);
            monto.set(carrito.calcularMontoConPromos());
            System.out.println("el valor total es de: "+monto.get());
        });
        return monto.get();
    }



    @Override
    public List<Venta> ventas() {
        List<Venta> ventas = new ArrayList<>();
        inTransactionExecute((em) -> {
            ventas.addAll(em.createQuery("SELECT v FROM Venta v", Venta.class).getResultList());
        });
        return ventas;
    }

    /*
    @Override
    public Long realizarVentaId(long idCliente, List<Long> productos, long idTarjeta) {
        AtomicReference<Long> ventaId = new AtomicReference<>(null);

        inTransactionExecute((em) -> {
            List<Descuento> promociones = this.descuentoService.recuperarDescuentos();
            List<Producto> listaProductos = em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();

            NumeroSiguiente nroSiguiente;

            try {
                nroSiguiente = em.createQuery("SELECT n FROM NumeroSiguiente n WHERE año = :anioActual", NumeroSiguiente.class).setParameter("anioActual", LocalDate.now().getYear()).setLockMode(LockModeType.PESSIMISTIC_WRITE).getSingleResult();
            } catch (NoResultException e) {
                nroSiguiente = new NumeroSiguiente(0, LocalDate.now().getYear());
            }


            try {
                Venta venta = new Carrito(em.getReference(Cliente.class, idCliente),
                        listaProductos,
                        promociones,
                        servicioValidadorTarjetas,
                        em.getReference(Tarjeta.class, idTarjeta)).realizarPago(String.valueOf(nroSiguiente.recuperarSiguiente())+"-"+String.valueOf(nroSiguiente.getAño()));
                em.persist(venta);
                ventaId.set(venta.id()); // Asegúrate de que Venta tenga un método getId()
            } catch (TarjetaInvalidaExcepcion | ProductoInvalidoExcepcion e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("id de venta: "+ventaId.get());
        return ventaId.get();
    }*/


}