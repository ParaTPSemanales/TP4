package resources;

import java.io.IOException;

import grafos.GrafoColor;

public class ProbadorDeGrafos {
	
	public static void main(String[] args) throws IOException {
		GrafoColor gc;

		gc = new GrafoColor("70_porciento_adyacencia.in","grafoColoreado.out");
	
		if(gc.hayNodosAdyacentesIgualColor()||gc.hayNodosRepetidos()||gc.hayNodosSinPintar()){
			System.out.println("Grafo MAL coloreado");
		}
		else
			System.out.println("Grafo BIEN coloreado");
	}
	
	
}
