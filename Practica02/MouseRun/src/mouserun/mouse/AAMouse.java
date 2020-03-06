//package mouserun.mouse;
//
//import mouserun.game.Mouse;
//import mouserun.game.Grid;
//import mouserun.game.Cheese;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Random;
//import java.util.Stack;
//import java.util.Vector;
//import javafx.util.Pair;
//
////import org.apache.commons.larg3.tuple.Pair;
////import org.javatuples.Pair;
//
//
///**
// * @author Alemol
// */
//public class AAMouse extends Mouse{
//     
//    private HashMap<Pair<Integer, Integer>, Grid> mapa;
//    private Stack<Integer> pilaMov;
//    private long numCasillasVisitadas;
//    
//    //  Funciones   //
//    
//    public AAMouse(){
//        super("AAMouse");
//        numCasillasVisitadas=0;
//    }
//    
//    public void newCheese(){
//        
//    }
//    
//    public void respawned() {
//    
//    }
//    
//    
//    /**
//     * @brief Función que procede a mover el objeto
//     * @param currentGrid   casilla actual donde está el ratón
//     * @param cheese        queso que tiene que buscar
//     * @return 
//     */
//    public int move (Grid currentGrid,Cheese cheese){
//        
//        int x=currentGrid.getX();
//        int y=currentGrid.getY();
//           
//        
//        if(!mapa.containsKey(new Pair(x, y))){      //Vemos si la casilla actual esta en el mapa
//            numCasillasVisitadas++;                 //aumentamos las casillas visitadas
//            mapa.put(new Pair(x, y), currentGrid);  //y guardamos la casilla en el mapa
//        }   
//        
//        
//        if (currentGrid.canGoDown()) {          //probamos si puede ir hacia arriba
//                Pair p = new Pair(x, y - 1);    //creamos un pair de la casilla superior
//                if(!mapa.containsKey(p)){       //si no esta contenida en el mapa
////                    Grid nuevaCasilla= new Grid(x,y-1);
//                    pilaMov.add(Mouse.UP);      //metemos en movimiento en la pila
//                    return Mouse.DOWN;          //devolvemos el movimiento
//                }
//        }
//        
//        
//        if (currentGrid.canGoLeft()) {
//                Pair p = new Pair(x - 1, y);
//                if(!mapa.containsKey(p)){
//                    pilaMov.add(Mouse.RIGHT);
//                    return Mouse.LEFT;
//                }
//        }
//        
//        if (currentGrid.canGoRight()) {
//            Pair p = new Pair(x + 1, y);
//            if(!mapa.containsKey(p)){
//                pilaMov.add(Mouse.LEFT);
//                return Mouse.RIGHT;
//            }
//        }
//        
//        if (currentGrid.canGoUp()) {
//            Pair p = new Pair(x , y + 1);
//            if(!mapa.containsKey(p)){
//                pilaMov.add(Mouse.LEFT);
//                return Mouse.UP;
//            }
//        }
//        return pilaMov.pop();
//    }
//    
//    
//    
//    
//}
//
///**
// * Clase que almacena la informacion de la celda donde estamos parados.
// *
// class celda{
//     
//    private Grid gribCelda;
//    private Vector<Integer> celdasAdy;
//
//    public celda(Grid actualGrid){
//        celdasAdy=new Vector<Integer> ();
//        gribCelda=actualGrid;
//    }
//    
//}*/
//
//
