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
    int limite=8;
    
    /**
     *
     * @param tablero Representación del tablero de juego
     * @param conecta Número de fichas consecutivas para ganar
     * @return Jugador ganador (si lo hay)
     */
    @Override
    public int turnoJugada(Grid tablero, int conecta) {
        int limiteActual=0;
        // ...
        // Calcular la mejor columna posible donde hacer nuestra turnoJugada
        //Pintar Ficha (sustituir 'columna' por el valor adecuado)
        //Pintar Ficha
        //int columna = getRandomColumn(tablero);
        
        Nodo arrayNodo = new Nodo(tablero);
        
        int mejorMov = 1;           // luego se cambia
        int x=tablero.getColumnas(), y=tablero.getFilas(); //ToDo: Asignación tamaño tablero
        int minInicial, minActual;
        minInicial = Integer.MAX_VALUE;
        int filaAux = 0;
        //unNodo.tableroNodo = tablero.toArray();
        
        int matrix[][] = arrayNodo.tableroNodo.clone();
        for (int j = 0; j < tablero.getColumnas(); j++) {
            if (hayFilas(matrix, j, tablero)) {
                int aux = queFila(matrix, j, tablero);
                matrix[aux][j] = -1;
                mostrarMatriz(matrix, arrayNodo.getColumnaNodo(), arrayNodo.getFilaNodo());
                System.out.println("\n Comenzamos el MiniMax");
                minActual = valorMin(arrayNodo,tablero,x,y,conecta,limiteActual);   //Empieza Minimax
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
    private int valorMax(Nodo nodoActual, Grid tablero, int x, int y, int conecta,int limiteMax) {
        
        System.out.println("Comenzamos valorMax");
        mostrarMatriz(nodoActual.tableroNodo, x, y);
        int termina = tablero.checkWin(x, y, conecta);      //verifica si se gana

        //DefaultMutableTreeNode arbol; //ver si sirve
        
        if (termina != 0) {
            return 1;
            
        } else {
            
            int caminoMax = Integer.MIN_VALUE;
            int aux;
            for (int reCols = 0; reCols < tablero.getColumnas(); reCols++) {
                for (int reFilas = tablero.getFilas()-1; reFilas>=0; reFilas--) {      //Cambiar como valormix
                    if (nodoActual.getTableroNodo(reCols, reFilas) == 0) {
                        nodoActual.rellenarNodo(reCols, reFilas, conecta);

                        
//                        nodoActual.tableroNodo[i][j] = 1;
//                        nodoActual.setCoordenadasNodo(i, j);
                        
                        aux = valorMin(nodoActual,tablero,x,y,conecta,limiteMax);     //puede ser la poda
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
     * @param nodoActual
     * @param tablero
     * @param x
     * @param y
     * @param conecta
     * @return 
     */
    private int valorMin(Nodo nodoActual, Grid tablero, int x, int y, int conecta, int limiteMin){
        
        System.out.println("Comenzamos valorMin");
        mostrarMatriz(nodoActual.tableroNodo, x, y);
        int termina = tablero.checkWin(x, y-1, conecta);    
        
        
        if (termina != 0) {        //ToDo: Gestionar gane max o min o empate (tablero lleno) o nivel profundidad maximo
            return -1;
            
        } else {
            
            int caminoMinimo = Integer.MAX_VALUE;                   //Iniciamos al valor mas alto
            int aux;                                                //auxiliar que nos ayude
            for (int reCols = 0; reCols < tablero.getColumnas(); reCols++) {       //recorrer columnas
                for (int reFilas = tablero.getFilas()-1; reFilas >= 0; reFilas--) {   //recorrer filas desde getfila()-1 hasta 0
                    if (nodoActual.tableroNodo[reCols][reFilas] == 0) {
                        nodoActual.rellenarNodo(reCols, reFilas, conecta);
                        
                       // nodoActual.tableroNodo[reCols][reFilas] = -1;
                       // nodoActual.setCoordenadasNodo(reCols, reFilas);
                        
                        //ecuacion heuristica
                        aux = valorMax(nodoActual,tablero,x,y,conecta,limiteMin);
                        if (aux < caminoMinimo) {
                            caminoMinimo = aux;
                        }
                        nodoActual.tableroNodo[reCols][reFilas] = -1;
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
        int nuevaMatriz[][] = new int[origCol][origFila];
        for (int columna = 0; columna < origCol; columna++) {
            for (int fila = origFila - 1; fila >= 0; fila--) {

                nuevaMatriz[columna][fila] = origMatriz[columna][fila];
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
    
    
    public void mostrarMatriz(int [][] matriz, int numColumnas, int numFilas){
         
        for (int fila = numFilas - 1; fila >= 0; fila--) {
            System.out.println("\n");
            for (int col = 0; col < numColumnas; col++) {
                System.out.print(matriz[col][fila]);
            }
        }
    }

    
    public class Nodo{
        int numColumnas;        //ultima columna usada
        int numFilas;         //ultima fila jugada
        int tableroNodo [][];   //*matriz con el grid en enteros
        
        Nodo [] vecNodos; 
        
        
        private Nodo (Grid tablero){
            numColumnas=tablero.getColumnas();
            numFilas = tablero.getFilas();
            tableroNodo = copiarMatriz(tablero.toArray(),tablero.getColumnas(),tablero.getFilas());    // ??
            vecNodos = new Nodo [numColumnas];
            //tableroGrid = tablero;              // no sirve, es asignación
        }
        
        private Nodo (Nodo orig){
            tableroNodo = orig.getTableroNodo();
            numColumnas = orig.getColumnaNodo();
            numFilas = orig.getFilaNodo();
           //tableroGrid = NodoTablero.tableroGrid; 
        }
        
        public int[][] getTableroNodo(){
            return tableroNodo;
        }
        
        public void setFilaNodo(int num){
            numFilas=num;
        }
        
        public int getFilaNodo(){
            return numFilas;            
        }
        
        public int getTableroNodo(int i,int j){
            return tableroNodo[i][j];                   
        }
        
        public void setTableroNodo(int matrizTablero[][]){
            tableroNodo=matrizTablero;            
        }
        
        public int getColumnaNodo() { 
            return numColumnas;
        }  
        
        public void setColumnaNodo(int columnaNueva){
            numColumnas=columnaNueva;
        }
        
        public void setCoordenadasNodo(int col, int fila){
            setColumnaNodo(col);
            setFilaNodo(fila);
        }
        
        public boolean rellenarNodo(int col, int fila, int jugador){
            setCoordenadasNodo(col, fila);
            this.tableroNodo[col][fila] = jugador;
            vecNodos[col] = new Nodo (this);
            
            return true;
        }
        
    }

} // IAPlayer
