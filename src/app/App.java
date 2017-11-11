package app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


import grafos.GrafoNDNP;
import resources.GeneradorDeGrafos;
import resources.GrafoException;

public class App {

	public static void main(String[] args) throws GrafoException, IOException {

    int[] colores = new int[10000];
    int min = Integer.MAX_VALUE, posicion =0 ;
    int porcentaje = 50;
    
    PrintWriter salida = new PrintWriter(new FileWriter("Estadisticas/Resumenes/ResumenRegularConAdyacencia" +porcentaje+"_PPOWELL.txt"));
    GrafoNDNP afo = null;
    salida.println("RESUMEN DE COLORES");

    for (int i = 0; i < 10; i++) {
         afo = GeneradorDeGrafos.generarGrafoRegularConPorcentajeDeAdyacencia(6, porcentaje);
         if(afo!=null) {
         	afo.colorear(GrafoNDNP.getPowell());
        	colores[i] = afo.getCantidadColores();
        	if (colores[i] < min){
        		min = colores[i];
        		posicion = i + 1;
        	}
        	salida.println("EJECUCION: " +i+ "COLORES: "+afo.getCantidadColores()); 
         }
    }
    

    
   
    System.out.println("minima cantidad de colores: " + min);
    System.out.println("Aparecio por primera vez en la ejecucion: " +posicion);
    if(afo!=null)
    System.out.println("Grado Minimo: " + afo.getGradoMinimo()+ "Grado Maximo: " + afo.getGradoMaximo());
 

    salida.close();

}
}
	
