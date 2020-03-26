package mouserun.mouse;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;
import javafx.util.Pair;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Vector;
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
    private LinkedList<Integer> camino;               //camino hasta el queso
//    private TreeMap<Pair,Grid> visitadas;
//    private long numCasillasVisitadas;
    private boolean firstQueso=true;
    private HashMap<Pair<Integer, Integer>, Grid> celdasVisitadas;
    private HashMap<Pair<Integer, Integer>, Grid> mapaAuxiliar;
    Set<String> auxiliar;                               //estructura auxiliar
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
        mapaAuxiliar    = new HashMap<>();
        firstQueso      = true;
        auxiliar        = new LinkedHashSet<>();
        camino          = new LinkedList<>();
    }

    /**
     * @brief Método principal para el movimiento del raton. Incluye la gestión de cuando un queso aparece o no.
     * @param currentGrid Celda actual
     * @param cheese Queso
     */
    @Override
    public int move(Grid currentGrid, Cheese cheese) {  
        //int salida =0;      // TODO: borrame
        if(!celdasVisitadas.containsKey(generarPair(currentGrid)) ){
            celdasVisitadas.put(generarPair(currentGrid), currentGrid);
        }
        
        Pair pairQueso= generarPair(cheese.getX(),cheese.getY());
        
        if(!celdasVisitadas.containsKey(pairQueso)){  
            if(!firstQueso)
                firstQueso=true;
//            System.out.println("Escaneando......");
            return explorar(currentGrid);  
            
        }else{
            
//            System.out.println("Buscando el queso");
            if(firstQueso){
                pilaMovAuxiliar.clear();
//                mapaAuxiliar.clear(); 
                firstQueso=false;
            }
            return calcCaminoGreedy(currentGrid, cheese);
        }
    }
    
    /**
     * @brief Método que se llama cuando aparece un nuevo queso
     */
    @Override
    public void newCheese() {
        pilaMovAuxiliar.clear();
//        mapaAuxiliar.clear(); 
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
               
    private int explorar(Grid currentGrid){
        int x=currentGrid.getX();
        int y=currentGrid.getY();
           
        if(!celdasVisitadas.containsKey(new Pair(x, y))){       //Vemos si la casilla actual esta en el mapa
            this.incExploredGrids();                            //Aumentamos el numero de casillas visitadas                           //aumentamos las casillas visitadas
            celdasVisitadas.put(new Pair(x, y), currentGrid);   //y guardamos la casilla en el mapa
        }   
        
        if (currentGrid.canGoDown()) {                     
                if(!celdasVisitadas.containsKey(new Pair(x, y - 1))){
                    pilaMovimientos.add(UP);
                    return Mouse.DOWN;              
                }
        }
        
        if (currentGrid.canGoRight()) {
            if(!celdasVisitadas.containsKey(new Pair(x + 1, y))){
                pilaMovimientos.add(LEFT);
                return Mouse.RIGHT;
            }
        }
        
        if (currentGrid.canGoLeft()) {
                if(!celdasVisitadas.containsKey(new Pair(x - 1, y))){
                    pilaMovimientos.add(RIGHT);
                    return Mouse.LEFT;
                }
        }
        
        if (currentGrid.canGoUp()) {
            if(!celdasVisitadas.containsKey(new Pair(x , y + 1))){
                pilaMovimientos.add(DOWN);
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
    
    private int calcManhattan(int xi, int yi, int xq, int yq){
        return abs(xi - xq) + abs(yi - yq);
    } 
    
    private int calcManhattan(Grid coord,Cheese queso){
        return abs(coord.getX() - queso.getX()) + abs(coord.getY() - queso.getY());
    } 
    
    private int calcManhattan(Grid cas1,Grid cas2){        //No creo
        return abs(cas1.getX() - cas2.getX()) + abs(cas1.getX() - cas2.getY());
    }     
    
    private int calcCaminoGreedy (Grid currentGrid, Cheese cheese){
        
        int x=currentGrid.getX();
        int y=currentGrid.getY();
        
        if(celdasVisitadas.containsKey(generarPair(cheese.getX(), cheese.getY()))){
            int distUP;
            int distDown;
            int distLeft;
            int distRight;
            
            if(currentGrid.canGoUp()){
                distUP = calcManhattan(x, y + 1, cheese.getX(), cheese.getY());
            }else
                distUP = 99999;
                
            if(currentGrid.canGoDown())
                distDown = calcManhattan(x, y - 1, cheese.getX(), cheese.getY());
            else
                distDown = 99999;
            
            if(currentGrid.canGoLeft())
                distLeft =calcManhattan(x - 1, y, cheese.getX(), cheese.getY());
            else 
                distLeft = 99999;
            
            if(currentGrid.canGoRight())
                distRight = calcManhattan(x + 1, y, cheese.getX(), cheese.getY());
            else
                distRight = 99999;
                
            
            if(minimo(distUP, distRight, distLeft, distDown)){
                if(!mapaAuxiliar.containsKey(generarPair(x,y+1)) && celdasVisitadas.containsKey(generarPair(currentGrid)) ){
                    mapaAuxiliar.put(generarPair(x,y+1), new Grid(x, y+1));
                    pilaMovAuxiliar.add(DOWN);
                    pilaMovimientos.add(DOWN);
                    return UP;
                } else{ 
                    distUP=99999; 
                }
            }
            
            if(minimo(distRight, distUP, distLeft, distDown)){
                if(!mapaAuxiliar.containsKey(generarPair(x+1, y)) && celdasVisitadas.containsKey(generarPair(currentGrid)) ){
                    mapaAuxiliar.put(generarPair(x+1, y), new Grid(x+1, y));
                    pilaMovAuxiliar.add(LEFT);
                    pilaMovimientos.add(LEFT);
                    return RIGHT;
                }else{ 
                    distRight=99999; }
            }
            if(minimo(distDown, distUP, distLeft, distRight)){
                if(!mapaAuxiliar.containsKey(generarPair(x,y-1)) && celdasVisitadas.containsKey(generarPair(currentGrid)) ){
                    mapaAuxiliar.put(generarPair(x,y-1), new Grid(x, y-1));
                    pilaMovAuxiliar.add(UP);
                    pilaMovimientos.add(UP);
                    return DOWN;
                }else{
                    distDown=99999; 
                }
            }
            if(minimo(distLeft, distUP, distRight, distDown)){
                if(!mapaAuxiliar.containsKey(generarPair(x - 1, y)) && celdasVisitadas.containsKey(generarPair(currentGrid)) ){
                    mapaAuxiliar.put(generarPair(x - 1, y), new Grid(x+1, y));
                    pilaMovAuxiliar.add(RIGHT);
                    pilaMovimientos.add(RIGHT);
                    return LEFT;
                }else{
                    distLeft=Integer.MAX_VALUE; 
                }
            }
            if(pilaMovAuxiliar.size() >=1){
                pilaMovimientos.add(pilaMovAuxiliar.peek());
                return pilaMovAuxiliar.pop();
            } 
        }
        return pilaMovimientos.pop() ;
        
    }
    
    
    boolean minimo(int clave, int a, int b, int c){     
        if(clave > a){
            return false;
        }
        if(clave > b){
            return false;
        }
        if(clave > c){
            return false;
        }
        return true;
    }    
}

