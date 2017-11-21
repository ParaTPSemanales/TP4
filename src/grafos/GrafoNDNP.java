package grafos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import resources.GeneradorDeGrafos;
import resources.GrafoException;

public class GrafoNDNP {

	private static final int SECUENCIAL = 1;
	private static final int POWELL = 2;
	private static final int MATULA = 3;
	/**
	 * Matriz para la representacion de un grafo.
	 */
	private MatrizSimetrica matriz;
	/**
	 * Cantidad de nodos que tiene el grafo
	 */
	private int cantNodos;
	/**
	 * Cantidad de aristas
	 */
	private int cantAristas;
	/**
	 * Porcentaje de adyacencia
	 */
	private double porcentAdy;
	/**
	 * Grado maximo que puede tener un nodo
	 */
	private int grMax;
	/**
	 * Grado minimo del nodo
	 */
	private int grMin;
	/**
	 * Cantidad de Colores del Grafo
	 */
	private int cantColores;
	/**
	 * Lista que contiene los nodos pertenecientes al grafo
	 */
	private ArrayList<Nodo> nodos = new ArrayList<Nodo>();

	/**
	 * Scanner para lectura de archivo
	 */
	private Scanner sc;

	/**
	 * Constructor de grafo
	 * 
	 * @param cantNodos:La
	 *            cantidad de nodos que contiene .
	 * @param matrizSim:
	 *            La matriz Simetrica que representa al grafo.
	 * @param cAristas:
	 *            Cantidad de aristas que contiene.
	 * @param pAdy:
	 *            Porcentaje de adyacencia necesaria.
	 */
	public GrafoNDNP(int cantNodos, MatrizSimetrica matrizSim, int cAristas, double pAdy) {
		this.cantNodos = cantNodos;
		this.matriz = matrizSim;
		this.cantAristas = cAristas;
		this.porcentAdy = pAdy;
		this.nodos = new ArrayList<Nodo>();
		calcularGrados(); // Maximo y minimo
		completarGrados(); // Para todos los nodos
	}

	/**
	 * Contructor de grafo mediante el uso de un archivo
	 * 
	 * @param archivo
	 */
	public GrafoNDNP(String archivo) {
		int fila, columna;

		try {
			sc = new Scanner(new File(archivo));

			cantNodos = sc.nextInt();
			cantAristas = sc.nextInt();
			porcentAdy = Double.parseDouble(sc.next());
			grMax = sc.nextInt();
			grMin = sc.nextInt();
			this.matriz = new MatrizSimetrica(cantNodos);

			for (int i = 0; i < cantAristas; i++) {
				fila = sc.nextInt() - 1;
				columna = sc.nextInt() - 1;
				matriz.setValor(fila, columna, true);
			}

			for (int i = 0; i < matriz.getDimension(); i++) {
				int grado = 0;
				for (int j = 0; j < matriz.getDimension(); j++) {
					if (matriz.getValor(i, j) && i != j) {
						grado++;
					}
				}
				nodos.add(new Nodo(i + 1, 0, grado));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
	}

	/**
	 * Metodo que sirve para completar los grados de cada nodo. O(n2)
	 */
	public void completarGrados() {
		int grado = 0;
		for (int f = 0; f < cantNodos; f++) {
			grado = 0;
			for (int c = 0; c < cantNodos; c++) {
				if (getValor(f, c) == true)
					grado++;
			}
			nodos.add(new Nodo(f + 1, 0, grado));
		}
	}

	/**
	 * Metodo que sirve para calcular los grados maximos y minimos de un grafo.
	 */
	public void calcularGrados() {
		grMax = 1;
		int temp;
		for (int f = 0; f < cantNodos; f++) {
			temp = 0;
			for (int c = 0; c < cantNodos; c++) {
				if (getValor(f, c) == true)
					temp++;
			}
			if (temp > grMax)
				grMax = temp;
		}
		grMin = grMax;
		for (int f = 0; f < cantNodos; f++) {
			temp = 0;
			for (int c = 0; c < cantNodos; c++) {
				if (getValor(f, c) == true)
					temp++;
			}
			if (temp < grMin)
				grMin = temp;
		}
		
	}

	/**
	 * Metodo que sirve para obtener todos los nodos adyacentes a un nodo que se
	 * ingresa por parametro
	 * 
	 * @param nodo
	 * @return Lista con nodos adyacentes.
	 */
	public ArrayList<Integer> nodosAdyacentesA(final int nodo) {
		ArrayList<Integer> adyacentes = new ArrayList<Integer>();
		for (int i = 0; i < matriz.getDimension(); i++) {
			if (esAdyacente(nodo, i) && nodo != i) {
				adyacentes.add(i);
			}
		}
		return adyacentes;
	}

	/**
	 * Metodo que sirve para saber si el grafo es conexo o no
	 * 
	 * @return V O F
	 */
	public boolean esConexo() {
		boolean result = true;
		int indice = 0;
		BusquedaEnProfundidad busqueda = new BusquedaEnProfundidad(this, 0);

		while (result && indice < cantNodos) {
			if (!busqueda.estaVisitado(indice))
				result = false;
			indice++;
		}

		return result;
	}

	/**
	 * Setear valor en el grafo.
	 * 
	 * @param fila
	 * @param col
	 * @param valor
	 */
	public void setValor(final int fila, int col, boolean valor) {
		this.matriz.setValor(fila, col, valor);
	}

	/**
	 * Obtener el valor de un nodo Lo busca en la M.A
	 * 
	 * @param fila
	 * @param col
	 * @return V o F
	 */
	public boolean getValor(int fila, int col) {
		return matriz.getValor(fila, col);
	}

	/**
	 * Obtener cantidad de nodos.
	 * 
	 * @return entero
	 */
	public int getCantidadNodos() {
		return cantNodos;
	}

	/**
	 * Saber si dos nodos son adyacentes.
	 * 
	 * @param nodoA
	 * @param nodoB
	 * @return V o F
	 */
	public boolean esAdyacente(final int nodoA, final int nodoB) {
		return matriz.getValor(nodoA, nodoB);
	}

	/**
	 * Cantidad de colores que existen
	 * 
	 * @return entero
	 */
	public int getCantidadColores() {
		return cantColores;
	}

	/**
	 * Obtener tamaño de la matriz
	 * 
	 * @return entero
	 */
	public int getTam() {
		return matriz.getTam();
	}

	/**
	 * Obtener cantidad de aristas
	 * 
	 * @return entero
	 */
	public int getCantidadAristas() {
		return cantAristas;
	}

	/**
	 * Obtener grado maximo
	 * 
	 * @return entero
	 */
	public int getGradoMaximo() {
		return grMax;
	}

	/**
	 * Obtener el grado minimo
	 * 
	 * @return entero
	 */
	public int getGradoMinimo() {
		return grMin;
	}

	/**
	 * Obtener porcentaje de adyacencia
	 * 
	 * @return numero real.
	 */
	public double getPorcentajeAdyacencia() {
		return porcentAdy;
	}

	/**
	 * Mostrar grafo o matriz de adyacencia
	 */
	public void mostrarGrafo() {
		matriz.mostrarMatriz();
	}

	/**
	 * Guardar grafo en un archivo
	 * 
	 * @param archivo
	 */
	public void guardarGrafo(final String archivo) {
		PrintWriter salida = null;// !=BufferedReader
		try {

			salida = new PrintWriter(new FileWriter(archivo));
			salida.println(cantNodos + " " + cantAristas + " " + porcentAdy + " " + grMax + " " + grMin);
			for (int f = 0; f < cantNodos; f++) {
				for (int c = f + 1; c < cantNodos; c++) {
					if (getValor(f, c) == true) {
						salida.println((f + 1) + " " + (c + 1));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			salida.close();
		}
	}

	/**
	 * Metodo para el coloreo de grafos
	 */
	public void colorearSecuencialAlternativo() {
		int color;
		cantColores = 0;
		for (int i = 0; i < cantNodos; i++) {
			color = 1;
			/** Mientras el color no se pueda usar, elijo otro color **/
			while (!sePuedeColorear(i, color))
				color++;

			nodos.get(i).setColor(color);

			if (color > cantColores)
				cantColores = color;
		}
	}

	/**
	 * Se hace un random al listado de nodos para luego colorear.
	 */
	public void colorearSecuencial() {
		// Collections.shuffle(nodos);
		colorearSecuencialAlternativo();
	}

	/**
	 * coloreo segun Powell.
	 */
	public void colorearPowell() {
		ordenarGradoDescendente(nodos, 0, nodos.size() - 1);
		colorearSecuencialAlternativo();
	}

	/**
	 * Resetear colores
	 */
	public void despintarGrafo() {
		for (int i = 0; i < cantNodos; i++) {
			nodos.get(i).setColor(0);
		}
	}

	/**
	 * Coloreo segun matula.
	 */
	public void colorearMatula() {
		ordenarGradoAscendente(nodos, 0, nodos.size() - 1);
		// mezclarPorGrado();
		colorearSecuencialAlternativo();
	}

	/**
	 * Metodo que valida si es posible colorear el grafo o no
	 * 
	 * @param indice
	 * @param color
	 * @return V o F
	 */
	private boolean sePuedeColorear(int indice, int color) {
		int i = 0;
		boolean sePuede = true;
		if (nodos.get(indice).getColor() != 0) // si el nodo fue coloreado
			sePuede = false;
		while (i < cantNodos && sePuede) {
			// Si hay un nodo adyacente con ese color, no se puede colorear. Si el nodo fue
			// coloreado tampoco se puede.
			if (nodos.get(i).getColor() == color && i != indice) {
				if (esAdyacente(nodos.get(i).getNumero() - 1, nodos.get(indice).getNumero() - 1))
					sePuede = false;
			}
			i++;
		}
		return sePuede;
	}

	/**
	 * Mezclar el grafo segun el grado
	 */
	public void mezclarPorGrado() {
		int i = 0;
		int inicio = 0;
		int fin = 0;
		int grado = 0;
		Nodo aux;
		Random r = new Random();
		boolean[] mezclado = new boolean[cantNodos];

		while (i < cantNodos) {
			inicio = i;
			grado = nodos.get(i).getGrado();
			while (i < cantNodos && nodos.get(i).getGrado() == grado)
				i++;

			fin = i;

			for (int k = inicio; k < (fin - inicio); k++) {
				int res = r.nextInt(fin - inicio);
				if (mezclado[k])
					res = r.nextInt(fin - inicio);
				aux = nodos.get(k);
				mezclado[k] = true;
				nodos.set(k, nodos.get(inicio + res));
				nodos.set(inicio + res, aux);
			}
		}
		validarMezcla();
	}

	/**
	 * metodo que sirve para validar si la mezcla realizada fue correcta
	 * 
	 * @return V o F
	 * @throws GrafoException
	 */
	private boolean validarMezcla() {
		int grado = nodos.get(0).getGrado();
		boolean retorno = true;
		for (Nodo n : nodos) {
			if (n.getGrado() != grado) {
				if (n.getGrado() < grado) {
					System.err.println("Error en la mezcla del grafo");
					System.exit(4321);
				} else
					grado = n.getGrado();
			}
		}
		return retorno;
	}

	/**
	 * Uso de quicksort para ordenamiento de grafo segun los grados.
	 * 
	 * @param nodo
	 * @param izq
	 * @param der
	 */
	private void ordenarGradoAscendente(ArrayList<Nodo> nodo, int izq, int der) {
		Nodo pivote = nodos.get((izq + der) / 2), aux;
		int i = izq, d = der;
		do {
			while (nodos.get(i).getGrado() < pivote.getGrado())
				i++;
			while (nodos.get(d).getGrado() > pivote.getGrado())
				d--;
			if (i <= d) {
				aux = nodo.get(i);
				nodo.set(i, nodo.get(d));
				nodo.set(d, aux);
				i++;
				d--;
			}
		} while (i <= d);

		if (izq < d)
			ordenarGradoAscendente(nodos, izq, d);
		if (i < der)
			ordenarGradoAscendente(nodos, i, der);
	}

	/**
	 * Uso de quicksort para ordenamiento de grafo segun los grados.
	 * 
	 * @param nodo
	 * @param izq
	 * @param der
	 */
	private void ordenarGradoDescendente(ArrayList<Nodo> nodo, int izq, int der) {
		Nodo pivote = nodos.get((izq + der) / 2);
		Nodo aux;
		int i = izq, d = der;
		do {
			while (nodo.get(i).getGrado() > pivote.getGrado())
				i++;
			while (nodo.get(d).getGrado() < pivote.getGrado())
				d--;
			if (i <= d) {
				aux = nodo.get(i);
				nodo.set(i, nodo.get(d));
				nodo.set(d, aux);
				i++;
				d--;
			}
		} while (i <= d);
		if (izq < d)
			ordenarGradoDescendente(nodo, izq, d);
		if (i < der)
			ordenarGradoDescendente(nodo, i, der);
	}

	private void alterarOrdenNodos() {
		cantColores = 0;
		Collections.shuffle(nodos);

	}

	/**
	 * Que tipo de coloreo se requiere
	 * 
	 * @param codAlgoritmo
	 */
	public void colorear(final int codAlgoritmo) {
		switch (codAlgoritmo) {
		case SECUENCIAL:
			colorearSecuencial();
			break;
		case POWELL:
			colorearPowell();
			break;
		case MATULA:
			colorearMatula();
			break;
		}
	}

	/**
	 * Ejecucion de caso. Metodo estatico
	 * 
	 * @param pathCaso
	 * @param cod_algoritmo
	 */
	public static void ejecutarCaso(final String pathCaso, final String totalFrec, final int codigoAlgoritmo,
			final int porcentaje, final String nombre, final int cantidadEjecuciones,  GrafoNDNP afo) {
		int[] colores = new int[10000];
		int min = Integer.MAX_VALUE, posicion = 0;
		int contadorFrec;
		
		try {
		PrintWriter salida = new PrintWriter(new FileWriter(pathCaso));
		PrintWriter totales = new PrintWriter(new FileWriter(totalFrec));
		HashMap<Integer, Integer> frecuenciaColor = new HashMap<Integer, Integer>();
		salida.println("RESUMEN DE COLORES");

		if (afo != null) {
			for (int i = 0; i < cantidadEjecuciones; i++) {
				afo.alterarOrdenNodos();
				
				
				afo.mezclarPorGrado();
				
				afo.colorear(codigoAlgoritmo);
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
			totales.println("COLOR CANTIDAD");
			frecuenciaColor.forEach((k, v) -> totales.println(k + " " + v));
			totales.println("gradoMax: " + afo.getGradoMaximo());
		}
		System.out.println(nombre);
		System.out.println("minima cantidad de colores: " + min);
		System.out.println("Aparecio por primera vez en la ejecucion: " + posicion);
		if (afo != null)
			System.out.println("Grado Minimo: " + afo.getGradoMinimo() + "Grado Maximo: " + afo.getGradoMaximo());

		salida.close();
		totales.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * metodo para guardar el archivo.
	 * 
	 * @param archivo
	 */
	public void grabarGrafoColoreado(String archivo) {
		PrintWriter salida = null;
		try {

			salida = new PrintWriter(new FileWriter(archivo));
			salida.println(cantNodos + " " + cantColores);

			for (int i = 0; i < cantNodos; i++)
				salida.println(nodos.get(i).getNumero() + " " + nodos.get(i).getColor());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			salida.close();
		}
	}

	public ArrayList<Nodo> getNodos() {
		return nodos;
	}

	public void setNodos(ArrayList<Nodo> nodos) {
		this.nodos = nodos;
	}

	public static int getSecuencial() {
		return SECUENCIAL;
	}

	public static int getPowell() {
		return POWELL;
	}

	public static int getMatula() {
		return MATULA;
	}

}
