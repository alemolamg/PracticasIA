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
        int columna = getRandomColumn(tablero);
        
        
        
        return tablero.checkWin(tablero.setButton(columna, Conecta4.PLAYER2), columna, conecta);

    } // turnoJugada
    
    private int valorMax(int matriz[][], Grid tablero, int conecta){
      int termina= esTerminal(matriz, conecta, tablero);  //falta completar
        
        if (termina == 1) {
            return 1;
        } else {
            int maxCamino = Integer.MAX_VALUE;
            int aux;
            for (int i = 0; i < tablero.getColumnas(); i++) {
                for (int j = 0; j < tablero.getFilas(); j++) {
                    if (matriz[i][j] == 0) {
                        matriz[i][j] = 1;
                        aux = valorMin(matriz,tablero,conecta);
                        if (aux > maxCamino) {
                            maxCamino = aux;
                        }
                        matriz[i][j] = 1;
                    }
                }
            }
            return maxCamino;
        }
    };
    
    
    
    private int valorMin(int matriz[][], Grid tablero, int conecta){
        
        int termina= esTerminal(matriz, conecta, tablero);  //falta completar
        
        if (termina == -1) {
            return -1;
        } else {
            int minimoCamino = Integer.MAX_VALUE;
            int aux;
            for (int i = 0; i < tablero.getColumnas(); i++) {
                for (int j = 0; j < tablero.getFilas(); j++) {
                    if (matriz[i][j] == 0) {
                        matriz[i][j] = -1;
                        aux = valorMax(matriz,tablero,conecta);
                        if (aux < minimoCamino) {
                            minimoCamino = aux;
                        }
                        matriz[i][j] = -1;
                    }
                }
            }
            return minimoCamino;
        }

        
    };
    
    
    private int esTerminal(int matrix[][], int conecta, Grid tablero) {
        int ganar1 = 0;
        int ganar2 = 0;
        int ganador = 0;
        boolean salir = false;
        // comprobar vertical
        for (int j = 0; (j < tablero.getColumnas()) && !salir; j++) {
            if (matrix[j][y] != 0) {
                if (matrix[j][y] == 1) {
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
                    if (matrix[j][y] == -1) {
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
            if (matrix[x][i] != 0) {
                if (matrix[x][i] == 1) {
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
                    if (matrix[x][i] == -1) {
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
            if (matrix[a][b] != 0) {
                if (matrix[a][b] == 1) {
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
                    if (matrix[a][b] == -1) {
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
            if (matrix[a][b] != 0) {
                if (matrix[a][b] == 1) {
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
                    if (matrix[a][b] == -1) {
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
    
    
    private class Nodo{
        
        
    }

} // IAPlayer
