package conecta4;

import java.util.TreeMap;
import javax.swing.tree.DefaultMutableTreeNode;
        
/**
 *
 * @author José María Serrano
 * @version 1.5 Departamento de Informática. Universidad de Jáen

 Inteligencia Artificial. 2º Curso. Grado en Ingeniería Informática

 Clase IAPlayer para representar al jugador CPU que usa una técnica de IA

 Esta clase es la que tenemos que implementar y completar
 *
 */
public class IAPlayer extends Player {  
    Nodo arrayNodo;    
    
    /**
     *
     * @param tablero Representación del tablero de juego
     * @param conecta Número de fichas consecutivas para ganar
     * @return Jugador ganador (si lo hay)
     */
    @Override
    public int turnoJugada(Grid tablero, int conecta) {

        // ...
        // Calcular la mejor columna posible donde hacer nuestra turnoJugada
        //Pintar Ficha (sustituir 'columna' por el valor adecuado)
        //Pintar Ficha
        //int columna = getRandomColumn(tablero);
        
        Nodo unNodo = new Nodo(tablero);
        
        int mejorMov = 1;           // luego se cambia
        int x=tablero.getColumnas()-1, y=tablero.getFilas()-1; //ToDo: Asignación tamaño tablero
        int minInicial, minActual;
        minInicial = Integer.MAX_VALUE;
        int filaAux = 0;
        //unNodo.tableroNodo = tablero.toArray();
        
        int matrix[][] = unNodo.tableroNodo.clone();
        for (int j = 0; j < tablero.getColumnas(); j++) {
            if (hayFilas(matrix, j, tablero)) {
                int aux = queFila(matrix, j, tablero);
                matrix[aux][j] = -1;
                minActual = valorMin(unNodo,tablero,x,y,conecta);   //Empieza Minimax
                matrix[aux][j] = 0;
                if (minActual < minInicial) {
                    filaAux = aux;
                    minInicial = minActual;
                    mejorMov = j;
                }
            }
        }
        int meterButton = tablero.setButton(mejorMov, Conecta4.PLAYER2);
               
        return tablero.checkWin(mejorMov, meterButton, conecta);
        //return tablero.checkWin(tablero.setButton(columna, Conecta4.PLAYER2), columna, conecta);

    } // turnoJugada
    
    /**
     * 
     * @param nodoActual
     * @param tablero
     * @param x
     * @param y
     * @param conecta
     * @return 
     */
    private int valorMax(Nodo nodoActual, Grid tablero, int x, int y, int conecta) {

        int termina = tablero.checkWin(x, y, conecta);      //verifica si se gana

        //DefaultMutableTreeNode arbol; //ver si sirve
        
        if (termina != 0) {
            return 1;
            
        } else {
            
            int caminoMax = Integer.MIN_VALUE;
            int aux;
            for (int i = 0; i < tablero.getColumnas(); i++) {
                for (int j = tablero.getFilas()-1; j>=0; j--) {      //Cambiar como valormix
                    if (nodoActual.getTableroNodo(i, j) == 0) {
                        nodoActual.tableroNodo[i][j] = 1;
                        nodoActual.setUltimoMov(i, j);
                        
                        aux = valorMin(nodoActual,tablero,x,y,conecta);     //puede ser la poda
                        if (aux > caminoMax) {
                            caminoMax = aux;
                        }
                        //matriz[i][j] = 1;
                    }
                }
            }
            return caminoMax;
        }
    };

    
    /**
     * 
     * @param nodoActual
     * @param tablero
     * @param x
     * @param y
     * @param conecta
     * @return 
     */
    private int valorMin(Nodo nodoActual, Grid tablero, int x, int y, int conecta){
        
        int termina = tablero.checkWin(x, y, conecta);      
        
        if (termina != 0) {        // Gestionar gane max o min o empate (tablero lleno) o nivel profundidad maximo
            return -1;
            
        } else {
            
            int caminoMinimo = Integer.MAX_VALUE;                   //Iniciamos al valor mas alto
            int aux;                                                //auxiliar que nos ayude
            for (int i = 0; i < tablero.getColumnas(); i++) {       //recorrer columnas
                for (int j = tablero.getFilas()-1; j >= 0; j--) {   //recorrer filas desde getfila()-1 hasta 0
                    if (nodoActual.tableroNodo[i][j] == 0) {
                        nodoActual.tableroNodo[i][j] = -1;
                        nodoActual.setUltimoMov(i, j);
                        
                        //ecuacion heuristica
                        aux = valorMax(nodoActual,tablero,x,y,conecta);
                        if (aux < caminoMinimo) {
                            caminoMinimo = aux;
                        }
                        nodoActual.tableroNodo[i][j] = -1;
                        //matriz[i][j] = -1;
                    }
                }
            }
            return caminoMinimo;
        }

        
    };
    
    /**
     * 
     * @param matrix
     * @param x
     * @param tablero
     * @return 
     */
    private boolean hayFilas(int matrix[][], int x, Grid tablero) {
        for (int i = tablero.getFilas() - 1; i >= 0; i--) {
            if (matrix[i][x] != 1 && matrix[i][x] != -1) {
                return true;
            }
        }
        return false;
    }
    
    
    public int[][] copiarMatriz(int[][] origMatriz, int origCol , int origFila){
        int nuevaMatriz[][]= new int[origCol][origFila];
        for (int fila = origFila - 1; fila >= 0; fila--) {
            for (int columna = 0; columna < origCol; columna++) {
                nuevaMatriz[columna][fila]=origMatriz[columna][fila];
            }
        }
        return nuevaMatriz;
    }
    
    /**
     * 
     * @param matrix
     * @param x
     * @param tablero
     * @return 
     */
    private int queFila(int matrix[][], int x, Grid tablero) {
        int cont = 0;
        for (int i = tablero.getFilas() - 1; i >= 0; i--) {
            if (matrix[i][x] != 1 && matrix[i][x] != -1) {
                cont = i;
                break;
            }
        }
        return cont;
    }
    
    
    private int heuristica(int matrix[][], int conecta, int x, int y, Grid tablero) {
        int sumaJugador1 = 0;
        int sumaJugador2 = 0;

        for (int k = 2; k <= conecta; k++) {
            int paresVerticales1 = 0;
            int paresVerticales2 = 0;
            for (int j = 0; j < tablero.getColumnas(); j++) {
                int acV1 = 0;
                int acV2 = 0;
                for (int i = 0; i < tablero.getFilas(); i++) {
                    if (matrix[i][j] == 1) {
                        acV2=0;
                        acV1++;
                        
                        if(k == acV1) {
                            paresVerticales1++;
                        }
                    } else if (matrix[i][j] == -1) {
                        acV1=0;
                        acV2++;
                        if(k == acV2) {
                            paresVerticales2++;
                        }
                    }
                }
            }
            sumaJugador1 += k * paresVerticales1;
            sumaJugador2 += k * paresVerticales2;
        }

        for (int k = 2; k <= conecta; k++) {
            int paresHorizontales1 = 0;
            int paresHorizontales2 = 0;
            for (int i = 0; i < tablero.getFilas(); i++) {
                int acH1 = 0;
                int acH2 = 0;
                for (int j = 0; j < tablero.getColumnas(); j++) {
                    if (matrix[i][j] == 1) {
                        acH2 = 0;
                        acH1++;
                        if(k == acH1) {
                            paresHorizontales1++;
                        }
                    } else if (matrix[i][j] == -1) {
                        acH1 = 0;
                        acH2++;
                       
                        if(k == acH2) {
                            paresHorizontales2++;
                        }
                    }
                }
            }

            sumaJugador1 += k * paresHorizontales1;
            sumaJugador2 += k * paresHorizontales2;
        }

        return sumaJugador1 - sumaJugador2;
    }
    
    
    public class Nodo{
        int columJugada;        //ultima columna usada
        int filaJugada;         //ultima fila jugada
        int tableroNodo [][];   //*matriz con el grid en enteros
        Grid tableroGrid;       //grib con el tablero
        
        
        private Nodo (Grid tablero){
            tableroNodo = tablero.toArray();    // ??
            tableroGrid = tablero;              // no sirve, es asignación
            
        }
        
        private Nodo (Nodo NodoTablero){
            tableroNodo = NodoTablero.getTableroNodo();
            tableroGrid = NodoTablero.tableroGrid; 
        }
        
        public int[][] getTableroNodo(){
            return tableroNodo;
        }
        
        public void setFilaNodo(int num){
            filaJugada=num;
        }
        
        public int getFilaNodo(){
            return filaJugada;            
        }
        
        public int getTableroNodo(int i,int j){
            return tableroNodo[i][j];                   
        }
        
        public void setTableroNodo(int matrizTablero[][]){
            tableroNodo=matrizTablero;            
        }
        
        public int getColumnaJugada() { 
            return columJugada;
        }  
        
        public void setColumnaJugada(int columnaNueva){
            columJugada=columnaNueva;
        }
        
        public void setUltimoMov(int col, int fila){
            setColumnaJugada(col);
            setFilaNodo(fila);
        }
        
    }

} // IAPlayer
