package mouserun.mouse;

import mouserun.game.Mouse;
import mouserun.game.Grid;
import mouserun.game.Cheese;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;
import javafx.util.Pair;

//import org.apache.commons.larg3.tuple.Pair;
//import org.javatuples.Pair;


/**
 * @author Alemol
 */
public class AAMouse extends Mouse{
     
    private HashMap<Pair<Integer, Integer>, Grid> mapa;
    private Stack<Grid> pilaMov;
    private long numCasillasVisitadas;
    
    //  Funciones   //
    
    public AAMouse(){
        super("AAMouse");
        numCasillasVisitadas=0;
    }
    
    public void newCheese(){
        
    }
    
    public void respawned() {
    
    }
    
    
    /**
     * @brief Función que procede a mover el objeto
     * @param currentGrid   casilla actual donde está el ratón
     * @param cheese        queso que tiene que buscar
     * @return 
     */
    public int move (Grid currentGrid,Cheese cheese){
        
        int x=currentGrid.getX();
        int y=currentGrid.getY();
           
         Pair<Integer, Integer> pair = new Pair(x, y);//Hacemos un pair de la casilla actual
        
        if(!mapa.containsKey(pair)){//Vemos si la casilla actual esta en el mapa
            numCasillasVisitadas++;//aumentamos las casillas visitadas
            mapa.put(pair, currentGrid);//y guardamos la casilla en el mapa
        }   
        
        
        if (currentGrid.canGoDown()) {//probamos si puede ir hacia arriba
                Pair p = new Pair(x, y - 1);//creamos un pair de la casilla superior
                if(!mapa.containsKey(p)){//si no esta contenida en el mapa
//                    Grid nuevaCasilla= new Grid(x,y-1);
                    pilaMov.add(new Grid(x,y - 1));//metemos en movimiento en la pila
                    return Mouse.DOWN;//devolvemos el movimiento
                }
        }
        
        if (currentGrid.canGoLeft()) {
                Pair p = new Pair(x - 1, y);
                if(!mapa.containsKey(p)){
                    pilaMov.add(new Grid(x-1,y));
                    return Mouse.LEFT;
                }
        }
        
        if (currentGrid.canGoRight()) {
            Pair p = new Pair(x + 1, y);
            if(!mapa.containsKey(p)){
                pilaMov.add(new Grid(x+1,y));
                return Mouse.RIGHT;
            }
        }
        
        if (currentGrid.canGoUp()) {
            Pair p = new Pair(x , y + 1);
            if(!mapa.containsKey(p)){
                pilaMov.add(new Grid(x,y+1));
                return Mouse.UP;
            }
        }
        return 0;
        
        
         //int solSimple=moveSimple(currentGrid, cheese);
        
         //Si el contenedor esta vacio lo inicializamos con las celdas vacias.
    }
    
    
    public int moveSimple(Grid currentGrid, Cheese cheese){
        
        int x=currentGrid.getX();
        int y=currentGrid.getY();
        
        Pair <Integer,Integer> pair =new Pair (x,y);
        
        if(!mapa.containsKey(pair)){//Vemos si la casilla actual esta en el mapa
            numCasillasVisitadas++;//aumentamos las casillas visitadas
            mapa.put(pair, currentGrid);//y guardamos la casilla en el mapa
        } 
        System.out.println("Comenzamos a movernos");
        
        if(cheese.getY()>y){        
            if(currentGrid.canGoUp())   //verificar que podemos subir
                System.out.println("podemos subir");
//                pilaMov.add(pair);      //implementar bien
                return Mouse.UP;
        }

        if(cheese.getY()<y){        
            if(currentGrid.canGoDown())   //verificar que podemos subir
                return Mouse.DOWN;
        }
        
        if(cheese.getX()>x){        
            if(currentGrid.canGoRight())   //verificar que podemos subir
                return Mouse.RIGHT;
        }
        
        if(cheese.getX()>x){        
            if(currentGrid.canGoLeft())   //verificar que podemos subir
                return Mouse.LEFT;
        }
        
        return 0;
        
    }
        
}

/**
 * Clase que almacena la informacion de la celda donde estamos parados.
 */
 class celda1{

    int x;
    int y;
    int veces;
    double distancia;

    
    /**
     * Clase para crear las posiciones del tablero del juego.
     * @param posX Indica la posicion X en el tablero
     * @param posY Indica la posicion Y en el tablero
     * @param posCofreX Indica la posicion X del cofre del tesoro
     * @param posCofreY Indica la posicion Y del cofre del tesoro
     */
    public celda1(int posX,int posY,int posCofreX,int posCofreY)
    {
        this.x=posX;
        this.y=posY;
        veces=0;
        
        //Guardamos la distancia desde esta celda al cofre del tesoro.
        distancia=Math.sqrt(Math.pow(posCofreX-posX, 2) + Math.pow(posCofreY-posY, 2));
        
    }
}