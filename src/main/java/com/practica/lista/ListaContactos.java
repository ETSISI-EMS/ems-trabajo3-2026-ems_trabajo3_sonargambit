package com.practica.lista;

import com.practica.genericas.Coordenada;
import com.practica.genericas.FechaHora;
import com.practica.genericas.PosicionPersona;

import java.util.LinkedList;
import java.util.List;

public class ListaContactos {
	private List<NodoTemporal> lista;

	public ListaContactos() {
		this.lista = new LinkedList<>();
	}

	/**
	 * Insertamos en la lista de nodos temporales, y a la vez inserto en la lista de nodos de coordenadas. 
	 * En la lista de coordenadas metemos el documento de la persona que está en esa coordenada 
	 * en un instante 
	 */
	public void insertarNodoTemporal (PosicionPersona p) {
		/**
		 * Busco la posición adecuada donde meter el nodo de la lista, excepto
		 * que esté en la lista. Entonces solo añadimos una coordenada.
		 */
		int index = 0;
		boolean encontrado = false;
		while(index < lista.size() && !encontrado) {
			NodoTemporal nodoActual = lista.get(index);
			if (nodoActual.getFecha().equals(p.getFechaPosicion())){
				insertarPosicion(p.getCoordenada(),nodoActual.getListaCoordenadas());
				encontrado = true;
			}
			index++;
		}
		if (!encontrado){
			insertarNuevoNodoTemporal(p);
		}
		/**
		 * No hemos encontrado ninguna posición temporal, así que
		 * metemos un nodo nuevo en la lista
		 */

	}

	private void insertarPosicion(Coordenada coordenada,List<NodoPosicion> lista) {
		boolean encontrado = false;
		int index = 0;
		while(index < lista.size() && !encontrado) {
			if (lista.get(index).getCoordenada().equals(coordenada)) {
				lista.get(index).setNumPersonas(lista.get(index).getNumPersonas()+1);
				encontrado = true;
			}else index++;
		}
		if (!encontrado) {
			lista.add(new NodoPosicion(coordenada,1));
		}
	}

	private  void insertarNuevoNodoTemporal(PosicionPersona p){
		NodoTemporal nuevoNodo = new NodoTemporal(p.getFechaPosicion());
		nuevoNodo.getListaCoordenadas().add(new NodoPosicion(p.getCoordenada(), 1));

		int index = 0;
		boolean insertado = false;

		while (index < lista.size() && !insertado) {
			NodoTemporal actual = lista.get(index);

			// Si la fecha del nuevo nodo es anterior, lo insertamos aquí
			if (nuevoNodo.compareTo(actual)<0) { /*Cambiar cuando se haya arreglado la clase FechaHora*/
				lista.add(index, nuevoNodo);
				insertado = true;
			} else {
				index++;
			}
		}

		// Si no se insertó en medio, va al final
		if (!insertado) {
			lista.add(nuevoNodo);
		}
	}
	
	public int tamanioLista () {
		return lista.size();
	}

	public String getPrimerNodo() {
		NodoTemporal aux = lista.get(0);
		String cadena = aux.getFecha().getFecha().toString();
		cadena+= ";" +  aux.getFecha().getHora().toString();
		return cadena;
	}

	/**
	 * Métodos para comprobar que insertamos de manera correcta en las listas de 
	 * coordenadas, no tienen una utilidad en sí misma, más allá de comprobar que
	 * nuestra lista funciona de manera correcta.
	 */
	public int numPersonasEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		if(lista.isEmpty())
			return 0;
		int cont = 0;
		int index = 0;
		NodoTemporal aux;
		while(index<lista.size()) {
			aux = lista.get(index);
			if(aux.getFecha().compareTo(inicio)>=0 && aux.getFecha().compareTo(fin)<=0) {
				cont+=aux.totalPersonas();
			}
			index++;
		}
		return cont;
	}

	
	
	
	public int numNodosCoordenadaEntreDosInstantes(FechaHora inicio, FechaHora fin) {
		if(this.lista.isEmpty())
			return 0;
		int cont = 0;
		int index = 0;
		while(index<lista.size()) {
			NodoTemporal aux = lista.get(index);
			if(aux.getFecha().compareTo(inicio)>=0 && aux.getFecha().compareTo(fin)<=0) {
				cont+=aux.sizeNodo();
			}
			index++;
		}
		return cont;
	}
	
	
	
	@Override
	public String toString() {
		String cadena="";
		NodoTemporal aux;
		for(int index=0;index<lista.size();index++) {
			aux = lista.get(index);
			cadena += aux.getFecha().toString();
			cadena += ";" +  aux.getFecha().getHora().toString() + " ";
		}
		return cadena;
	}
}
