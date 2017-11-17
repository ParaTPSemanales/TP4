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
		String powell = "Powell", matula ="Matula", secuencial = "Secuencial";
		int cantidadNodos = 1000;
		int ejecuciones = 10000;
		int porcentaje = 75;
		String pathCasoPowell = "Estadisticas/Resumenes/ResumenRegularConAdyacencia" + porcentaje + "_" + powell + ".in";
		String PathTotalesPowell = "Estadisticas/Totales/ResumenRegularConAdyacencia" + porcentaje + "_" + powell + ".in";
		
		String pathCasoMatula = "Estadisticas/Resumenes/ResumenRegularConAdyacencia" + porcentaje + "_" + matula + ".in";
		String PathTotalesMatula = "Estadisticas/Totales/ResumenRegularConAdyacencia" + porcentaje + "_" + matula + ".in";
		
		String pathCasoSec = "Estadisticas/Resumenes/ResumenRegularConAdyacencia" + porcentaje + "_" + secuencial + ".in";
		String PathTotalesSec = "Estadisticas/Totales/ResumenRegularConAdyacencia" + porcentaje + "_" + secuencial + ".in";
		
		GrafoNDNP afo = GeneradorDeGrafos.generarGrafoRegularConPorcentajeDeAdyacencia(cantidadNodos, porcentaje);

		GrafoNDNP.ejecutarCaso(pathCasoPowell, PathTotalesPowell, GrafoNDNP.getPowell(), 
				porcentaje, powell, ejecuciones,afo);
		
		GrafoNDNP.ejecutarCaso(pathCasoMatula, PathTotalesMatula, GrafoNDNP.getMatula(), 
				porcentaje, matula, ejecuciones,afo);	
		
		GrafoNDNP.ejecutarCaso(pathCasoSec, PathTotalesSec, GrafoNDNP.getSecuencial(), 
				porcentaje, secuencial, ejecuciones,afo);	
	}
}
