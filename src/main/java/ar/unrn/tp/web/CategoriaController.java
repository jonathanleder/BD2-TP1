package ar.unrn.tp.web;

import ar.unrn.tp.api.CategoriaService;

import ar.unrn.tp.modelo.Categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody Categoria categoria) {
        this.categoriaService.crearCategoria(categoria.getNombre());
        return ResponseEntity.status(OK).body("La categoria se añadió con éxito!");
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listar() {
        return ResponseEntity.status(OK).body(this.categoriaService.listarCategorias());
    }

}