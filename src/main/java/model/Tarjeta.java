package model;

import java.util.Objects;

public interface Tarjeta {

    public boolean esValida();
    public String tipoDeTarjeta();

    public boolean tieneFondos();
    public int numeroDeTarjeta();

    public void descontar(double monto);
}
