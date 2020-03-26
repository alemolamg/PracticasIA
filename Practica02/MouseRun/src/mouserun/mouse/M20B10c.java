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
//    private LinkedList<Integer> camino;     //camino hasta el queso
//    private TreeMap<Pair,Grid> visitadas;
//    private long numCasillasVisitadas;
    private boolean firstQueso=true;
    private HashMap<Pair<Integer, Integer>, Grid> celdasVisitadas;
    private HashMap<Pair<Integer, Integer>, Grid> mapaAuxiliar;
    
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
        
        if(!celdasVisitadas.containsKey(pairQueso)){ //aquí funciona, Pair=clave hashmap    
            if(!firstQueso)
                firstQueso=true;
            System.out.println("Escaneando......");
            return explorar(currentGrid);           
        }else{
            System.out.println("Buscando el queso");
            if(firstQueso){
                pilaMovAuxiliar.clear();
                mapaAuxiliar.clear(); 
                firstQueso=false;
            }
//            return calcCaminoGreed(currentGrid, cheese);

        int x=currentGrid.getX();
        int y=currentGrid.getY();
        
            System.out.println("Calculamos la distancia del greedy");
        if(celdasVisitadas.containsKey(generarPair(cheese.getX(), cheese.getY()))){
            System.out.println("Está en el mapa\n Calculamos la distancia del greedy");
            int distUP      = distanciaManhattan(x, y + 1, cheese.getX(), cheese.getY());
            int distDown    = distanciaManhattan(x, y - 1, cheese.getX(), cheese.getY());
            int distRight   = distanciaManhattan(x + 1, y, cheese.getX(), cheese.getY());
            int distLeft    = distanciaManhattan(x - 1, y, cheese.getX(), cheese.getY());
            
            
            if(!currentGrid.canGoUp())
                distUP = Integer.MAX_VALUE;
            
            if(!currentGrid.canGoLeft())
                distLeft = Integer.MAX_VALUE;
            
            if(!currentGrid.canGoRight())
                distRight = Integer.MAX_VALUE;
            
            if(!currentGrid.canGoDown())
                distDown = Integer.MAX_VALUE;
            
            
            if(minimo(distUP, distRight, distLeft, distDown)){
                Pair siguiente=generarPair(x,y+1);
                System.out.println("Arriba\n");
                if(!mapaAuxiliar.containsKey(siguiente)){
                    mapaAuxiliar.put(siguiente, currentGrid);
                    pilaMovAuxiliar.add(DOWN);
                    pilaMovimientos.add(DOWN);
//                    pilaMovimientos.add(new Grid(x, y-1));
                    return UP;
                } else{ System.out.println("no podemos subir");
                    distUP=Integer.MAX_VALUE; }
            }
            
            if(minimo(distRight, distUP, distLeft, distDown)){
                Pair siguiente= new Pair(x+1, y);
                System.out.println("derecha\n");
                if(!mapaAuxiliar.containsKey(siguiente)){
                    mapaAuxiliar.put(siguiente, currentGrid);
                    pilaMovAuxiliar.add(LEFT);
                    pilaMovimientos.add(LEFT);
//                    pilaMovimientos.add(new Grid(x-1, y));
                    return RIGHT;
                }else{ 
                    System.out.println("No podemos derecha");
                    distRight=Integer.MAX_VALUE; }
            }
            if(minimo(distDown, distUP, distLeft, distRight)){
                System.out.println("bajamos\n");
                    Pair pairDown=generarPair(x,y-1);
                if(!mapaAuxiliar.containsKey(pairDown)){
//                    System.out.println("Metemos pos en el mapa");
                    mapaAuxiliar.put(pairDown, currentGrid);
                    pilaMovAuxiliar.add(UP);
                    pilaMovimientos.add(UP);
                    //pilaMovimientos.add(new Grid(x, y+1));
//                    System.out.println("pilas llenas, volvemos");
                    return DOWN;
                }else{ System.out.println("No podemos Bajar");
                    distDown=Integer.MAX_VALUE; }
            }
            if(minimo(distLeft, distUP, distRight, distDown)){
                System.out.println("izquierda\n");
                if(!mapaAuxiliar.containsKey(generarPair(x - 1, y))){
                    mapaAuxiliar.put(generarPair(x - 1, y), currentGrid);
                    pilaMovAuxiliar.add(RIGHT);
                    pilaMovimientos.add(RIGHT);
                    //pilaMovimientos.add(new Grid(x+1, y));
                    return LEFT;
                }else{ System.out.println("No podemos izquierda");
                    distLeft=Integer.MAX_VALUE; }
            }
           
            if(pilaMovAuxiliar.size() > 0)//{
                //pilaMovimientos.add(pilaMovAuxiliar.peek());
                return pilaMovAuxiliar.pop();
            //}
            
        }
        return pilaMovimientos.pop() ;
        }
    }
    
    /**
     * @brief Método que se llama cuando aparece un nuevo queso
     */
    @Override
    public void newCheese() {
        pilaMovAuxiliar.clear();
        mapaAuxiliar.clear();
        
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
        
        if (currentGrid.canGoLeft()) {
                if(!celdasVisitadas.containsKey(new Pair(x - 1, y))){
                    pilaMovimientos.add(RIGHT);
                    return Mouse.LEFT;
                }
        }
        
        if (currentGrid.canGoRight()) {
            if(!celdasVisitadas.containsKey(new Pair(x + 1, y))){
                pilaMovimientos.add(LEFT);
                return Mouse.RIGHT;
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
            int distU = distanciaManhattan(x, y + 1, cheese.getX(), cheese.getY());
            int distR = distanciaManhattan(x + 1, y, cheese.getX(), cheese.getY());
            int distL = distanciaManhattan(x - 1, y, cheese.getX(), cheese.getY());
            int distD = distanciaManhattan(x, y - 1, cheese.getX(), cheese.getY());
            
            if(!currentGrid.canGoUp())
                distU = Integer.MAX_VALUE;
            
            if(!currentGrid.canGoLeft())
                distL = Integer.MAX_VALUE;
            
            if(!currentGrid.canGoRight())
                distR = Integer.MAX_VALUE;
            
            if(!currentGrid.canGoDown())
                distD = Integer.MAX_VALUE;
            
            
            if(minimo(distU, distR, distL, distD)){
                System.out.println("Subimos");
                if(!mapaAuxiliar.containsKey(generarPair(x, y + 1))){
                    mapaAuxiliar.put(generarPair(x, y + 1), currentGrid);
                    pilaMovAuxiliar.add(DOWN);
                    pilaMovimientos.add(DOWN);
//                    pilaMovimientos.add(new Grid(x, y-1));
                    return UP;
                }
            }
            if(minimo(distR, distU, distL, distD)){
                System.out.println("derecha");
                if(!mapaAuxiliar.containsKey(generarPair(x + 1, y))){
                    mapaAuxiliar.put(generarPair(x + 1, y), currentGrid);
                    pilaMovAuxiliar.add(LEFT);
                    pilaMovimientos.add(LEFT);
//                    pilaMovimientos.add(new Grid(x-1, y));
                    return RIGHT;
                }
            }
            if(minimo(distD, distU, distL, distR)){
                System.out.println("bajamos\n");
                if(!mapaAuxiliar.containsKey(generarPair(x, y - 1))){
                    System.out.println("Metemos pos en el mapa");
                    mapaAuxiliar.put(generarPair(x, y - 1), currentGrid);
                    pilaMovAuxiliar.add(UP);
                    pilaMovimientos.add(UP);
                    //pilaMovimientos.add(new Grid(x, y+1));
                    System.out.println("pilas llenas, volvemos");
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
        //System.out.println("Entramos en calMinimo");
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
