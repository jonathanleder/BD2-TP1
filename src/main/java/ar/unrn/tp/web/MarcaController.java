package ar.unrn.tp.web;


import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.modelo.Marca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody Marca marca) {
        this.marcaService.crearMarca(marca.getNombre());
        return ResponseEntity.status(OK).body("La marca se añadió con éxito!");
    }

    @GetMapping("/listar")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(OK).body(this.marcaService.listarMarcas());
    }

}
