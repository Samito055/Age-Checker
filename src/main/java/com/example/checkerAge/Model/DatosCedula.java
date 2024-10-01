package com.example.checkerAge.Model;

import java.time.LocalDate;

public class DatosCedula {

    private String nombre;
    private String numeroCedula;
    private LocalDate fechaNacimiento;

    public DatosCedula(String nombre, String numeroCedula, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.numeroCedula = numeroCedula;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroCedula() {
        return numeroCedula;
    }

    public void setNumeroCedula(String numeroCedula) {
        this.numeroCedula = numeroCedula;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
