package ar.unrn.tp.modelo;

import ar.unrn.tp.excepciones.ClienteInvalidoExcepcion;
import ar.unrn.tp.excepciones.EmailInvalidoExcepcion;
import lombok.NoArgsConstructor;

import javax.jdo.annotations.Unique;
import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre,apellido,email;
    @Unique
    private String DNI;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Tarjeta> tarjetas= new HashSet<>();


    public static String INVALID_DNI="Debe ingresar un dni valido";

    public Cliente (String nombre, String apellido, String dni, String email) throws ClienteInvalidoExcepcion, EmailInvalidoExcepcion {
        this.nombre= Objects.requireNonNull(nombre);
        this.apellido= Objects.requireNonNull(apellido);
        if (noEsValido(dni)){
            throw new RuntimeException(INVALID_DNI);
        }
        this.DNI= dni;
        this.email= Objects.requireNonNull(email);

    }



    public void agregarTarjeta(Tarjeta nuevaTarjeta){
        this.tarjetas.add(nuevaTarjeta);
    }

    public boolean buscarTarjeta(Tarjeta tarjetaBuscada){
        for (Tarjeta unaTarjeta: tarjetas){
            if (unaTarjeta.numeroDeTarjeta().equals(tarjetaBuscada.numeroDeTarjeta()))
                return true;
        }
        throw new RuntimeException("El numero de tarjeta ingresada no coincide con sus tarjetas registradas");
    }
    private boolean noEsValido(String dni){
        int dniValidador= Integer.parseInt(dni);
        return (dniValidador<0);
    }

    public void actualizarNombre(String nombre) {
        this.nombre = nombre;
    }

    public void actualizarApellido(String apellido) {
        this.apellido = apellido;
    }

    public void actualizarEmail(String email) {
        this.email = email;
    }

    public void actualizarDni(String DNI) {
        this.DNI = DNI;
    }

    public void actualizarTarjetas(Set<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }
    public Set<Tarjeta> tarjetas(){
        return this.tarjetas;
    }

    public boolean sos(Cliente cliente){
        return this.equals(cliente);
    }
    public String nombre(){
        return this.nombre;
    }
    public Long id(){
        return this.id;
    }
}
