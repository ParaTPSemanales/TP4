package app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import grafos.GrafoNDNP;
import resources.GeneradorDeGrafos;
import resources.GrafoException;

public class App {

	public static void main(String[] args) throws GrafoException, IOException {
		String nombre = "Powell";
		int[] colores = new int[10000];
		int min = Integer.MAX_VALUE, posicion = 0;
		int porcentaje = 50;
		int contadorFrec;
		PrintWriter salida = new PrintWriter(new FileWriter(
				"Estadisticas/Resumenes/ResumenRegularConAdyacencia" + porcentaje + "_" + nombre + ".in"));
		PrintWriter totales = new PrintWriter(
				new FileWriter("Estadisticas/Totales/ResumenRegularConAdyacencia" + porcentaje + "_" + nombre + ".in"));
		HashMap<Integer, Integer> frecuenciaColor = new HashMap<Integer, Integer>();
		GrafoNDNP afo = null;
		salida.println("RESUMEN DE COLORES");

		afo = GeneradorDeGrafos.generarGrafoRegularConPorcentajeDeAdyacencia(10, porcentaje);
		if (afo != null) {
			for (int i = 0; i < 100; i++) {
				afo.mezclarPorGrado();

				afo.colorear(GrafoNDNP.getPowell());
				colores[i] = afo.getCantidadColores();
				if (colores[i] < min) {
					min = colores[i];
					posicion = i + 1;
				}
				salida.println("EJECUCION:  " + i + "COLORES:  " + afo.getCantidadColores());

				if (frecuenciaColor.containsKey(afo.getCantidadColores())) {
					contadorFrec = frecuenciaColor.get(afo.getCantidadColores());
					frecuenciaColor.replace(afo.getCantidadColores(), contadorFrec + 1);
				} else {
					frecuenciaColor.put(afo.getCantidadColores(), 1);
				}

				afo.despintarGrafo();
			}

			totales.println("TOTALES DE " + nombre + "CON ADY" + porcentaje);
			frecuenciaColor.forEach((k, v) -> totales.println("Color: " + k + " Cantidad: " + v ));
			totales.println( "gradoMax: " +afo.getGradoMaximo());
		}

		System.out.println("minima cantidad de colores: " + min);
		System.out.println("Aparecio por primera vez en la ejecucion: " + posicion);
		if (afo != null)
			System.out.println("Grado Minimo: " + afo.getGradoMinimo() + "Grado Maximo: " + afo.getGradoMaximo());

		salida.close();
		totales.close();

	}
}
