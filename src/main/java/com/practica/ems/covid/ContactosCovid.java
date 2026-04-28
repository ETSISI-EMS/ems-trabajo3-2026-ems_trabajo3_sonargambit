package com.practica.ems.covid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import com.practica.excecption.EmsDuplicateLocationException;
import com.practica.excecption.EmsDuplicatePersonException;
import com.practica.excecption.EmsInvalidNumberOfDataException;
import com.practica.excecption.EmsInvalidTypeException;
import com.practica.excecption.EmsLocalizationNotFoundException;
import com.practica.excecption.EmsPersonNotFoundException;
import com.practica.genericas.Constantes;
import com.practica.genericas.PosicionPersona;
import com.practica.lista.ListaContactos;

public class ContactosCovid {
	private Poblacion poblacion;
	private Localizacion localizacion;
	private ListaContactos listaContactos;

	public ContactosCovid() {
		this.poblacion = new Poblacion();
		this.localizacion = new Localizacion();
		this.listaContactos = new ListaContactos();
	}

	public Poblacion getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(Poblacion poblacion) {
		this.poblacion = poblacion;
	}

	public Localizacion getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(Localizacion localizacion) {
		this.localizacion = localizacion;
	}
	
	

	public ListaContactos getListaContactos() {
		return listaContactos;
	}

	public void setListaContactos(ListaContactos listaContactos) {
		this.listaContactos = listaContactos;
	}

	private void borrarInformacionAnterior(boolean reset){
		if (reset) {
			this.poblacion = new Poblacion();
			this.localizacion = new Localizacion();
			this.listaContactos = new ListaContactos();
		}
	}
	public void loadData(String data, boolean reset) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException,
			EmsDuplicatePersonException, EmsDuplicateLocationException {
		borrarInformacionAnterior(reset);

		String datas[] = dividirEntrada(data);
		for (String linea : datas) {
			String datos[] = this.dividirLineaData(linea);
			comprobarPersonaOLocalizacion(datos[0]);

			if (datos[0].equals("PERSONA")) {
				cargarDatosPersona(datos);
			}
			else if (datos[0].equals("LOCALIZACION")) {
				cargarDatosLocalizacion(datos);
			}
		}
	}

	private void comprobarPersonaOLocalizacion(String dato) throws EmsInvalidTypeException{
		if (!dato.equals("PERSONA") && !dato.equals("LOCALIZACION")) {
			throw new EmsInvalidTypeException();
		}
	}
	private void cargarDatosPersona(String[] datos) throws EmsInvalidNumberOfDataException, EmsDuplicatePersonException {
		if (datos.length != Constantes.MAX_DATOS_PERSONA) {
			throw new EmsInvalidNumberOfDataException("El número de datos para PERSONA es menor de 8");
		}
		this.poblacion.addPersona(datos);
	}
	private void cargarDatosLocalizacion(String[] datos) throws EmsInvalidNumberOfDataException, EmsDuplicateLocationException {
		if (datos.length != Constantes.MAX_DATOS_LOCALIZACION) {
			throw new EmsInvalidNumberOfDataException(
					"El número de datos para LOCALIZACION es menor de 6" );
		}
		PosicionPersona pp = PosicionPersona.crearPosicionPersona(datos);
		this.localizacion.addLocalizacion(pp);
		this.listaContactos.insertarNodoTemporal(pp);
	}
	private void cargarLinea(String linea) throws EmsInvalidTypeException, EmsInvalidNumberOfDataException, EmsDuplicatePersonException, EmsDuplicateLocationException {
		String datos[] = this.dividirLineaData(linea);

		comprobarPersonaOLocalizacion(datos[0]);

		if (datos[0].equals("PERSONA")) {
			cargarDatosPersona(datos);
		}
		else if (datos[0].equals("LOCALIZACION")) {
			cargarDatosLocalizacion(datos);
		}
	}

	@SuppressWarnings("resource")
	public void loadDataFile(String fichero, boolean reset ) {
		File archivo = null;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			archivo = new File(fichero);
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);

			borrarInformacionAnterior(reset);

			String data;
			while ((data = br.readLine()) != null) {
				String[] datas = dividirEntrada(data.trim());
				for (String linea : datas) {
					cargarLinea(linea);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	public int findPersona(String documento) throws EmsPersonNotFoundException {
		int pos;
		try {
			pos = this.poblacion.findPersona(documento);
			return pos;
		} catch (EmsPersonNotFoundException e) {
			throw new EmsPersonNotFoundException();
		}
	}

	public int findLocalizacion(String documento, String fecha, String hora) throws EmsLocalizationNotFoundException {
		int pos;
		try {
			pos = localizacion.findLocalizacion(documento, fecha, hora);
			return pos;
		} catch (EmsLocalizationNotFoundException e) {
			throw new EmsLocalizationNotFoundException();
		}
	}

	public List<PosicionPersona> localizacionPersona(String documento) throws EmsPersonNotFoundException {
		return localizacion.localizacionPersona(documento);
	}

	public void delPersona(String documento) throws EmsPersonNotFoundException {
		this.poblacion.delPersona(documento);
	}

	private String[] dividirEntrada(String input) {
		String cadenas[] = input.split("\\n");
		return cadenas;
	}

	private String[] dividirLineaData(String data) {
		String cadenas[] = data.split("\\;");
		return cadenas;
	}
}
