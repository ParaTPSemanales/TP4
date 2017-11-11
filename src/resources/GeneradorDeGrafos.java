package resources;

import java.util.Random;

import grafos.GrafoNDNP;
import grafos.MatrizSimetrica;

/**
 * Generador de grafos aleatorios dados N y el porcentaje de adyacencia.
 * 
 * @author Grupo10
 *
 */
public class GeneradorDeGrafos {

	/**
	 * Segun porcentaje de adyacencia
	 * 
	 * @param cantNodos
	 * @param porcAdy
	 * @return
	 * @throws GrafoNDNP
	 */
	public static GrafoNDNP generarGrafoAleatorioConPorcentajeDeAdyacencia(final int cantNodos, final double porcAdy)
			throws GrafoException {
		Random r = new Random();
		MatrizSimetrica matriz = new MatrizSimetrica(cantNodos);
		int rand;
		int maxAristas = (cantNodos * (cantNodos - 1)) / 2;
		Double aux = (porcAdy * matriz.getTam()) / 100;
		int cantAristas = (int) Math.round(aux);
		//System.out.println(maxAristas + "\t" + cantAristas);// Para seguimiento.

		for (int i = 0; i < cantAristas; i++) {
			rand = r.nextInt(maxAristas);
			if (matriz.getValor(rand) == true)
				i--;
			else
				matriz.setValor(rand, true);
		}

		double porcAdyReal = (cantAristas * 100) / maxAristas;

		GrafoNDNP grafo = new GrafoNDNP(cantNodos, matriz, cantAristas, porcAdyReal);
		if (grafo.esConexo()) {
			return grafo;
		} else {
			throw new GrafoException("Error en la creacion de grafo Aleatorio con porcentaje de adyacencia " + porcAdy);
		}
	}

	/**
	 * una probabilidad para cada arista.
	 * 
	 * @param cantNodos
	 * @param probabilidad
	 * @return
	 * @throws GrafoNDNP
	 */
	public static GrafoNDNP generarGrafoAleatorioConProbabilidad(final int cantNodos, final double probabilidad)
			throws GrafoException {
		Random r = new Random();
		MatrizSimetrica matriz = new MatrizSimetrica(cantNodos);
		final int maxAristas = matriz.getTam();

		int cantAristas = 0;
		for (int i = 0; i < maxAristas; i++) {
			double porc = r.nextDouble();
			if (porc < probabilidad) {
				matriz.setValor(i, true);
				cantAristas++;
			} else {
				matriz.setValor(i, false);
			}
		}
		double porcAdyReal = (cantAristas * 100) / maxAristas;
		GrafoNDNP grafo = new GrafoNDNP(cantNodos, matriz, cantAristas, porcAdyReal);
		if (grafo.esConexo()) {
			return grafo;
		} else {
			throw new GrafoException();
		}
	}

	/**
	 * Generador de grafos regulares dados N y el grado.
	 * 
	 * @param cantNodos
	 * @param grado
	 * @return
	 * @throws GrafoNDNPInvalidoException
	 */
	public static GrafoNDNP generarGrafoRegularGradoN(final int cantNodos, final int grado) throws GrafoException {
		if (grado >= cantNodos || cantNodos <= 0 || grado < 0)
			throw new GrafoException("Error en la creacion de un grafo Regular con grado igual a " + grado);
		MatrizSimetrica matriz = new MatrizSimetrica(cantNodos);
		int cantAristas = 0;
		if (cantNodos % 2 == 0) { // par
			if (grado % 2 == 1) {
				int saltoImpar = (cantNodos / 2);
				for (int i = 0; i < cantNodos / 2; i++) {
					matriz.setValor(i, i + saltoImpar, true);
					cantAristas++;
				}
			}
			int saltoNodo = 1;
			for (int g = 0; g < grado / 2; g++) {
				for (int i = 0; i < cantNodos; i++) {
					matriz.setValor(i, (i + saltoNodo) % 6, true);
					cantAristas++;
				}
				saltoNodo++;
			}
		} else { // impar
			if (grado % 2 == 1)
				throw new GrafoException();
		}
		GrafoNDNP g = new GrafoNDNP(cantNodos, matriz, cantAristas, (cantAristas * 100) / ((cantNodos * (cantNodos - 1)) / 2));
		return g;
	}

	/**
	 * Generador de grafos regulares dados N y el porcentaje de adyacencia.
	 * @param cantNodos
	 * @param porAdy
	 * @return
	 */

//	public static GrafoNDNP generarGrafoRegularConPorcentajeDeAdyacencia(final int cantNodos,final int porAdy) {
//		double grado1 = (porAdy * (cantNodos - 1)) / 100.0;
//		//Con este nos quedamos
//		int grado = (int) grado1;
//		GrafoNDNP retorno = null;
//		if (cantNodos < 1 || grado < 0 || grado >= cantNodos || (cantNodos != 1 && grado == 0)
//				|| (cantNodos != 2 && grado == 1) || (cantNodos % 2 != 0 && grado % 2 != 0) || (grado -grado1 != 0)) {
//			System.out.println("no se puede generar el grafo");
//			return retorno;
//		}
		
	public static GrafoNDNP generarGrafoRegularConPorcentajeDeAdyacencia(final int cantNodos, int porAdy) {
		double gradoReal = (porAdy * (cantNodos - 1)) / 100.0;
		int grado = (int) gradoReal;
		double pepe = (grado*porAdy)/gradoReal;

		if(grado - gradoReal != 0) {
			 pepe = (grado*porAdy)/gradoReal;//Lo redondeamo
		}
		GrafoNDNP retorno = null;
		if (cantNodos < 1 || grado < 0 || grado >= cantNodos || (cantNodos != 1 && grado == 0)
				|| (cantNodos != 2 && grado == 1) || (cantNodos % 2 != 0 && grado % 2 != 0)) {

			System.out.println("no se puede generar el grafo");
			return retorno;
		}

		MatrizSimetrica matriz = new MatrizSimetrica(cantNodos);

		// Camino externo.
		for (int x = 0; x < cantNodos - 1; x++)
			matriz.setValor(x, x + 1, true);
		if (cantNodos > 2) { 
			/**Si hay un nodo no necesita aristas. Si hay dos,
			   la unica arista la completa en el for
				anterior**/
			matriz.setValor(0, cantNodos - 1, true);
			grado -= 2; 
			 /**Coloco todas las aristas entre grafos consecutivos,
			 entonces cada nodo ya tiene 2 aristas.
			 Cruz.**/
			if (grado % 2 != 0) {
				for (int x = 0; x < cantNodos / 2; x++)
					matriz.setValor(x, x + (cantNodos / 2), true);
				grado--;
			}
			
			int cantVeces = grado / 2;
			int saltear = 2;
			for (int x = 0; x < cantVeces; x++) {
				for (int nodoActual = 0; nodoActual < cantNodos; nodoActual++) {
					int aux = nodoActual + saltear;
					if (aux > cantNodos - 1)
						aux -= cantNodos;
					matriz.setValor(nodoActual, aux, true);
				}
				saltear++;
			}
		}

		
		retorno = new GrafoNDNP(cantNodos, matriz, (cantNodos * grado) / 2, porAdy);

		retorno = new GrafoNDNP (cantNodos, matriz, (cantNodos * grado) / 2, porAdy);

		return retorno ;
	}

	/**
	 * Generador de grafos n-partitos,
	 * Cantidad de nodos y N partes.
	 * @param cantNodos
	 * @param n
	 * @return
	 * @throws GrafoNDNP
	 */

	public static GrafoNDNP generarGrafoNPartito(final int cantNodos,final int n) throws GrafoException {
		
		if (cantNodos % n != 0)
			throw new GrafoException("Error en creacion de grafos");
		
		
		MatrizSimetrica matriz = new MatrizSimetrica(cantNodos);
		final int aristasContiguas = (cantNodos / n) - 1;
		int aux = aristasContiguas;
		int cantAristas = 0;
		for (int i = 0; i < cantNodos; i++) {
			if (aux > 0) {
				matriz.setValor(i, i + 1, true);
				cantAristas++;
				aux--;
			} else
				aux = aristasContiguas;
		}
		return new GrafoNDNP(cantNodos, matriz, cantAristas, (cantAristas * 100) / ((cantNodos * (cantNodos - 1)) / 2));
	}
}
