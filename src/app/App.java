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
		int cantidadNodos = 10;
		int ejecuciones = 10;
		int porcentaje = 50;
		String pathCaso = "Estadisticas/Resumenes/ResumenRegularConAdyacencia" + porcentaje + "_" + nombre + ".in";
		String PathTotales = "Estadisticas/Totales/ResumenRegularConAdyacencia" + porcentaje + "_" + nombre + ".in";
		GrafoNDNP.ejecutarCaso(pathCaso, PathTotales, GrafoNDNP.getPowell(), 
				porcentaje, nombre, ejecuciones,cantidadNodos);
	}
}
