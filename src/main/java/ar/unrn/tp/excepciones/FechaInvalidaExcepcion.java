package ar.unrn.tp.excepciones;

public class FechaInvalidaExcepcion extends Exception{

    public FechaInvalidaExcepcion(){
        super("La fechas deben ser validas");
    }

}