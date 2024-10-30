package ar.unrn.tp.web;


import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @PutMapping("/actualizar")
    public ResponseEntity<?> update(@RequestBody Producto producto) {
        System.out.println("Entro a actualizar producto, datos: "+producto.toString());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.productoService.modificarProducto(producto.getId(),producto.getCodigo(),producto.getDescripcion(),producto.getPrecio(),producto.getCategoria().getId(),producto.getMarca().getId(),producto.getVersion());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/listar")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(OK).body(this.productoService.listarProductos());
    }
    @PutMapping("/modificar")
    public ResponseEntity<?> modificar(@RequestBody Producto producto) {
        return ResponseEntity.status(OK).body(this.productoService.listarProductos());
    }
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        System.out.println("Entro a buscar con el id: "+id);
        return ResponseEntity.status(OK).body(this.productoService.buscarProducto(id));
    }

}