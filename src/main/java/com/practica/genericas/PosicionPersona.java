package com.practica.genericas;


public class PosicionPersona {
	private Coordenada coordenada;
	private String documento;
	private FechaHora fechaPosicion;
	public Coordenada getCoordenada() {
		return coordenada;
	}
	public void setCoordenada(Coordenada coordenada) {
		this.coordenada = coordenada;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public FechaHora getFechaPosicion() {
		return fechaPosicion;
	}
	public void setFechaPosicion(FechaHora fechaPosicion) {
		this.fechaPosicion = fechaPosicion;
	}

	public static PosicionPersona crearPosicionPersona(String[] data) {
		PosicionPersona posicionPersona = new PosicionPersona();
		String fecha = null, hora;
		float latitud = 0, longitud;
		for (int i = 1; i < Constantes.MAX_DATOS_LOCALIZACION; i++) {
			String s = data[i];
			switch (i) {
				case 1:
					posicionPersona.setDocumento(s);
					break;
				case 2:
					fecha = data[i];
					break;
				case 3:
					hora = data[i];
					posicionPersona.setFechaPosicion(FechaHora.parsearFechaHora(fecha, hora));
					break;
				case 4:
					latitud = Float.parseFloat(s);
					break;
				case 5:
					longitud = Float.parseFloat(s);
					posicionPersona.setCoordenada(new Coordenada(latitud, longitud));
					break;
			}
		}
		return posicionPersona;
	}

	@Override
	public String toString() {
		String cadena = "";
        cadena += String.format("%s;", getDocumento());
        FechaHora fecha = getFechaPosicion();        
        cadena+=String.format("%s;",
	        		fecha.toString());
        cadena+=String.format("%.4f;%.4f\n", getCoordenada().getLatitud(), 
	        		getCoordenada().getLongitud());
	
		return cadena;
	}
}
