package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class TarjetaDeCredito implements Tarjeta{

    private Integer numero;
    private String tipoTarjeta;
    private String titular;
    private double fondos;
    private LocalDateTime fechaVencimiento;

    public TarjetaDeCredito(Integer numero, String titular,String tipoTarjeta, LocalDateTime fechaVencimiento) {
        this.numero= Objects.requireNonNull(numero);
        this.titular=Objects.requireNonNull(titular);
        this.tipoTarjeta=Objects.requireNonNull(tipoTarjeta);
        this.fechaVencimiento= Objects.requireNonNull(fechaVencimiento);
        this.fondos=100000;
    }

    @Override
    public void descontar(double monto){
        this.fondos-=monto;
    }

    public double saldo(){
        return this.fondos;
    }

    @Override
    public  boolean tieneFondos(){
        return true;
    }

    @Override
    public int numeroDeTarjeta() {
        return this.numero;
    }

    @Override
    public boolean esValida() {
        return this.fechaVencimiento.isAfter(LocalDateTime.now());
    }

    @Override
    public String tipoDeTarjeta() {
        return this.tipoTarjeta;
    }


}
