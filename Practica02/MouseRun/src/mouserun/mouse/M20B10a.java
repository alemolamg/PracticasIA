package mouserun.mouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;
import javafx.util.Pair;
import mouserun.game.Mouse;
import mouserun.game.Grid;
import mouserun.game.Cheese;

/**
 * Clase que contiene el esqueleto del raton base para las prácticas de
 * Inteligencia Artificial del curso 2019-20.
 *
 * @author Cristóbal José Carmona (ccarmona@ujaen.es) y Ángel Miguel García Vico
 * (agvico@ujaen.es)
 * @author Ana Montijano Zaragoza y Alejandro Molero Gómez
 */
public class M20B10a extends Mouse {

    /**
     * Variable para almacenar la ultima celda visitada
     */
    private Grid lastGrid;

    /**
     * Variable con el número de casillas recorridaas
     */
    private long numCasillasVisitadas;  //no lo usamos

    /**
     * Tabla hash para almacenar las celdas visitadas por el raton: Clave:
     * Coordenadas Valor: La celda
     */
    private HashMap<Pair<Integer, Integer>, Grid> celdasVisitadas;

    /**
     * Pila para almacenar el camino recorrido.
     */
    private Stack<Grid> pilaMovimientos;

    /**
     * Constructor (Puedes modificar el nombre a tu gusto).
     */
    public M20B10a() {
        super("M20B10a");
        celdasVisitadas = new HashMap<>();
        pilaMovimientos = new Stack<>();
    }

    /**
     * @brief Método principal para el movimiento del raton. Incluye la gestión
     * de cuando un queso aparece o no.
     * @param currentGrid Celda actual
     * @param cheese Queso
     */
    @Override
    public int move(Grid currentGrid, Cheese cheese) {
        int x = currentGrid.getX();
        int y = currentGrid.getY();

        if (!celdasVisitadas.containsKey(new Pair(x, y))) {      //Vemos si la casilla actual esta en el mapa
            this.incExploredGrids();                            //Aumentamos el numero de casillas visitadas
            celdasVisitadas.put(new Pair(x, y), currentGrid);  //y guardamos la casilla en el mapa
        }

        if (currentGrid.canGoDown()) {                                   //vemos si podemos movernos            
            if (!celdasVisitadas.containsKey(new Pair(x, y - 1))) {    //comprobamos que la casilla es nueva
                pilaMovimientos.push(currentGrid);                   //la guardamos en la pila
                return Mouse.DOWN;                                   //nos movemos
            }
        }

        if (currentGrid.canGoLeft()) {                  //vemos si podemos movernos
            Pair p = new Pair(x - 1, y);            //creamos las nuevas coordenadas
            if (!celdasVisitadas.containsKey(p)) {    //comprobamos que la casilla es nueva
                pilaMovimientos.push(currentGrid);   //la guardamos en la pila
                return Mouse.LEFT;                  //nos movemos
            }
        }

        if (currentGrid.canGoUp()) {                    //vemos si podemos movernos
            Pair p = new Pair(x, y + 1);               //creamos las nuevas coordenadas
            if (!celdasVisitadas.containsKey(p)) {        //comprobamos que la casilla es nueva
                pilaMovimientos.push(currentGrid);       //la guardamos en la pila
                return Mouse.UP;                        //nos movemos
            }
        }

        if (currentGrid.canGoRight()) {                 //vemos si podemos movernos
            Pair p = new Pair(x + 1, y);                //creamos las nuevas coordenadas
            if (!celdasVisitadas.containsKey(p)) {        //comprobamos que la casilla es nueva
                pilaMovimientos.push(currentGrid);       //la guardamos en la pila
                return Mouse.RIGHT;                     //nos movemos
            }
        }

        int salida = posRegreso(currentGrid);
        System.out.println("El movimiento es: " + salida);
        pilaMovimientos.pop();
        return salida;
    }

    /**
     *
     * @param celdaActual celda donde se encuentra el ratón
     * @return movimiento de regreso del raton
     */
    private int posRegreso(Grid celdaActual) {   //Falta un caso por tener en cuenta

        if (pilaMovimientos.peek().getX() == celdaActual.getX()) {
            if (pilaMovimientos.peek().getY() < celdaActual.getY()) {
                return Mouse.DOWN;  //bajamos el ratón
            } else if (pilaMovimientos.peek().getY() > celdaActual.getY()) {
                return Mouse.UP;
            }
        }  //else
        if (pilaMovimientos.peek().getY() == celdaActual.getY()) {
            if (pilaMovimientos.peek().getX() < celdaActual.getX()) {
                return Mouse.LEFT;
            } else if (pilaMovimientos.peek().getX() > celdaActual.getX()) {
                return Mouse.RIGHT;
            }
        }

        return -2;  //fallo las dos coordenadas son diferentes, servirá para implementar bombas
    }

    /**
     * @brief Método que se llama cuando aparece un nuevo queso
     */
    @Override
    public void newCheese() {
    }

    /**
     * @brief Método que se llama cuando el raton pisa una bomba
     */
    @Override
    public void respawned() {
        this.pilaMovimientos = new Stack<>();
    }

    /**
     * @brief Método que devuelve si de una casilla dada, está contenida en el
     * mapa de celdasVisitadas
     * @param casilla Casilla que se pasa para saber si ha sido visitada
     * @return True Si esa casilla ya la había visitado
     */
    public boolean visitada(Grid casilla) {
        Pair par = new Pair(casilla.getX(), casilla.getY());
        return celdasVisitadas.containsKey(par);
    }

    /**
     * @brief Método para calcular si una casilla está en una posición relativa
     * respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición Y de la actual es mayor que la de la anterior
     */
    public boolean actualArriba(Grid actual, Grid anterior) {
        return actual.getY() > anterior.getY();
    }

    /**
     * @brief Método para calcular si una casilla está en una posición relativa
     * respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición Y de la actual es menor que la de la anterior
     */
    public boolean actualAbajo(Grid actual, Grid anterior) {
        return actual.getY() < anterior.getY();
    }

    /**
     * @brief Método para calcular si una casilla está en una posición relativa
     * respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición X de la actual es mayor que la de la anterior
     */
    public boolean actualDerecha(Grid actual, Grid anterior) {
        return actual.getX() > anterior.getX();
    }

    /**
     * @brief Método para calcular si una casilla está en una posición relativa
     * respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición X de la actual es menor que la de la anterior
     */
    public boolean actualIzquierda(Grid actual, Grid anterior) {
        return actual.getX() < anterior.getX();
    }

}
