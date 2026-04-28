package com.practica.genericas;

public class Hora {
    private int hora, minuto;

    public Hora(int hora, int minuto) {
        super();
        this.hora = hora;
        this.minuto = minuto;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hora, minuto);
    }

	public static Hora parsearHora(String horaMinuto){
		int hora, minuto;
		String[] valores = horaMinuto.split("\\:");
		hora = Integer.parseInt(valores[0]);
		minuto = Integer.parseInt(valores[1]);
		return new Hora(hora, minuto);
	}
}
