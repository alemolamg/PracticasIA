/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserun.mouse;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import mouserun.game.Cheese;
import mouserun.game.Grid;
import mouserun.game.Mouse;
import static mouserun.game.Mouse.DOWN;
import static mouserun.game.Mouse.LEFT;
import static mouserun.game.Mouse.RIGHT;
import static mouserun.game.Mouse.UP;

/**
 *
 * @author Usuario
 */
public class M20B10DFS extends Mouse {

    private HashMap<Integer,Casilla> exploredGrids;
    private int i;//indice del queso por donde voy
    private HashMap<Integer, Casilla> visitadas;//mapa de casillas visitadas
    private int ultimo = LEFT;//ultimo movimiento realizado(inverso)
    private Grid lastGrid;//ultima casilla visitada
    Set<String> marcados;//estructura auxiliar para crear el camino
    private LinkedList camino;//camino hasta el queso
    private boolean bomba;
    
    public M20B10DFS() {
        super("M20B10DFS");
        i = 0;
        exploredGrids=new HashMap<>();
        lastGrid = new Grid(0, 0);
        visitadas=new HashMap<>();
        marcados = new HashSet<>();
        camino=new LinkedList<>();
        bomba=false;
    }

    @Override
    public int move(Grid currentGrid, Cheese cheese) {
        
        Casilla actual = new Casilla(currentGrid);
        if (visitadas.get(clave(actual.getGrid())) == null) {//si no se ha pasado antes por la casilla
            visitadas.put(clave(actual.getGrid()), actual);
            if(exploredGrids.get(clave(actual.getGrid())) == null){
                exploredGrids.put(clave(actual.getGrid()), actual);
            }
        }
        //exploracion normal si el queso no esta en el mapa de visitadas
        if (!visitadas.containsKey(clave(cheese.getX(), cheese.getY()))) {
            return explorar(actual, currentGrid);
        }else{
           if (!camino.isEmpty()) {
             return (int) camino.removeFirst();
            }
        marcados.clear();
        lastGrid = currentGrid;
        planificarProfundidad(currentGrid.getX(),currentGrid.getY(),cheese);
        return (int)camino.removeFirst();}
    }

//Metodo que devuelve el movimiento que nos lleva a la casilla menos visitada
    private int explorar(Casilla actual, Grid currentGrid) {
        Casilla cUP = new Casilla(currentGrid.getX(), currentGrid.getY() + 1);
        Casilla cDOWN = new Casilla(currentGrid.getX(), currentGrid.getY() - 1);
        Casilla cLEFT = new Casilla(currentGrid.getX() - 1, currentGrid.getY());
        Casilla cRIGHT = new Casilla(currentGrid.getX() + 1, currentGrid.getY());
        if (visitadas.get(clave(actual.getGrid())) == null) {//si no se ha pasado antes por la casilla
            visitadas.put(clave(actual.getGrid()), actual);
            if(exploredGrids.get(clave(actual.getGrid())) == null){
                exploredGrids.put(clave(actual.getGrid()), actual);
            }
        }
        visitadas.get(clave(actual.getGrid())).incrementa();
        int min = 9999;
        int cont = 0;//vemos cual de las casillas de alrededor es la menos visitada
        int narriba = 10000, nabajo = 10000, nizq = 10000, nder = 10000;
        if (ultimo != UP && currentGrid.canGoUp()) {
            cont++;
            if (visitadas.containsKey(clave(cUP.getGrid()))) {
                narriba = visitadas.get(clave(cUP.getGrid())).getNveces();
            } else {
                narriba = 0;
            }
            if (narriba < min) {
                min = narriba;
            }
        }
        if (ultimo != DOWN && currentGrid.canGoDown()) {
            cont++;
            if (visitadas.containsKey(clave(cDOWN.getGrid()))) {
                nabajo = visitadas.get(clave(cDOWN.getGrid())).getNveces();
            } else {
                nabajo = 0;
            }
            if (nabajo < min) {
                min = nabajo;
            }
        }
        if (ultimo != LEFT && currentGrid.canGoLeft()) {
            cont++;
            if (visitadas.containsKey(clave(cLEFT.getGrid()))) {
                nizq = visitadas.get(clave(cLEFT.getGrid())).getNveces();
            } else {
                nizq = 0;
            }
            if (nizq < min) {
                min = nizq;
            }
        }
        if (ultimo != RIGHT && currentGrid.canGoRight()) {
            cont++;
            if (visitadas.containsKey(clave(cRIGHT.getGrid()))) {
                nder = visitadas.get(clave(cRIGHT.getGrid())).getNveces();
            } else {
                nder = 0;
            }
            if (nder < min) {
                min = nder;
            }
        }
        if (cont == 0) {//si esta en un callejon pone el numero de veces alto
            visitadas.get(clave(actual.getGrid())).setNveces(300);
            ultimo = inverso(ultimo);
            lastGrid = currentGrid;
            return ultimo;
        }//si esta en un callejon al salir pone las casillas del callejon con un nveces alto
        if (cont == 1 && visitadas.get(clave(lastGrid)).getNveces() >= 300) {
            visitadas.get(clave(actual.getGrid())).setNveces(300);
        }
        lastGrid = currentGrid;
        if (min == narriba) {
            ultimo = DOWN;
            return UP;
        } else if (min == nabajo) {
            ultimo = UP;
            return DOWN;
        } else if (min == nizq) {
            ultimo = RIGHT;
            return LEFT;
        } else {
            ultimo = LEFT;
            return RIGHT;
        }
    }

//Metodo que planifica un camino de forma recursiva hasta el queso   
    public boolean planificarProfundidad(int x,int y, Cheese cheese) {
        int cX=x;
        int cY=y;
        
        if(cX==cheese.getX()&&cY==cheese.getY()){
            return true;
        }
        Grid g=new Grid(x,y);
        Casilla c=new Casilla(g);

        Grid gUP=new Grid(x,y+1);
        Casilla cUP=new Casilla(gUP);
        Grid gD=new Grid(x,y-1);
        Casilla cDOWN=new Casilla(gD);
        Grid gL=new Grid(x-1,y);
        Casilla cLEFT=new Casilla(gL);
        Grid gR=new Grid(x+1,y);
        Casilla cRIGHT=new Casilla(gR);
        String x1 = String.valueOf(x);
        String y1 = String.valueOf(y);

        String actual = x1 + y1;
        String sup = x1 + String.valueOf(cY + 1);
        String inf = x1 +  String.valueOf(cY - 1);
        String izq = String.valueOf(cX - 1) + y1;
        String der = String.valueOf(cX + 1) + y1;
        
        marcados.add(actual);
        if (visitadas.containsKey(clave(cUP.getGrid()))&& !marcados.contains(sup)) {
            if(visitadas.get(clave(c.getGrid())).getGrid().canGoUp()){
            camino.addLast(UP);
            if(planificarProfundidad(x,cY+1,cheese)){
                return true;
            }
            camino.removeLast();
            }
        }
        if (visitadas.containsKey(clave(cDOWN.getGrid()))&& !marcados.contains(inf)) {
            if(visitadas.get(clave(c.getGrid())).getGrid().canGoDown()){
            camino.addLast(DOWN);
            if(planificarProfundidad(x,cY-1,cheese)){
                return true;
            }
            camino.removeLast();
            }
        }
        if (visitadas.containsKey(clave(cLEFT.getGrid()))&& !marcados.contains(izq)) {
            if(visitadas.get(clave(c.getGrid())).getGrid().canGoLeft()){
            camino.addLast(LEFT);
            if(planificarProfundidad(cX-1,y,cheese)){
                return true;
            }
            camino.removeLast();
            }
        }
        if (visitadas.containsKey(clave(cRIGHT.getGrid()))&& !marcados.contains(der)) {
            if(visitadas.get(clave(c.getGrid())).getGrid().canGoRight()){
            camino.addLast(RIGHT);
            if(planificarProfundidad(cX+1,y,cheese)){
                return true;
            }
            camino.removeLast();
            }
        }
       return false;
    }

//funcion para asignar claves 
    public int hashXY(Grid g, int movimiento) {
        switch (movimiento) {
            case UP:
                Grid gUP = new Grid(g.getX(), g.getY() + 1);
                return clave(gUP);
            case DOWN:
                Grid gDOWN = new Grid(g.getX(), g.getY() - 1);
                return clave(gDOWN);
            case LEFT:
                Grid gLEFT = new Grid(g.getX() - 1, g.getY());
                return clave(gLEFT);
            case RIGHT:
                Grid gRIGHT = new Grid(g.getX() + 1, g.getY());
                return clave(gRIGHT);
        }
        return clave(g);
    }

//asignamos una clave para añadir al mapahash
    public int clave(Grid g) {
        int x = g.getX();
        int y = g.getY();
        int res = (x * 10000) + y;
        return res;
    }

//para asignar una clave a una casilla determinada segun las coordenadas    
    public int clave(int x, int y) {
        int res = (x * 10000) + y;
        return res;
    }

//devuelve el movimiento inverso al realizado
    public int inverso(int movimiento) {
        switch (movimiento) {
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
            case UP:
                return DOWN;
            case DOWN:
                return UP;
        }
        return BOMB;
    }

    @Override
    public void newCheese() {

    }
    //pensar 
    @Override
    public void respawned() {
        bomba=true;
    }

    @Override
    public long getExploredGrids() {
        return exploredGrids.size();
    }

//clase auxiliar para contabilizar el numero de veces que hemos pasado por una casilla
    class Casilla {

        private Grid grid;
        private int nveces;

        public Casilla(Grid g) {
            grid = g;
            nveces = 0;

        }

        public Casilla(int x, int y) {
            Grid g = new Grid(x, y);
            grid = g;
            nveces = 0;
        }

        public void incrementa() {
            setNveces(getNveces() + 1);
        }

        /**
         * @return the casilla
         */
        public Grid getGrid() {
            return grid;
        }

        /**
         * @param casilla the casilla to set
         */
        public void setGrid(Grid casilla) {
            this.grid = casilla;
        }

        /**
         * @return the nveces
         */
        public int getNveces() {
            return nveces;
        }

        /**
         * @param nveces the nveces to set
         */
        public void setNveces(int nveces) {
            this.nveces = nveces;
        }
    }
}
