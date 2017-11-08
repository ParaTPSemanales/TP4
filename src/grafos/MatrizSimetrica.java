package grafos;

import java.util.Random;

/**
 * 
 * @author Grupo10
 *Clase que tendra la matriz simetrica
 *la cual representa un grafo.
 */
public class MatrizSimetrica {
	private boolean[] vectorEquivalente;
	private int dimension;
	private int orden;// orden de la matriz
	private int tam;// tamaño del vector

	/**
	 * Constructor
	 * @param dimension
	 */
	public MatrizSimetrica(final int dimension) {
		this.dimension = dimension;

		if (dimension < 0)
			this.dimension = 1;
		
		tam = (this.dimension * (this.dimension - 1)) / 2; 
		vectorEquivalente = new boolean[tam]; // Crea el vector Equivalente.
		Double aux = ((1 + Math.sqrt(1 + 8 * vectorEquivalente.length)) / 2);
		orden = aux.intValue();
		// generarGrafo(45);
	}

	/**
	 * metodo que sirve para generar un grafo
	 * a partir de su porcentaje de adyacencia.
	 * @param porcAdy
	 */
	public void generarGrafo(final double porcAdy) {
		Random r = new Random();
		int rand;
		Double aux = (porcAdy * this.tam) / 100;
		
		int cantAristas = (int) Math.round(aux);
		for (int i = 0; i < cantAristas; i++) {
			if (vectorEquivalente[rand = r.nextInt(cantAristas + 1)] == true)
				i--;
			else
				vectorEquivalente[rand] = true;
		}
	}

	/**
	 * Obtener el valor de la matriz a partir
	 * de su vector equivalente
	 * @param fila
	 * @param col
	 * @return
	 */
	public boolean getValor(final int fila,final  int col) {
		
		if (fila == col)
			return true;
		int i;
		if (fila > col)
			i = (col * orden) + fila - ((col * col) + (3 * col) + 2) / 2;
		else
			i = (fila * orden) + col - ((fila * fila) + (3 * fila) + 2) / 2;
		return vectorEquivalente[i];
	}

	/**
	 * Establecer un valor en la matriz
	 * a partir de su vector equivalente.
	 * @param fila
	 * @param col
	 * @param valor
	 */
	public void setValor(final int fila,final int col,final boolean valor) {
	
		if (fila != col) {
			int posicion;
			if (fila > col)
				posicion = (col * orden) + fila - ((col * col) + (3 * col) + 2) / 2;
			else
				posicion = (fila * orden) + col - ((fila * fila) + (3 * fila) + 2) / 2;
			
			vectorEquivalente[posicion] = valor;
		}
		
	}

	/**
	 * Mostrar la matriz resultante.
	 */
	public void mostrarMatriz() {
		System.out.print(" ");
		for (int i = 0; i < dimension; i++) {
			System.out.print("|" + i + "\t");
		}
		System.out.println("|");
		for (int i = 0; i < dimension; i++) {
			System.out.print(i);
			for (int j = 0; j < dimension; j++) {
				System.out.print("|" + getValor(i, j) + "\t");
			}
			System.out.println("|");
	}
	}
	
	
	public void setValor(int posicion, boolean val) {
		vectorEquivalente[posicion] = val;
	}



	public int getTam() {
		return tam;
	}
	
	
	public boolean getValor(final int i) {
		return vectorEquivalente[i];
	}

	public int getDimension() {
		return dimension;
	}

}
