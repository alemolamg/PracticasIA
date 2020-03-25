package mouserun.mouse;

import java.util.TreeMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import javafx.util.Pair;
import static java.lang.Math.abs;
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
 * @author Ana Montijano Zaragoza y Alejandro Molero Gómez
 */
public class M20B10c extends Mouse {

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
    private HashMap<Pair<Integer, Integer>, Grid> mapaAuxiliar;
    
    /**
     * Pila para almacenar el camino recorrido.
     */
    private Stack<Integer> pilaMovimientos;
    private Stack<Integer> pilaMovAuxiliar;
    
    
    /**
     * Constructor (Puedes modificar el nombre a tu gusto).
     */
    public M20B10c() {
        super("M20B10c");
        celdasVisitadas = new HashMap<>();
        pilaMovimientos = new Stack<>();
        pilaMovAuxiliar = new Stack<>();
        
    }

    /**
     * @brief Método principal para el movimiento del raton. Incluye la gestión de cuando un queso aparece o no.
     * @param currentGrid Celda actual
     * @param cheese Queso
     */
    @Override
    public int move(Grid currentGrid, Cheese cheese) {  
        //int salida =0;      // TODO: borrame
        
        Pair pairQueso= generarPair(cheese.getX(),cheese.getY());
        
        if(!celdasVisitadas.containsKey(pairQueso)){ //aquí funciona, Pair=clave hashmap    
            System.out.println("Escaneando......");
            return explorar(currentGrid);           
        }else{
            return calcCaminoGreed(currentGrid, cheese);
        }
    }
    
    /**
     * 
     * @param celdaActual celda donde se encuentra el ratón
     * @return movimiento de regreso del raton
     */
//    int posRegreso(Grid celdaActual){   
//        
////        if(pilaMovimientos.peek().equals(casillaActual))
////            return -1;  //Es la misma casilla, fallo grave
//        
//        if(pilaMovimientos.peek().getX()== celdaActual.getX()){
//             if(pilaMovimientos.peek().getY()<celdaActual.getY())
//                 return Mouse.DOWN;  //bajamos el ratón
//             else if(pilaMovimientos.peek().getY()>celdaActual.getY())
//                 return Mouse.UP;             
//        }  //else
//        if (pilaMovimientos.peek().getY()==celdaActual.getY()){
//            if(pilaMovimientos.peek().getX()<celdaActual.getX())
//                return Mouse.LEFT;
//            else if (pilaMovimientos.peek().getX()>celdaActual.getX())
//                return Mouse.RIGHT;
//        }
//        
//        return -2;  //fallo las dos coordenadas son diferentes, servirá para implementar bombas
//    }

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
    
    
    private int explorar(Grid currentGrid){
        int x=currentGrid.getX();
        int y=currentGrid.getY();
           
        if(!celdasVisitadas.containsKey(new Pair(x, y))){      //Vemos si la casilla actual esta en el mapa
            this.incExploredGrids();                            //Aumentamos el numero de casillas visitadas                           //aumentamos las casillas visitadas
            celdasVisitadas.put(new Pair(x, y), currentGrid);  //y guardamos la casilla en el mapa
        }   
        
        
        if (currentGrid.canGoDown()) {                     
                if(!celdasVisitadas.containsKey(new Pair(x, y - 1))){
                    pilaMovimientos.push(UP);
                    return Mouse.DOWN;              
                }
        }
        
        if (currentGrid.canGoLeft()) {
                if(!celdasVisitadas.containsKey(new Pair(x - 1, y))){
                    pilaMovimientos.push(RIGHT);
                    return Mouse.LEFT;
                }
        }
        
        if (currentGrid.canGoRight()) {
            if(!celdasVisitadas.containsKey(new Pair(x + 1, y))){
                pilaMovimientos.push(LEFT);
                return Mouse.RIGHT;
            }
        }
        
        if (currentGrid.canGoUp()) {
            if(!celdasVisitadas.containsKey(new Pair(x , y + 1))){
                pilaMovimientos.push(DOWN);
                return Mouse.UP;
            }
        }
        return pilaMovimientos.pop();     
        
    }
    
    
    private Pair generarPair(Grid gridToPair){
        return new Pair(gridToPair.getX(),gridToPair.getY());       
    }
    
    private Pair generarPair(int x,int y){
        return new Pair (x,y);
    }
    
    private int distanciaManhattan(int xi, int yi, int xq, int yq){     //NO creo
        return abs(xi - xq) + abs(yi - yq);
    } 
    
    private int distanciaManhattan(Grid coord,Cheese queso){
        return abs(coord.getX() - queso.getX()) + abs(coord.getY() - queso.getY());
    } 
    
    private int distanciaManhattan(Grid cas1,Grid cas2){        //No creo
        return abs(cas1.getX() - cas2.getX()) + abs(cas1.getX() - cas2.getY());
    } 
    
    
    
    private int calcCaminoGreed (Grid currentGrid, Cheese cheese){
        
        int x=currentGrid.getX();
        int y=currentGrid.getY();
        
        if(celdasVisitadas.containsKey(generarPair(cheese.getX(), cheese.getY()))){
            int distR = distanciaManhattan(x + 1, y, cheese.getX(), cheese.getY());
            int distU = distanciaManhattan(x, y + 1, cheese.getX(), cheese.getY());
            int distL = distanciaManhattan(x - 1, y, cheese.getX(), cheese.getY());
            int distD = distanciaManhattan(x, y - 1, cheese.getX(), cheese.getY());
            
            if(currentGrid.canGoUp())
                distU = Integer.MAX_VALUE;
            
            if(currentGrid.canGoLeft())
                distL = Integer.MAX_VALUE;
            
            if(currentGrid.canGoRight())
                distR = Integer.MAX_VALUE;
            
            if(currentGrid.canGoDown())
                distD = Integer.MAX_VALUE;
            
            
            if(minimo(distU, distR, distL, distD)){
                if(!mapaAuxiliar.containsKey(generarPair(x, y + 1))){
                    mapaAuxiliar.put(generarPair(x, y + 1), currentGrid);
                    pilaMovAuxiliar.add(DOWN);
                    pilaMovimientos.add(DOWN);
//                    pilaMovimientos.add(new Grid(x, y-1));
                    return UP;
                }
            }
            if(minimo(distR, distU, distL, distD)){
                if(!mapaAuxiliar.containsKey(generarPair(x + 1, y))){
                    mapaAuxiliar.put(generarPair(x + 1, y), currentGrid);
                    pilaMovAuxiliar.add(LEFT);
                    pilaMovimientos.add(LEFT);
//                    pilaMovimientos.add(new Grid(x-1, y));
                    return RIGHT;
                }
            }
            if(minimo(distD, distU, distL, distR)){
                if(!mapaAuxiliar.containsKey(generarPair(x, y - 1))){
                    mapaAuxiliar.put(generarPair(x, y - 1), currentGrid);
                    pilaMovAuxiliar.add(UP);
                    pilaMovimientos.add(UP);
                    //pilaMovimientos.add(new Grid(x, y+1));
                    return DOWN;
                }
            }
            if(minimo(distL, distU, distR, distD)){
                if(!mapaAuxiliar.containsKey(generarPair(x - 1, y))){
                    mapaAuxiliar.put(generarPair(x - 1, y), currentGrid);
                    pilaMovAuxiliar.add(RIGHT);
                    pilaMovimientos.add(RIGHT);
                    //pilaMovimientos.add(new Grid(x+1, y));
                    return LEFT;
                }
            }
           
            
            if(pilaMovAuxiliar.size() > 0){
                pilaMovimientos.add(pilaMovAuxiliar.peek());
                return pilaMovAuxiliar.pop();
            }
            
        }
        return pilaMovimientos.pop() ;
    }
    
    
    boolean minimo(int minimoAComprobar, int a, int b, int c){
        if(minimoAComprobar > a){
            return false;
        }
        if(minimoAComprobar > b){
            return false;
        }
        if(minimoAComprobar > c){
            return false;
        }
        
        return true;
    }
}
