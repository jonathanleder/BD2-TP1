package ar.unrn.tp.web;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.dto.VentaDTO;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("ventas")
public class VentaController {

    @Autowired
    private final VentaService ventaService;
    //private final DescuentoService descuentoService;
   // private final ProductoService productoService;

    public VentaController(VentaService ventaService, ProductoService productoService, DescuentoService descuentoService) {
        this.ventaService = ventaService;
      // this.descuentoService = descuentoService;
     //   this.productoService = productoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody VentaDTO ventaDTO) {
        try {
            Long clienteId = ventaDTO.getCliente();
            List<Long> productosIds = ventaDTO.getProductos();
            Long tarjetaId = ventaDTO.getTarjeta();

            this.ventaService.realizarVenta(clienteId, productosIds, tarjetaId);
            return ResponseEntity.ok("Venta realizada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al realizar la venta: " + e.getMessage());
        }
    }

    @PostMapping("/calcular-monto")
    public ResponseEntity<?> calculateAmount(@RequestBody VentaDTO ventaDTO) {
     //   productoService.eliminarTodosLosProductos();
      //  descuentoService.eliminarTodosLosDescuentos();
        System.out.println("entro al controlador: " +ventaDTO.toString());

        try {
            List<Long> productosIds = ventaDTO.getProductos();
            Long tarjetaId = ventaDTO.getTarjeta();

            return ResponseEntity.status(HttpStatus.OK).body(this.ventaService.calcularMonto(productosIds, tarjetaId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al calcular el monto: " + e.getMessage());
        }
    }
}