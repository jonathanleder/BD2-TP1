package ar.unrn.tp.web;


import ar.unrn.tp.api.MarcaService;
import ar.unrn.tp.modelo.Marca;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("marcas")
public class MarcaController {

    private MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody Marca marca) {
        this.marcaService.crearMarca(marca.marca());
        return ResponseEntity.status(OK).body("La marca se añadió con éxito!");
    }

}
