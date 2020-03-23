package mouserun.mouse;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import javafx.util.Pair;
import mouserun.game.Mouse;
import mouserun.game.Grid;
import mouserun.game.Cheese;
import static mouserun.game.Mouse.DOWN;
import static mouserun.game.Mouse.LEFT;
import static mouserun.game.Mouse.RIGHT;
import static mouserun.game.Mouse.UP;

/**
 * Clase que contiene el esqueleto del raton base para las prácticas de Inteligencia Artificial del curso 2019-20.
 * 
 * @author Cristóbal José Carmona (ccarmona@ujaen.es) y Ángel Miguel García Vico (agvico@ujaen.es)
 * @author Ana Montijano Zaragoza y Alejandro Molero Gómez
 */
public class M20B10b extends Mouse {

    /**
     * Variable para almacenar la ultima celda visitada
     */
    private Grid lastGrid;
    
    Set<String> marcados;//estructura auxiliar para crear el camino
    private LinkedList<Integer> camino;//camino hasta el queso
    private TreeMap<Pair,Grid> visitadas;
    
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
    public M20B10b() {
        super("M20B10b");
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
        int salida;
        
        Pair pairQueso= new Pair (cheese.getX(),cheese.getY());
        Grid gridQueso= new Grid(cheese.getX(),cheese.getY());
        
        if(!celdasVisitadas.containsKey(pairQueso)){ //aquí funciona, Pair=clave hashmap    
            System.out.println("Escaneando......");
            return salida = explorar(currentGrid, cheese);           
        }else{
            
            System.out.println("Conozco el queso, voy a por él");
//            if (!camino.isEmpty()) {
//                System.out.println("camino lleno, voy a por queso");
//             return (int) camino.removeFirst();
//            }
            
        //marcados.clear();
        lastGrid = currentGrid;
        System.out.println("Planifico...");
        if(planificarProfundidad(currentGrid,cheese)){      //Arreglar planificarProfuncidad()
            System.out.println("Camino encontrado");
        }       
            return (int)camino.removeFirst();    
//        return 0;
        }
    }
    
    /**
     * 
     * @param celdaActual celda donde se encuentra el ratón
     * @return movimiento de regreso del raton
     */
    int posRegreso(Grid celdaActual){   
        
//        if(pilaMovimientos.peek().equals(casillaActual))
//            return -1;  //Es la misma casilla, fallo grave
        
        if(pilaMovimientos.peek().getX()== celdaActual.getX()){
             if(pilaMovimientos.peek().getY()<celdaActual.getY())
                 return Mouse.DOWN;  //bajamos el ratón
             else if(pilaMovimientos.peek().getY()>celdaActual.getY())
                 return Mouse.UP;             
        }  //else
        if (pilaMovimientos.peek().getY()==celdaActual.getY()){
            if(pilaMovimientos.peek().getX()<celdaActual.getX())
                return Mouse.LEFT;
            else if (pilaMovimientos.peek().getX()>celdaActual.getX())
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
    
    
    private int explorar(Grid currentGrid, Cheese cheese){
        int x=currentGrid.getX();
        int y=currentGrid.getY();
           
        if(!celdasVisitadas.containsKey(new Pair(x, y))){      //Vemos si la casilla actual esta en el mapa
            this.incExploredGrids();                            //Aumentamos el numero de casillas visitadas                           //aumentamos las casillas visitadas
            celdasVisitadas.put(new Pair(x, y), currentGrid);  //y guardamos la casilla en el mapa
        }   
        
        
        if (currentGrid.canGoDown()) {                     
                if(!celdasVisitadas.containsKey(new Pair(x, y - 1))){
                    pilaMovimientos.push(currentGrid);
                    return Mouse.DOWN;              
                }
        }
        
        if (currentGrid.canGoLeft()) {
                if(!celdasVisitadas.containsKey(new Pair(x - 1, y))){
                    pilaMovimientos.push(currentGrid);
                    return Mouse.LEFT;
                }
        }
        
        if (currentGrid.canGoRight()) {
            if(!celdasVisitadas.containsKey(new Pair(x + 1, y))){
                pilaMovimientos.push(currentGrid);
                return Mouse.RIGHT;
            }
        }
        
        if (currentGrid.canGoUp()) {
            if(!celdasVisitadas.containsKey(new Pair(x , y + 1))){
                pilaMovimientos.push(currentGrid);
                return Mouse.UP;
            }
        }
        
        int salida=posRegreso(currentGrid);
        //System.out.println("El movimiento es: "+ salida);
        pilaMovimientos.pop();      //saca el ultimo grip
        
        return salida;                                // idea Mental
        
////        return posRegreso(currentGrid);
        
        
    }
    
    public boolean planificarProfundidad(Grid actual, Cheese cheese) {
                
        if(actual.getX() == cheese.getX() && actual.getY() == cheese.getY()){   //Ver si ha terminado el camino
            System.out.println("coinciden Queso y Casilla");
            return true;
        }
        System.out.println("Entramos Planificar Profundidad");        
        
        Grid g=new Grid(actual.getX(),actual.getY());
        Grid gUP=new Grid(actual.getX(),actual.getY()+1);
        Grid gDown=new Grid(actual.getX(),actual.getY()-1);
        Grid gLeft=new Grid(actual.getX()-1,actual.getY());
        Grid gRight=new Grid(actual.getX()+1,actual.getY());

//        marcados.add(posActual);
        //if (celdasVisitadas.containsKey(gUP) && !marcados.contains(sup)) {
        System.out.println("Vamos a calcular el camino correcto");
        
        if (celdasVisitadas.containsKey(generarPair(gUP))) {    //el pair es la clave
            System.out.println("gUP - existe en celdasVisitadas ");
            if (celdasVisitadas.get(g).canGoUp()) {     //
                System.out.println("Podemos movernos hacia arriba");
                camino.addLast(UP);
                System.out.println("Encontrado camino UP");
                if (planificarProfundidad(gUP, cheese)) {
                    return true;
                }
                camino.removeLast();
            }
        }
        
        //if (celdasVisitadas.containsKey(gDown) && !marcados.contains(inf)) {
        if (celdasVisitadas.containsKey(generarPair(gDown))) {
            System.out.println("gDOWN - existe en celdasVisitadas ");
            if (celdasVisitadas.get(g).canGoDown()) {
                System.out.println("Podemos movernos hacia ABAJO");
                camino.addLast(DOWN);
                System.out.println("Encontrado camino DOWN");
                if (planificarProfundidad(gDown, cheese)) {
                    return true;
                }
                camino.removeLast();
            }
        }
        
        
//        celdasVisitadas.containsKey(new Pair(actual.getX(),actual.getX()+1));
        
//        if (celdasVisitadas.containsKey(gLeft) && !marcados.contains(izq)) {
        if (celdasVisitadas.containsKey(generarPair(gLeft)) ) {
            System.out.println("gLEFT - existe en celdasVisitadas ");
            if (celdasVisitadas.get(g).canGoLeft()) {
                System.out.println("Podemos movernos hacia IZQUIERDA");
                camino.addLast(LEFT);
                System.out.println("Encontrado camino LEFT");
                if (planificarProfundidad(gLeft, cheese)) {
                    return true;
                }
                camino.removeLast();
            }
        }
        //if (celdasVisitadas.containsKey(gRight) && !marcados.contains(der)) {
        if (celdasVisitadas.containsKey(generarPair(gRight))) {
            System.out.println("gRIGHT - existe en celdasVisitadas ");
            if (celdasVisitadas.get(g).canGoRight()) {
                System.out.println("Podemos movernos hacia DERECHA");
                camino.addLast(RIGHT);
                System.out.println("Encontrado camino RIGHT");
                if (planificarProfundidad(gRight, cheese)) {
                    return true;
                }
                camino.removeLast();
            }
        }
        System.out.println("Falla, sale FALSE");
       return false;
    }
    
    
    private Pair generarPair(Grid gridToPair){
        Pair sol= new Pair (gridToPair.getX(),gridToPair.getY());
        return sol;       
    }
    
}
