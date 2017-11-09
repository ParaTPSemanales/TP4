package app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


import grafos.GrafoNDNP;
import resources.GeneradorDeGrafos;
import resources.GrafoException;

public class App {

	public static void main(String[] args) throws GrafoException, IOException {
    GeneradorDeGrafos gr = new GeneradorDeGrafos();
    int[] colores = new int[10000];
    int min = Integer.MAX_VALUE, posicion =0 ;
    int porcentaje = 50;
    
    PrintWriter salida = new PrintWriter(new FileWriter("Estadisticas/Resumenes/ResumenRegularConAdyacencia" +porcentaje+"_Secuencial.in"));
    
    salida.println("RESUMEN DE COLORES");
    long tiempo = System.currentTimeMillis()*1000;
    for (int i = 0; i < 10000; i++) {
        GrafoNDNP afo = gr.generarGrafoRegularConPorcentajeDeAdyacencia(1000, porcentaje);
    	afo.colorear(GrafoNDNP.getSecuencial());
    	//salida.println("Datos del Grafo "+ i +" Cantidad Colores: "+ afo.getCantidadColores());
    	colores[i] = afo.getCantidadColores();
    	if (colores[i] < min){
    		min = colores[i];
    		posicion = i + 1;
    	}
    	salida.println("EJECUCION: " +i+ "COLORES: "+afo.getCantidadColores());
	}
    
   
    System.out.println("minima cantidad de colores: " + min);
    System.out.println("Aparecio por primera vez en la ejecucion: " +posicion);
    long tiempoFinal = System.currentTimeMillis()*1000 - tiempo;
    System.out.println("Tiempo usado: "+tiempoFinal);
	salida.close();

}
	
}