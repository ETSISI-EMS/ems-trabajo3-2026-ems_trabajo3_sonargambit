package com.practica.lista;

import com.practica.genericas.FechaHora;

import java.util.LinkedList;
import java.util.List;


/**
 * Nodo para guardar un instante de tiempo. Además guardamos una lista con las coordeandas
 * y las personas (solo número) que en ese instante están en una coordeanda en concreto  
 *
 */
public class NodoTemporal {
	private List<NodoPosicion> listaCoordenadas;
	private FechaHora fecha;

	
	public NodoTemporal(FechaHora fecha) {
		this.fecha = fecha;
		listaCoordenadas=new LinkedList<>();
	}
	public List<NodoPosicion> getListaCoordenadas() {
		return listaCoordenadas;
	}
	public void setListaCoordenadas(List<NodoPosicion> listaCoordenadas) {
		this.listaCoordenadas = listaCoordenadas;
	}
	public FechaHora getFecha() {
		return fecha;
	}
	public void setFecha(FechaHora fecha) {
		this.fecha = fecha;
	}

	public int compareTo(NodoTemporal nodo){
		return this.fecha.compareTo(nodo.getFecha());
	}
	public int totalPersonas(){
		int total = 0;
		for(int i=0;i<this.listaCoordenadas.size();i++){
			total+=listaCoordenadas.get(i).getNumPersonas();
		}
		return total;
	}
	public int sizeNodo(){
		return this.listaCoordenadas.size();
	}
}
