package ar.unrn.tp.web;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.dto.DescuentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("descuentos")
public class DescuentoController {

    @Autowired
    private DescuentoService descuentoService;

    public DescuentoController(DescuentoService descuentoService) {
        this.descuentoService = descuentoService;
    }

    @PostMapping("/crear/compra/{producto}")
    public ResponseEntity<?> createDescuentoProducto(@PathVariable String producto,@RequestBody DescuentoDTO descuento) {

        System.out.println("entro a descuento de producto:\n producto: "+producto+"\n descuento: "+descuento.getMarca()+" "+descuento.getPorcentaje());
        this.descuentoService.crearDescuento(producto,descuento.getFechaInicio(),descuento.getFechaFin(),descuento.getPorcentaje());
        return ResponseEntity.status(OK).body("El descuento se creo con éxito!");
    }

    @PostMapping("/crear/producto/{tarjeta}")
    public ResponseEntity<?> createDescuentoCompra(@PathVariable String tarjeta,@RequestBody DescuentoDTO descuento) {

        System.out.println("entro a descuento de producto:\n tarjeta: "+tarjeta+"\n descuento: "+descuento.getMarca()+" "+descuento.getPorcentaje());
        this.descuentoService.crearDescuentoSobreTotal(tarjeta,descuento.getFechaInicio(),descuento.getFechaFin(),descuento.getPorcentaje());
        return ResponseEntity.status(OK).body("El descuento se creo con éxito!");
    }

    @GetMapping("/listar")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(OK).body(this.descuentoService.recuperarDescuentos());
    }


}