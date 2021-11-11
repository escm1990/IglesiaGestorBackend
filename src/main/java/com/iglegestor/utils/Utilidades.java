package com.iglegestor.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.iglegestor.enums.Accion;
import com.iglegestor.enums.EstadoCivil;
import com.iglegestor.enums.EstadoRegistro;
import com.iglegestor.enums.Sexo;

public class Utilidades {

	// Obtener listado de acciones
	public static List<Accion> listarAcciones() {
		List<Accion> lista = Arrays.asList(Accion.values());
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}
		return lista;
	}
	
	// Obtener listado de estado civil
		public static List<Sexo> listarSexo() {
			List<Sexo> lista = Arrays.asList(Sexo.values());
			for (int i = 0; i < lista.size(); i++) {
				System.out.println(lista.get(i));
			}
			return lista;
		}

	// Obtener listado de estado civil
	public static List<EstadoCivil> listarEstadoCivil() {
		List<EstadoCivil> lista = Arrays.asList(EstadoCivil.values());
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}
		return lista;
	}

	// Obtener listado de estados
	public static List<EstadoRegistro> listarEstadoRegistro() {
		List<EstadoRegistro> lista = Arrays.asList(EstadoRegistro.values());
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}
		return lista;
	}

	// Formatear fecha Cadena de texto en milisegundos
	public static Long fechaStringMilisegundos(String fecha, String formatoHora) throws ParseException {
		String myDate = formatearFechaParametro(fecha) + formatoHora;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = sdf.parse(myDate);
		Long millis = date.getTime();
		return millis;
	}
	
	// Formatear fecha en milisegundos
		public static Long fechaMilisegundos(Date fecha) throws ParseException {
			return fecha.getTime();
		}

	// Formatear fecha en dd/MM/yyyy (Espera un valor en formato ddMMyyyy)
	public static String formatearFechaParametro(String fecha) {
		return fecha.substring(0, 2) + "/" + fecha.substring(2, 4) + "/" + fecha.substring(4, 8);
	}
}
