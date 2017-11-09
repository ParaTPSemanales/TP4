package grafos;

public class BusquedaEnProfundidad {
	private boolean[] visitados;    
	private int count = 0;  
	
	public BusquedaEnProfundidad(GrafoNDNP grafo, int nodo) {
		visitados = new boolean[grafo.getCantidadNodos()];
		dfs(grafo,nodo);
	}
	
	private void dfs(GrafoNDNP grafo, int nodo) {
       count++;
       visitados[nodo] = true;
       for (int w : grafo.nodosAdyacentesA(nodo)) {
           if (!visitados[w]) {
               dfs(grafo, w);
           }
       }
	}	
	
	public boolean estaVisitado(int v) {
        return visitados[v];
    }
}
