package ar.unrn.tp.web;


import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Venta;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("ventas")
public class VentaController {

    VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping("/crear")
    @Operation(summary = "Agregar una venta")
    public ResponseEntity<?> create(@RequestBody Venta venta) {
        List<Long> idList = new ArrayList<>();
        for(Producto producto: venta.getProductos()){
            idList.add(producto.id());
        }
        this.ventaService.realizarVenta(venta.getCliente().getId(),idList,venta.getTarjeta().getId() );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/calcular-monto")
    @Operation(summary = "Calcular el monto de una venta")
    public ResponseEntity<?> calculateAmount(@RequestBody Venta venta) {
        List<Long> productosIds = venta.getProductos().stream().map(Producto::id).collect(Collectors.toList());
        return ResponseEntity.status(OK).body(this.ventaService.calcularMonto(productosIds,venta.getTarjeta().getId()));
    }


}