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
//    private Grid lastGrid;             // Variable para almacenar la ultima celda visitada
    private LinkedList<Integer> camino;               //camino hasta el queso
    private boolean firstQueso=true;
    private int LimiteVisitas = 5;
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
        Celda celdaActual;
        if( celdasVisitadas.containsKey(generarPair(currentGrid))){
           celdaActual = celdasVisitadas.get(generarPair(currentGrid));
        } else {
            celdaActual = new Celda(currentGrid);
            celdasVisitadas.put(generarPair(celdaActual), celdaActual);
            this.incExploredGrids();
        }
        
//        celdasVisitadas.put(generarPair(celdaActual), celdaActual);
        celdaActual.contarCasilla();
        
        return calcCaminoGreedy(celdaActual, cheese);
    }
    
    
//    /**
//     * Añade el queso al mapa que usamos para visitar las casillas
//     * @param queso     queso que buscamos
//     * @param tablero   tablero de la partida
//     */
//    private void aniadirQuesoMapa(Cheese queso, Grid tablero){
//         for(int x=0;x<tablero.getX();x++)
//                for(int y=0;y<tablero.getY();y++)
//                    celdasVisitadas.put(generarPair(queso),new Grid(queso.getX(), queso.getY()) );
//            
//             System.out.println("Inicializamos el contenedor");   
//    }
    
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

        //  Calculamos si podemos movernos
        if (celdaActual.getGridCasilla().canGoUp()) {       //Subir
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

        if (celdaActual.getGridCasilla().canGoDown()) {     //Bajar
            distDown = calcManhattan(x, y - 1, cheese.getX(), cheese.getY());
            if (celdasVisitadas.containsKey(generarPair(x, y - 1))) {
                vecesDown = celdasVisitadas.get(generarPair(x, y - 1)).getVecesCasilla();
            } else {
                vecesDown = 0;
            }
        } else {
            distDown = Integer.MAX_VALUE;
            vecesDown = Integer.MAX_VALUE;
        }

        if (celdaActual.getGridCasilla().canGoLeft()) {     //Izquierda
            distLeft = calcManhattan(x - 1, y, cheese.getX(), cheese.getY());
            if (celdasVisitadas.containsKey(generarPair(x-1, y ))) {
                vecesLeft = celdasVisitadas.get(generarPair(x - 1, y)).getVecesCasilla();
            } else {
                vecesLeft = 0;
            }
        } else {
            distLeft = Integer.MAX_VALUE;
            vecesLeft = Integer.MAX_VALUE;
        }

        if (celdaActual.getGridCasilla().canGoRight()) {    //Derecha
            distRight = calcManhattan(x + 1, y, cheese.getX(), cheese.getY());
            if (celdasVisitadas.containsKey(generarPair(x + 1, y))) {
                vecesRight = celdasVisitadas.get(generarPair(x + 1, y)).getVecesCasilla();
            } else {
                vecesRight = 0;
            }
        } else {
            distRight = Integer.MAX_VALUE;
            vecesRight = Integer.MAX_VALUE;
        }

        
        //  Calculamos el movimiento mas relevante
        if (minimo(distUP, distRight, distLeft, distDown) || vecesUp + 2 <= Integer.min(vecesDown, Integer.min(vecesLeft, vecesRight))) {    // no está terminado, tenemos que probar que no entre en bucle infinito
            if (!mapaAuxiliar.containsKey(generarPair(x, y + 1))) {
                
                if (minimo(vecesUp, vecesRight, vecesLeft, vecesDown) || vecesUp == 0) {
                    System.out.println("Numero veces en la casilla: " + vecesUp);

                    mapaAuxiliar.put(generarPair(x, y + 1), new Grid(x, y + 1));
//                pilaMovAuxiliar.add(DOWN);

                    pilaMovimientos.add(DOWN);
                    return UP;
                } else {
                    distUP = Integer.MAX_VALUE;
                }
            }else{
                
            }

        }
        

        if (minimo(distRight, distUP, distLeft, distDown) || vecesRight + 2 <= Integer.min(vecesDown, Integer.min(vecesLeft, vecesUp)) ) {
            if (minimo(vecesRight, vecesUp, vecesLeft, vecesDown) || vecesRight == 0) {
                System.out.println("Numero veces en la casilla: "+vecesRight);
//                mapaAuxiliar.put(generarPair(x + 1, y), new Grid(x + 1, y));
//                pilaMovAuxiliar.add(LEFT);
                pilaMovimientos.add(LEFT);
                return RIGHT;
            } else {
                distRight = Integer.MAX_VALUE;
            }
        }

        if (minimo(distDown, distUP, distLeft, distRight) || vecesDown +2 <= Integer.min(vecesUp, Integer.min(vecesLeft, vecesRight) )) {
            if (minimo(vecesDown, vecesRight, vecesLeft, vecesUp) || vecesDown == 0) {
                System.out.println("Numero veces en la casilla: "+vecesDown);
//                mapaAuxiliar.put(generarPair(x, y - 1), new Grid(x, y - 1));
//                pilaMovAuxiliar.add(UP);
                pilaMovimientos.add(UP);
                return DOWN;
            } else {
                distDown = Integer.MAX_VALUE;
            }
        }
        if (minimo(distLeft, distUP, distRight, distDown) || vecesLeft + 2 <= Integer.min(vecesDown, Integer.min(vecesUp, vecesRight)) ) {
           if (minimo(vecesLeft, vecesRight, vecesUp, vecesDown) || vecesLeft == 0) {
               System.out.println("Numero veces en la casilla: "+vecesLeft);
//                mapaAuxiliar.put(generarPair(x - 1, y), new Grid(x + 1, y));
//                pilaMovAuxiliar.add(RIGHT);
                pilaMovimientos.add(RIGHT);
                return LEFT;
            } else {
                distLeft = Integer.MAX_VALUE;
            }
        }
        
//        if ( !(Integer.min(distDown, distUP) < Integer.MAX_VALUE ||  Integer.min(distLeft, distRight) < Integer.MAX_VALUE) ){    
//            
//        }
//
//        if (pilaMovAuxiliar.size() >= 1) {
//            pilaMovimientos.add(pilaMovAuxiliar.peek());
//            return pilaMovAuxiliar.pop();
//        }
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
     * Calcula si el primer número es el mayor
     * @param clave valor que deseamos mínimo
     * @param a     numero con quien comparar
     * @param b     numero con quien comparar
     * @param c     numero con quien comparar
     * @return      true si "calve" es el máximo, false en caso contrario.
     */
        boolean maximo(int clave, int a, int b, int c){     
        if(clave < a){
            return false;
        }
        if(clave < b){
            return false;
        }
        if(clave < c){
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
            numVecesRecorrida = 0;   
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

