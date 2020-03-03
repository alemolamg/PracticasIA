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
        
        int solSimple=moveSimple(currentGrid, cheese);
        
        return solSimple;
    }
    
    
    public int moveSimple(Grid currentGrid, Cheese cheese){
        
        int x=currentGrid.getX();
        int y=currentGrid.getY();
        
        Pair <Integer,Integer> pair =new Pair (x,y);
        

        if(cheese.getY()>y){        
            if(currentGrid.canGoUp())   //verificar que podemos subir
                pilaMov.add(pair);      //implementar bien
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
    
    public void comprobarCasillas(Grid casillaActual){
        
        int x=casillaActual.getX();
        int y=casillaActual.getY();
        
         
    }
    
}
