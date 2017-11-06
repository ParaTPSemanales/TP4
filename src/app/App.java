package app;

import grafos.Grafo;
import resources.GeneradorDeGrafos;
import resources.GrafoException;

public class App {

	public static void main(String[] args) throws GrafoException {
    GeneradorDeGrafos gr = new GeneradorDeGrafos();
    
    Grafo afo = gr.generarGrafoRegularConPorcentajeDeAdyacencia(5, 50);
	System.out.println("Adyacencia:"+afo.getPorcentajeAdyacencia());
	System.out.println("--Grafo--");
	afo.mostrarGrafo();
	afo.colorear(Grafo.getMatula());
	
	System.out.println("--Nodos--");
	for (int i = 0; i < afo.getCantidadNodos(); i++) {
		System.out.println("Nodo : " + afo.getNodos().get(i).getNumero() +"\t" + "Color: " +afo.getNodos().get(i).getColor());
	}
	
	System.out.println("--Grados--");
	for (int i = 0; i < afo.getCantidadNodos(); i++) {
		System.out.println("Nodo : " + afo.getNodos().get(i).getNumero() +"\t" +"Grado: " +afo.getNodos().get(i).getGrado());
	}
}
}