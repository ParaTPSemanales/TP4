package grafos;
/**
 * Clase nodo. Tendra la informacion
 * de cada nodo , como el grado, el color
 * que numero es, etc. 
 * @author grupo10
 *
 */
public class Nodo implements Comparable<Nodo>{
	private int numero;
	private int color;
	private int grado;
	
	/**
	 * Constructor por defecto.
	 */
	public Nodo(){
	this(0,0,0);
	}
	
	/**
	 * Constructor con variables.
	 * @param num
	 * @param color
	 * @param grado
	 */
	public Nodo(final int num,final  int color,final int grado){
		this.color = color;
		this.grado = grado;
		numero = num;
	}
	
	/**
	 * Metodo para incrementar el 
	 * grado
	 */
	public void incrementarGrado(){
		this.grado++;
	}
	
	
	/**
	 * Funcion que compara
	 * dos nodos segun el
	 * grado de cada uno
	 */
	@Override
	public int compareTo(Nodo that) {
		if(this.grado < that.grado)
			return -1;
		else
			if(this.grado > that.grado)
				return 1;
		return 0;
	}
	
	
	/**
	 * Establecer el color
	 * @param color
	 */
	public void setColor(final int color){
		this.color = color;
	}
	
	/**
	 * Obtener color.
	 * @return entero con el color. 
	 */
	public int getColor(){
		return this.color;
	}
	
/**
 * Obtener el grado 
 * @return grado
 */
	public int getGrado(){
		return this.grado;
	}
	
	/**
	 * obtener el numero
	 * de nodo
	 * @return entero
	 */
	public int getNumero(){
		return numero;
	}

	
}
