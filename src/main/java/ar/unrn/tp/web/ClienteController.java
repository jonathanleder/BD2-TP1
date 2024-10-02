package ar.unrn.tp.web;

import ar.unrn.tp.api.ClienteService;

import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    public ClienteController(){}

    @PostMapping("/crear")
    public ResponseEntity<?> create(@RequestBody Cliente cliente) {
        System.out.println("Recibida solicitud para crear cliente: " + cliente.toString());
        this.clienteService.crearCliente(cliente.getNombre(),cliente.getApellido(),cliente.getDni(),cliente.getEmail());
        Map<String, Object> response = new HashMap<>();
        response.put("message", "El cliente se añadió con éxito!");
        return ResponseEntity.status(OK).body(response);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<?> update(@RequestBody Cliente cliente) {
        this.clienteService.modificarCliente(cliente.getId(),cliente.getNombre());
        return ResponseEntity.status(OK).body("El cliente se actualizó con éxito!");
    }

    @PutMapping("/agregar-tarjeta/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Tarjeta tarjeta) {
        this.clienteService.agregarTarjeta(id,tarjeta.getNumero(),tarjeta.tipoDeTarjeta());
        return ResponseEntity.status(OK).body("La tarjeta se añadió con éxito!");
    }

    @GetMapping("/listar-tarjetas/{id}")
    public ResponseEntity<?> findAllTarjetasDeCreditoDeCliente(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(this.clienteService.listarTarjetas(id));
    }

    @GetMapping("/listar")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(OK).body(this.clienteService.listarClientes());
    }

}