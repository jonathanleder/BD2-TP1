package ar.unrn.tp.web;


import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody Producto producto) {
        this.productoService.crearProducto(producto.getCodigo(),producto.getDescripcion(),producto.getPrecio(),producto.getCategoria().getId(),producto.getMarca().getId());
        Map<String, String> response = new HashMap<>();
        response.put("message", "El producto se añadió con éxito!");
        return ResponseEntity.status(OK).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(OK).body(this.productoService.listarProductos());
    }

}