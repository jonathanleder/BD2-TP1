package ar.unrn.tp.main;


import ar.unrn.tp.api.*;
import ar.unrn.tp.excepciones.FechaInvalidaExcepcion;
import ar.unrn.tp.jpa.servicios.*;
import ar.unrn.tp.modelo.ServicioPago;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication(scanBasePackages = {"ar.unrn.tp"})
public class Main {
    public static void main(String[] args) {


        SpringApplication.run(Main.class, args);




    }



}