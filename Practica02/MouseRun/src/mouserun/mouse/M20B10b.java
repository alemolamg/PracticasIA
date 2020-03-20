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
    private LinkedList camino;//camino hasta el queso
    private TreeMap<Integer,Grid> visitadas;
    
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
        
//        if(HashMap<pairQueso,gridQueso>){
            


//        }else{
            salida = escaner(currentGrid, cheese);
//        }
        
        
        return salida;
    }
    
    /**
     * 
     * @param celdaActual celda donde se encuentra el ratón
     * @return movimiento de regreso del raton
     */
    int posRegreso(Grid celdaActual){   //Falta un caso por tener en cuenta
        
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
    
    
    private int escaner(Grid currentGrid, Cheese cheese){
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
        System.out.println("El movimiento es: "+ salida);
        pilaMovimientos.pop();      //saca el ultimo grip
        
        return salida;                                // idea Mental
        
////        return posRegreso(currentGrid);
        
        
    }
    
    public boolean planificarProfundidad(int x,int y, Cheese cheese) {
        int cX=x;
        int cY=y;
        
        if(cX==cheese.getX()&&cY==cheese.getY()){
            return true;
        }
        Grid g=new Grid(x,y);
//        Casilla c=new Casilla(g);
        Grid gUP=new Grid(x,y+1);
//        Casilla cUP=new Casilla(gUP);
        Grid gDown=new Grid(x,y-1);
//        Casilla cDOWN=new Casilla(gD);
        Grid gLeft=new Grid(x-1,y);
//        Casilla cLEFT=new Casilla(gL);
        Grid gRight=new Grid(x+1,y);
//        Casilla cRIGHT=new Casilla(gR);
        String x1 = String.valueOf(x);
        String y1 = String.valueOf(y);

        String actual = x1 + y1;
        String sup = x1 + String.valueOf(cY + 1);
        String inf = x1 +  String.valueOf(cY - 1);
        String izq = String.valueOf(cX - 1) + y1;
        String der = String.valueOf(cX + 1) + y1;
        
        marcados.add(actual);
        if (visitadas.containsKey(gUP)&& !marcados.contains(sup)) {
            if(visitadas.get(g).canGoUp()){
            camino.addLast(UP);
            if(planificarProfundidad(x,cY+1,cheese)){
                return true;
            }
            camino.removeLast();
            }
        }
        if (visitadas.containsKey(gDown)&& !marcados.contains(inf)) {
            if(visitadas.get(g).canGoDown()){
            camino.addLast(DOWN);
            if(planificarProfundidad(x,cY-1,cheese)){
                return true;
            }
            camino.removeLast();
            }
        }
        if (visitadas.containsKey(gLeft)&& !marcados.contains(izq)) {
            if(visitadas.get(g).canGoLeft()){
            camino.addLast(LEFT);
            if(planificarProfundidad(cX-1,y,cheese)){
                return true;
            }
            camino.removeLast();
            }
        }
        if (visitadas.containsKey(gRight) && !marcados.contains(der)) {
            if(visitadas.get(g).canGoRight()){
            camino.addLast(RIGHT);
            if(planificarProfundidad(cX+1,y,cheese)){
                return true;
            }
            camino.removeLast();
            }
        }
       return false;
    }
    
    
}
