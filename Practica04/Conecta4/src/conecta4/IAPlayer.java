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
        
        Nodo unNodo=new Nodo(tablero);
        
        int mejorMov = 1;
        int x=tablero.getColumnas(), y=tablero.getFilas();
        int min, minActual;
        min = Integer.MAX_VALUE;
        int filaAux = 0;
        //unNodo.tableroNodo = tablero.toArray();
        
        int matrix[][] = unNodo.tableroNodo.clone();
        for (int j = 0; j < tablero.getColumnas(); j++) {
            if (hayFilas(matrix, j, tablero)) {
                int aux = queFila(matrix, j, tablero);
                matrix[aux][j] = -1;
                minActual = valorMin(unNodo,tablero,x,y,conecta);
                matrix[aux][j] = 0;
                if (minActual < min) {
                    filaAux = aux;
                    min = minActual;
                    mejorMov = j;
                }
            }
        }
               
        tablero.setButton(filaAux, mejorMov);
        return tablero.checkWin(filaAux, mejorMov, conecta);
        //return tablero.checkWin(tablero.setButton(columna, Conecta4.PLAYER2), columna, conecta);

    } // turnoJugada
    
    private int valorMax(Nodo nodoActual, Grid tablero,int x,int y, int conecta){
        
      //int termina= esTerminal(nodoActual, conecta, tablero);  //falta completar
      int termina = tablero.checkWin(x, y, conecta);      //verifica si se gana
      
      //DefaultMutableTreeNode arbol; //ver si sirve
      
        if (termina == 1) {
            return 1;
        } else {
            int maxCamino = Integer.MAX_VALUE;
            int aux;
            for (int i = 0; i < tablero.getColumnas(); i++) {
                for (int j = 0; j < tablero.getFilas(); j++) {
                    if (nodoActual.getTableroNodo(i, j) == 0) {
                        nodoActual.tableroNodo[i][j] = 1;
                        
                        aux = valorMin(nodoActual,tablero,x,y,conecta);     //puede ser la poda
                        if (aux > maxCamino) {
                            maxCamino = aux;
                        }
                        //matriz[i][j] = 1;
                    }
                }
            }
            return maxCamino;
        }
    };
    
    
    
    private int valorMin(Nodo nodoActual, Grid tablero, int x, int y, int conecta){
        
        //int termina = esTerminal(nodoActual, conecta, tablero);      //falta completar
        int termina = tablero.checkWin(x, y, conecta);      //falta completar
        
        if (termina == -1) {
            return -1;
            
        } else {
            int minimoCamino = Integer.MAX_VALUE;
            int aux;
            for (int i = 0; i < tablero.getColumnas(); i++) {
                for (int j = 0; j < tablero.getFilas(); j++) {
                    if (nodoActual.tableroNodo[i][j] == 0) {
                        nodoActual.tableroNodo[i][j] = -1;
                        
                        aux = valorMax(nodoActual,tablero,x,y,conecta);
                        if (aux < minimoCamino) {
                            minimoCamino = aux;
                        }
                        nodoActual.tableroNodo[i][j] = -1;
                        //matriz[i][j] = -1;
                    }
                }
            }
            return minimoCamino;
        }

        
    };
    
    
    private int esTerminal(Nodo nodoActual, int conecta,int x, int y, Grid tablero){ //no está corregido, necesitamos una x y una y
    
        //int x=tablero.getColumnas(), y=tablero.getFilas();  //ToDo: probar, no se sabe si funciona
        int ganar1 = 0;
        int ganar2 = 0;
        int ganador = 0;
        boolean salir = false;
        // comprobar vertical
        for (int j = 0; (j < tablero.getColumnas()) && !salir; j++) {
            if (nodoActual.tableroNodo[j][y] != 0) {
                if (nodoActual.tableroNodo[j][y] == 1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = 1;
                    salir = true;
                }
                if (!salir) {
                    if (nodoActual.tableroNodo[j][y] == -1) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = -1;
                        salir = true;
                    }
                }
            } else {
                ganar1 = 0;
                ganar2 = 0;
            }
        }
        // Comprobar horizontal
        ganar1 = 0;
        ganar2 = 0;
        for (int i = 0; (i < tablero.getColumnas()) && !salir; i++) {
            if (nodoActual.tableroNodo[x][i] != 0) {
                if (nodoActual.tableroNodo[x][i] == 1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = 1;
                    salir = true;
                }
                if (ganador != 1) {
                    if (nodoActual.tableroNodo[x][i] == -1) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = -1;
                        salir = true;
                    }
                }
            } else {
                ganar1 = 0;
                ganar2 = 0;
            }
        }
        // Comprobar oblicuo. De izquierda a derecha
        ganar1 = 0;
        ganar2 = 0;
        int a = x;
        int b = y;
        while (b > 0 && a > 0) {
            a--;
            b--;
        }
        while (b < tablero.getColumnas()&& a < tablero.getFilas()&& !salir) {
            if (nodoActual.tableroNodo[a][b] != 0) {
                if (nodoActual.tableroNodo[a][b] == 1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = 1;
                    salir = true;
                }
                if (ganador != 1) {
                    if (nodoActual.tableroNodo[a][b] == -1) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = -1;
                        salir = true;
                    }
                }
            } else {
                ganar1 = 0;
                ganar2 = 0;
            }
            a++;
            b++;
        }
        // Comprobar oblicuo de derecha a izquierda 
        ganar1 = 0;
        ganar2 = 0;
        a = x;
        b = y;
        //buscar posición de la esquina
        while (b < tablero.getColumnas()- 1 && a > 0) {
            a--;
            b++;
        }
        while (b > -1 && a < tablero.getFilas() && !salir) {
            if (nodoActual.tableroNodo[a][b] != 0) {
                if (nodoActual.tableroNodo[a][b] == 1) {
                    ganar1++;
                } else {
                    ganar1 = 0;
                }
                // Gana el jugador 1
                if (ganar1 == conecta) {
                    ganador = 1;
                    salir = true;
                }
                if (ganador != 1) {
                    if (nodoActual.tableroNodo[a][b] == -1) {
                        ganar2++;
                    } else {
                        ganar2 = 0;
                    }
                    // Gana el jugador 2
                    if (ganar2 == conecta) {
                        ganador = -1;
                        salir = true;
                    }
                }
            } else {
                ganar1 = 0;
                ganar2 = 0;
            }
            a++;
            b--;
        }

        return ganador;
    }
    
    private boolean hayFilas(int matrix[][], int x, Grid tablero) {
        for (int i = tablero.getFilas() - 1; i >= 0; i--) {
            if (matrix[i][x] != 1 && matrix[i][x] != -1) {
                return true;
            }
        }
        return false;
    }
     
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
    
    public class Nodo{
        int columJugada;        //ultima columna usada
        int filaJugada;         //ultima fila jugada
        int tableroNodo [][];   //*matriz con el grid en enteros
        Grid tableroGrid;       //grib con el tablero
        
        
        private Nodo (Grid tablero){
            tableroNodo = tablero.toArray();
            tableroGrid = tablero;
            
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
        
    }

} // IAPlayer
