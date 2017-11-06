package grafos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GrafoColor {
	/** Grafo original **/
	private Grafo grafoOriginal;
	/** Mapa que asocia numero de grafo con numero de color **/
	private Map<Integer, Integer> salidaColoreada;
	private boolean nodosRepetidos;
	private boolean nodosSinPintar;
	private boolean nodosAdyacentesIgualColor;
	private Scanner sc;

	/**
	 * Constructor
	 * 
	 * @param entrada
	 * @param salida
	 */
	public GrafoColor(final String entrada, final String salida) throws IOException {
		int nodo, color, cantColores, cantNodos;
		
		grafoOriginal = new Grafo(entrada);
		sc = new Scanner(new File(entrada));
		 cantColores = sc.nextInt();
		 cantNodos = sc.nextInt();
		salidaColoreada = new HashMap<Integer, Integer>();

		for (int i = 0; i < cantNodos; i++) {
			nodo = sc.nextInt();
			color = sc.nextInt();
			if (salidaColoreada.containsKey(nodo)) {
				nodosRepetidos = true;
			} else {
				salidaColoreada.put(nodo, color);
			}
		}

		/**
		 * CAMBIAR LA ESTRUCTURA
		 */
		for (Integer key : salidaColoreada.keySet()) {
			if (!(salidaColoreada.get(key).compareTo(0) > 0)) {
				nodosSinPintar = true;
				break;
			}
		}

		for (int i = 1; i <= cantNodos; i++) {
			for (int j = i; j <= cantNodos; j++) {
				if (grafoOriginal.getValor(i - 1, j - 1) == true && i != j) {
					if (salidaColoreada.get(i).equals(salidaColoreada.get(j))) {
						nodosAdyacentesIgualColor = true;
					}
				}
			}
		}
		sc.close();
	}

	public boolean hayNodosRepetidos() {
		return nodosRepetidos;
	}

	public boolean hayNodosSinPintar() {
		return nodosSinPintar;
	}

	public boolean hayNodosAdyacentesIgualColor() {
		return nodosAdyacentesIgualColor;
	}
}