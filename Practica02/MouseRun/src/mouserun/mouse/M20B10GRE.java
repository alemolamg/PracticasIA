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
 * @author Ana Montijano Zaragoza y Alejandro Molero Gómez
 */
public class M20B10GRE extends Mouse {

    // Atributos clase
    private Grid lastGrid;             // Variable para almacenar la ultima celda visitada
    private LinkedList<Integer> camino;               //camino hasta el queso
    private boolean firstQueso=true;
//    private HashMap<Pair<Integer, Integer>, Grid> celdasVisitadas;
    private HashMap<Pair<Integer, Integer>,Celda> celdasVisitadas;
    private HashMap<Pair<Integer, Integer>, Grid> mapaAuxiliar;
    Set<String> auxiliar;                               //estructura auxiliar
    private Stack<Integer> pilaMovimientos;
    private Stack<Integer> pilaMovAuxiliar;
    
    
    /**
     * Constructor (Puedes modificar el nombre a tu gusto).
     */
    public M20B10GRE() {
        super("M20B10-GRE");
        celdasVisitadas = new HashMap<>();
        pilaMovimientos = new Stack<>();
        pilaMovAuxiliar = new Stack<>();
        mapaAuxiliar    = new HashMap<>();
        firstQueso      = true;
        auxiliar        = new LinkedHashSet<>();
        camino          = new LinkedList<>();
    }

    /**
     * Método principal para el movimiento del raton. Incluye la gestión de cuando un queso aparece o no.
     * @param currentGrid Celda actual
     * @param cheese Queso
     */
    @Override
    public int move(Grid currentGrid, Cheese cheese) {  
        Celda celdaActual = new Celda(currentGrid);
        
        if(!celdasVisitadas.containsKey(generarPair(celdaActual)) ){
            celdasVisitadas.put(generarPair(celdaActual), celdaActual);
            //añadir a la pila 
        }
        celdaActual.contarCasilla();
        
        Pair pairQueso = generarPair(cheese.getX(),cheese.getY());
        
//        if(!celdasVisitadas.containsKey(pairQueso)){  //ir a explorar
//            if(!firstQueso)
//                firstQueso=true;
////            System.out.println("Escaneando......");
//            return explorar(currentGrid);  
//            
//        }else{
            
//            System.out.println("Buscando el queso");
            if(firstQueso){
                pilaMovAuxiliar.clear();
                mapaAuxiliar.clear(); 
                firstQueso=false;
            }
            return calcCaminoGreedy(celdaActual, cheese);
//        }
    }
    
    
    /**
     * Añade el queso al mapa que usamos para visitar las casillas
     * @param queso     queso que buscamos
     * @param tablero   tablero de la partida
     */
    private void aniadirQuesoMapa(Cheese queso, Grid tablero){
         for(int x=0;x<tablero.getX();x++)
                for(int y=0;y<tablero.getY();y++)
                    celdasVisitadas.put(generarPair(queso),new Grid(queso.getX(), queso.getY()) );
            
             System.out.println("Inicializamos el contenedor");   
    }
    
    /**
     * Método que se llama cuando aparece un nuevo queso
     */
    @Override
    public void newCheese() {
        pilaMovAuxiliar.clear();
//        mapaAuxiliar.clear(); 
    }

    /**
     * Método que se llama cuando el raton pisa una bomba
     */
    @Override
    public void respawned() {
        this.pilaMovimientos=new Stack<>();
    }

    /**
     * Método que devuelve si de una casilla dada, está contenida en el mapa de celdasVisitadas
     * @param casilla Casilla que se pasa para saber si ha sido visitada
     * @return True Si esa casilla ya la había visitado
     */
    public boolean visitada(Grid casilla) {
        Pair par = new Pair(casilla.getX(), casilla.getY());
        return celdasVisitadas.containsKey(par);
    }
               
//    private int explorar(Grid currentGrid){
//        int x=currentGrid.getX();
//        int y=currentGrid.getY();
//           
//        if(!celdasVisitadas.containsKey(new Pair(x, y))){       //Vemos si la casilla actual esta en el mapa
//            this.incExploredGrids();                            //Aumentamos el numero de casillas visitadas                           //aumentamos las casillas visitadas
//            celdasVisitadas.put(new Pair(x, y), currentGrid);   //y guardamos la casilla en el mapa
//        }   
//        
//        if (currentGrid.canGoDown()) {                     
//                if(!celdasVisitadas.containsKey(new Pair(x, y - 1))){
//                    pilaMovimientos.add(UP);
//                    return Mouse.DOWN;              
//                }
//        }
//        
//        if (currentGrid.canGoRight()) {
//            if(!celdasVisitadas.containsKey(new Pair(x + 1, y))){
//                pilaMovimientos.add(LEFT);
//                return Mouse.RIGHT;
//            }
//        }
//        
//        if (currentGrid.canGoLeft()) {
//                if(!celdasVisitadas.containsKey(new Pair(x - 1, y))){
//                    pilaMovimientos.add(RIGHT);
//                    return Mouse.LEFT;
//                }
//        }
//        
//        if (currentGrid.canGoUp()) {
//            if(!celdasVisitadas.containsKey(new Pair(x , y + 1))){
//                pilaMovimientos.add(DOWN);
//                return Mouse.UP;
//            }
//        }
//        return pilaMovimientos.pop();     
//    }
    
    /**
     * Genera un pair a partir de un Grid
     * @param gridToPair
     * @return nuevo Pair generado
     */
    private Pair generarPair(Grid gridToPair){
        return new Pair(gridToPair.getX(),gridToPair.getY());       
    }
    
    /**
     * Genera un pair a partir de un Cheese
     * @param queso
     * @return nuevo Pair generado
     */
    private Pair generarPair(Cheese queso){
        return new Pair (queso.getX(),queso.getY());
    }
    
    /**
     * Genera un pair a partir de dos coordenadas
     * @param x
     * @param y
     * @return nuevo Pair generado
     */
    private Pair generarPair(int x,int y){
        return new Pair (x,y);
    }
    
    public Pair generarPair (Celda c){
        return new Pair (c.getPosX(),c.getPosY());
    }
    
    private int calcManhattan(int xi, int yi, int xq, int yq){
        return abs(xi - xq) + abs(yi - yq);
    } 
    
    /**
     * Calcula distancia con la fórmula de Manhattan
     * @param coord
     * @param queso
     * @return num distancia Manhattan
     */
    private int calcManhattan(Grid coord,Cheese queso){
        return abs(coord.getX() - queso.getX()) + abs(coord.getY() - queso.getY());
    } 
    
    
    private int calcManhattan(Grid cas1,Grid cas2){        //No creo
        return abs(cas1.getX() - cas2.getX()) + abs(cas1.getX() - cas2.getY());
    }     
    
    /**
     * Calcula el camino para llegar al queso
     * @param celdaActual   casilla en la que estamos
     * @param cheese        posición del queso
     * @return              movimiento deseado para llegar al queso.
     */
    private int calcCaminoGreedy(Celda celdaActual, Cheese cheese) {

        int x = celdaActual.getPosX();
        int y = celdaActual.getPosY();

//        if(celdasVisitadas.containsKey(generarPair(cheese.getX(), cheese.getY()))){
        int distUP, vecesUp;
        int distDown, vecesDown;
        int distLeft, vecesLeft;
        int distRight, vecesRight;

        if (celdaActual.getGridCasilla().canGoUp()) {
            distUP = calcManhattan(x, y + 1, cheese.getX(), cheese.getY());
            if (celdasVisitadas.containsKey(generarPair(x, y + 1))) {
                vecesUp = celdasVisitadas.get(generarPair(x, y + 1)).getVecesCasilla();
            } else {
                vecesUp = 0;
            }
        } else {
            distUP = Integer.MAX_VALUE;
            vecesUp = Integer.MAX_VALUE;
        }

        if (celdaActual.getGridCasilla().canGoDown()) {
            distDown = calcManhattan(x, y - 1, cheese.getX(), cheese.getY());
            if (celdasVisitadas.containsKey(generarPair(x, y + 1))) {
                vecesDown = celdasVisitadas.get(generarPair(x, y + 1)).getVecesCasilla();
            } else {
                vecesDown = 0;
            }
        } else {
            distDown = Integer.MAX_VALUE;
            vecesDown = Integer.MAX_VALUE;
        }

        if (celdaActual.getGridCasilla().canGoLeft()) {
            distLeft = calcManhattan(x - 1, y, cheese.getX(), cheese.getY());
            if (celdasVisitadas.containsKey(generarPair(x, y + 1))) {
                vecesLeft = celdasVisitadas.get(generarPair(x, y + 1)).getVecesCasilla();
            } else {
                vecesLeft = 0;
            }
        } else {
            distLeft = Integer.MAX_VALUE;
            vecesLeft = Integer.MAX_VALUE;
        }

        if (celdaActual.getGridCasilla().canGoRight()) {
            distRight = calcManhattan(x + 1, y, cheese.getX(), cheese.getY());
            if (celdasVisitadas.containsKey(generarPair(x, y + 1))) {
                vecesRight = celdasVisitadas.get(generarPair(x, y + 1)).getVecesCasilla();
            } else {
                vecesRight = 0;
            }
        } else {
            distRight = Integer.MAX_VALUE;
            vecesRight = Integer.MAX_VALUE;
        }

        if (minimo(distUP, distRight, distLeft, distDown)) {    // no está terminado, tenemos que probar que no entre en bucle infinito
            if (minimo(vecesUp, vecesRight, vecesLeft, vecesDown) || celdasVisitadas.get(generarPair(x+1, y)).getVecesCasilla() == 0) {
//                if (!mapaAuxiliar.containsKey(generarPair(x, y + 1)) ) {
                mapaAuxiliar.put(generarPair(x, y + 1), new Grid(x, y + 1));
                pilaMovAuxiliar.add(DOWN);
                pilaMovimientos.add(DOWN);
                return UP;
            } else {
                distUP = Integer.MAX_VALUE;
//                }
            }

        }

        if (minimo(distRight, distUP, distLeft, distDown)) {
            if (!mapaAuxiliar.containsKey(generarPair(x + 1, y)) && celdasVisitadas.containsKey(generarPair(celdaActual))) {
                mapaAuxiliar.put(generarPair(x + 1, y), new Grid(x + 1, y));
                pilaMovAuxiliar.add(LEFT);
                pilaMovimientos.add(LEFT);
                return RIGHT;
            } else {
                distRight = 99999;
            }
        }

        if (minimo(distDown, distUP, distLeft, distRight)) {
            if (!mapaAuxiliar.containsKey(generarPair(x, y - 1)) && celdasVisitadas.containsKey(generarPair(celdaActual))) {
                mapaAuxiliar.put(generarPair(x, y - 1), new Grid(x, y - 1));
                pilaMovAuxiliar.add(UP);
                pilaMovimientos.add(UP);
                return DOWN;
            } else {
                distDown = 99999;
            }
        }
        if (minimo(distLeft, distUP, distRight, distDown)) {
            if (!mapaAuxiliar.containsKey(generarPair(x - 1, y)) && celdasVisitadas.containsKey(generarPair(celdaActual))) {
                mapaAuxiliar.put(generarPair(x - 1, y), new Grid(x + 1, y));
                pilaMovAuxiliar.add(RIGHT);
                pilaMovimientos.add(RIGHT);
                return LEFT;
            } else {
                distLeft = 99999;
            }
        }

        if (pilaMovAuxiliar.size() >= 1) {
            pilaMovimientos.add(pilaMovAuxiliar.peek());
            return pilaMovAuxiliar.pop();
        }
//        }
        return pilaMovimientos.pop();

    }// Fin clacCaminoGreedy
    
 
    /**
     * Verifica que un número es el mismo, en este caso int clave.
     * @param clave valor que deseamos mínimo
     * @param a     numero con quien comparar
     * @param b     numero con quien comparar
     * @param c     numero con quien comparar
     * @return      true si "calve" es el mínimo, false en caso contrario.
     */
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
    
    /**
     * Clase complementaria para contar las casillas por donde se avanza
     */
    private class Celda {
        // Atributos de Casilla
        private Grid gridCasilla;
        private int posX;
        private int posY;
        private Pair<Integer, Integer> pairCasilla;
        private int numVecesRecorrida;
        
        // Funciones Casilla
        
        /**
         * Constructor por defecto.
         * @param x
         * @param y 
         */
        Celda(Grid casilla){
            gridCasilla = casilla;
            posX = casilla.getX();
            posY = casilla.getY();
            pairCasilla = new Pair(casilla.getX(),casilla.getY());
            numVecesRecorrida=0;   
        }
        
        /**
         * Función que devuelve un entero con las veces que se ha recorrido
         * la casilla actual.
         * @return numVecesRecorrida
         */
        public int getVecesCasilla (){
            return numVecesRecorrida; 
        }
        
        /**
         * Cuenta +1 a la casilla actual
         */
        private void contarCasilla(){
            numVecesRecorrida++;
        }

        /**
         * @return the posX
         */
        public int getPosX() {
            return posX;
        }

        /**
         * @return the posY
         */
        public int getPosY() {
            return posY;
        }

        /**
         * @return the pairCasilla
         */
        public Pair<Integer, Integer> getPairCasilla() {
            return pairCasilla;
        }

        /**
         * @return the gridCasilla
         */
        public Grid getGridCasilla() {
            return gridCasilla;
        }
        
        
    }
    
    
}

