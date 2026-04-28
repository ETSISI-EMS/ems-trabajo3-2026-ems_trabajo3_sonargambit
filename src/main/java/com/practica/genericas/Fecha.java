package com.practica.genericas;

public class Fecha {
    private int dia, mes, anio;

    public Fecha(int dia, int mes, int anio) {
        super();
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public String toString() {
        String cadena = String.format("%2d/%02d/%4d", dia, mes, anio);
        return cadena;
    }

    public static Fecha parsearFecha(String fecha) {
        int dia, mes, anio;
        String[] valores = fecha.split("\\/");
        dia = Integer.parseInt(valores[0]);
        mes = Integer.parseInt(valores[1]);
        anio = Integer.parseInt(valores[2]);
        return new Fecha(dia, mes, anio);
    }
}
