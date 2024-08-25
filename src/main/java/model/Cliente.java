package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cliente {
    private String nombre;
    private String apellido;
    private int DNI;
    private String email;
    private List<Tarjeta> tarjetas= new ArrayList<>();
    public static String INVALID_DNI="Debe ingresar un dni valido";

    public Cliente(String nombre, String apellido, int dni, String email, Tarjeta unaTarjeta){
        this.nombre= Objects.requireNonNull(nombre);
        this.apellido= Objects.requireNonNull(apellido);
        if (dni < 0){
            throw new RuntimeException(INVALID_DNI);
        }
        this.DNI= dni;
        this.email= Objects.requireNonNull(email);
        this.tarjetas.add(Objects.requireNonNull(unaTarjeta));
    }

    public void realizarPago(double monto, Tarjeta tarjetaUtilizada){
        tarjetaUtilizada.descontar(monto);
        this.tarjetas.set(buscarTarjeta(tarjetaUtilizada),tarjetaUtilizada);
    }

    public int buscarTarjeta(Tarjeta tarjetaBuscada){
        for (Tarjeta unaTarjeta: tarjetas){
            if (unaTarjeta.numeroDeTarjeta()==tarjetaBuscada.numeroDeTarjeta())
                return tarjetas.indexOf(unaTarjeta);
        }
        throw new RuntimeException("El numero de tarjeta ingresada no coincide con sus tarjetas registradas");
    }

}
