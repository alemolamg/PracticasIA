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
 * Clase que contiene el esqueleto del raton base para las prácticas de Inteligencia Artificial del curso 2019-20.
 * 
 * @author Cristóbal José Carmona (ccarmona@ujaen.es) y Ángel Miguel García Vico (agvico@ujaen.es)
 * @author Ana Montijano Zaragoza y Alejandro Molero Gómez
 */
public class MXXA04 extends Mouse {

    /**
     * Variable para almacenar la ultima celda visitada
     */
    private Grid lastGrid;
    
    /**
     * Variable con el número de casillas recorridaas
     */
    private long numCasillasVisitadas;
    
    /**
     * Tabla hash para almacenar las celdas visitadas por el raton:
     * Clave: Coordenadas
     * Valor: La celda
     */
    private HashMap<Pair<Integer, Integer>, Grid> celdasVisitadas;
    
    /**
     * Pila para almacenar el camino recorrido.
     */
    private Stack<Grid> pilaMovimientos;
    
    
    /**
     * Constructor (Puedes modificar el nombre a tu gusto).
     */
    public MXXA04() {
        super("MXXA04");
        celdasVisitadas = new HashMap<>();
        pilaMovimientos = new Stack<>();
    }

    /**
     * @brief Método principal para el movimiento del raton. Incluye la gestión de cuando un queso aparece o no.
     * @param currentGrid Celda actual
     * @param cheese Queso
     */
    @Override
    public int move(Grid currentGrid, Cheese cheese) {  

        
        int x=currentGrid.getX();
        int y=currentGrid.getY();
           
        
        if(!celdasVisitadas.containsKey(new Pair(x, y))){      //Vemos si la casilla actual esta en el mapa
            this.incExploredGrids();                            //Aumentamos el numero de casillas visitadas
//            numCasillasVisitadas++;                             //aumentamos las casillas visitadas
            celdasVisitadas.put(new Pair(x, y), currentGrid);  //y guardamos la casilla en el mapa
        }   
        
        
        if (currentGrid.canGoDown()) {              //probamos si puede ir hacia arriba
                Pair p = new Pair(x, y - 1);        //creamos un pair de la casilla superior
                if(!celdasVisitadas.containsKey(p)){//si no esta contenida en el mapa
//                    Grid nuevaCasilla= new Grid(x,y-1);
                    pilaMovimientos.add(currentGrid);  //metemos en movimiento en la pila
                    return Mouse.DOWN;              //devolvemos el movimiento
                }
        }
        
        if (currentGrid.canGoLeft()) {
                Pair p = new Pair(x - 1, y);
                if(!celdasVisitadas.containsKey(p)){
                    pilaMovimientos.add(currentGrid);
                    return Mouse.LEFT;
                }
        }
        
        if (currentGrid.canGoRight()) {
            Pair p = new Pair(x + 1, y);
            if(!celdasVisitadas.containsKey(p)){
                pilaMovimientos.add(currentGrid);
                return Mouse.RIGHT;
            }
        }
        
        if (currentGrid.canGoUp()) {
            Pair p = new Pair(x , y + 1);
            if(!celdasVisitadas.containsKey(p)){
                pilaMovimientos.add(currentGrid);
                return Mouse.UP;
            }
        }
        
//        int salida=posicionRelativa(currentGrid);
//        return salida;                                // idea Mental
        
        return posicionRelativa(currentGrid);
        
    }
    
    int posicionRelativa(Grid casillaActual){
        if(pilaMovimientos.peek().equals(casillaActual))
            return -1;  //Es la misma casilla, fallo grave
        
        if(pilaMovimientos.peek().getX()== casillaActual.getX()){
             if(pilaMovimientos.peek().getY()<casillaActual.getY())
                 return Mouse.DOWN;  //bajamos el ratón
             else if(pilaMovimientos.peek().getY()>casillaActual.getY())
                 return Mouse.UP;             
        }  //else
        if (pilaMovimientos.peek().getY()==casillaActual.getY()){
            if(pilaMovimientos.peek().getX()<casillaActual.getX())
                return Mouse.LEFT;
            else if (pilaMovimientos.peek().getX()>casillaActual.getX())
                return Mouse.RIGHT;
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
        this.pilaMovimientos=new Stack<>();
    }

    /**
     * @brief Método para evaluar que no nos movamos a la misma celda anterior
     * @param direction Direccion del raton
     * @param currentGrid Celda actual
     * @return True Si las casillas X e Y anterior son distintas a las actuales
     */
   /*public boolean testGrid(int direction, Grid currentGrid) {
        if (lastGrid == null) {
            return true;
        }

        int x = currentGrid.getX();
        int y = currentGrid.getY();

        switch (direction) {
            case Mouse.UP:
                y += 1;
                break;

            case Mouse.DOWN:
                y -= 1;
                break;

            case Mouse.LEFT:
                x -= 1;
                break;

            case Mouse.RIGHT:
                x += 1;
                break;
        }

        return !(lastGrid.getX() == x && lastGrid.getY() == y);

    }*/
    
    /**
     * @brief Método que devuelve si de una casilla dada, está contenida en el mapa de celdasVisitadas
     * @param casilla Casilla que se pasa para saber si ha sido visitada
     * @return True Si esa casilla ya la había visitado
     */
    public boolean visitada(Grid casilla) {
        Pair par = new Pair(casilla.getX(), casilla.getY());
        return celdasVisitadas.containsKey(par);
    }

   /**
     * @brief Método para calcular si una casilla está en una posición relativa respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición Y de la actual es mayor que la de la anterior
     */
    public boolean actualArriba(Grid actual, Grid anterior) {
        return actual.getY() > anterior.getY();
    }

    /**
     * @brief Método para calcular si una casilla está en una posición relativa respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición Y de la actual es menor que la de la anterior
     */
    public boolean actualAbajo(Grid actual, Grid anterior) {
        return actual.getY() < anterior.getY();
    }
    
    /**
     * @brief Método para calcular si una casilla está en una posición relativa respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición X de la actual es mayor que la de la anterior
     */
    public boolean actualDerecha(Grid actual, Grid anterior) {
        return actual.getX() > anterior.getX();
    }
    
    /**
     * @brief Método para calcular si una casilla está en una posición relativa respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición X de la actual es menor que la de la anterior
     */
    public boolean actualIzquierda(Grid actual, Grid anterior) {
        return actual.getX() < anterior.getX();
    }
    

}
