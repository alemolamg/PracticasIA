package conecta4;

import java.util.Vector;
import java.util.TreeSet;
        
/**
 *
 * @author José María Serrano
 * @version 1.5 Departamento de Informática. Universidad de Jáen

 Inteligencia Artificial. 2º Curso. Grado en Ingeniería Informática

 Clase IAPlayer para representar al jugador CPU que usa una técnica de IA

 Esta clase es la que tenemos que implementar y completar
 *
 */
public class IAPlayer extends Player {  
    int limite = 3;
    
    /**
     *
     * @param tablero Representación del tablero de juego
     * @param conecta Número de fichas consecutivas para ganar
     * @return Jugador ganador (si lo hay)
     */
    @Override
    public int turnoJugada(Grid tablero, int conecta) {
        int limiteActual=0;
        
        // ...
        // Calcular la mejor columna posible donde hacer nuestra turnoJugada
        //Pintar Ficha (sustituir 'columna' por el valor adecuado)
        //Pintar Ficha
        //int columna = getRandomColumn(tablero);
        
        Nodo nodoJugada = new Nodo(tablero); 
        int mejorMov = 1;           // luego se cambia
//        int x=tablero.getColumnas(), y=tablero.getFilas(); //ToDo: Asignación tamaño tablero
        int minInicial, minActual;
        minInicial = Integer.MAX_VALUE;
        int filaAux = 0;
        int alfa = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;
        //unNodo.matrizNodo = tablero.toArray();
        
        //int matrix[][] = nodoJugada.matrizNodo.clone();
        minActual=calcularMin(nodoJugada, tablero, conecta, limiteActual, alfa, beta);
        for (int recorro = 0;recorro < tablero.getColumnas(); recorro++ ){
            if(minActual == nodoJugada.hijosNodo.elementAt(recorro).valorHeuristicaNodo){
                mejorMov=recorro;
            } else
                System.out.println("Mejor valor no es "+ recorro);
        }
        
//        
//        for (int j = 0; j < tablero.getColumnas(); j++) {
//            if (hayFilas(j, nodoJugada)) {
//                int aux = buscarFila(j,nodoJugada);
//                nodoJugada.matrizNodo[aux][j] = -1;
//                mostrarMatriz(nodoJugada.matrizNodo, nodoJugada.getColumnaNodo(), nodoJugada.getFilaNodo());
//                System.out.println("\nComenzamos el MiniMax");
//                minActual = calcularMin(nodoJugada,tablero,conecta,limiteActual,alfa,beta);   //Empieza Minimax
//                nodoJugada.matrizNodo[aux][j] = 0;
//                if (minActual < minInicial) {
//                    filaAux = aux;
//                    minInicial = minActual;
//                    mejorMov = j;
//                }
//            }
//        }
        int meterButton = tablero.setButton(mejorMov, Conecta4.PLAYER2);
               
        return tablero.checkWin(mejorMov, meterButton, conecta);
        //return tablero.checkWin(tablero.setButton(columna, Conecta4.PLAYER2), columna, conecta);

    } // Fin turnoJugada
    
    /**
     * Calcula el valor máximo, jugada deseada por el Jugador1
     * @param nodoActual
     * @param tablero
     * @param x
     * @param y
     * @param conecta
     * @return 
     */
    private int calcularMax(Nodo nodoActual, Grid tablero, int conecta, int limiteMax, int alfa, int beta) {

        System.out.println("Comenzamos valorMax");
        nodoActual.mostrarMatrizNodo();
        int termina = nodoActual.checkWin(conecta);      //verifica si se gana

        if (termina != 0 || limiteMax > limite) {
            return heuristicaCalcular(nodoActual, conecta);

        } else {

            int caminoMax = Integer.MIN_VALUE;
            for (int reCols = 0; reCols < tablero.getColumnas(); reCols++) {
                if (hayFilas(reCols, nodoActual)) {
                    int reFilas = buscarFila(reCols, nodoActual);
//                        nodoActual.rellenarNodo(reCols, reFilas,conecta, Conecta4.PLAYER1);
                    nodoActual.rellenarNodoHijo(reCols, conecta, Conecta4.PLAYER1);

                    alfa = calcularMaximo2Num(alfa, calcularMin(nodoActual.hijosNodo.elementAt(reCols), tablero, conecta, limiteMax++, alfa, beta));

                    if (alfa > caminoMax) {
                        caminoMax = alfa;
                        nodoActual.matrizNodo = copiarMatriz(nodoActual.hijosNodo.elementAt(reCols).matrizNodo, reFilas, reCols);
                        nodoActual.setheuristica(caminoMax);
                    }
                }
            }
            return caminoMax;
        }
    }   // Fin MIN

    
    
    /**
     * Calcula el valor mínimo, jugada deseada por el Jugador2
     * @param nodoActual
     * @param tablero
     * @param x
     * @param y
     * @param conecta
     * @return 
     */
    private int calcularMin(Nodo nodoActual, Grid tablero, int conecta, int limiteMin, int alfa, int beta) {

        System.out.println("Comenzamos valorMin");
        nodoActual.mostrarMatrizNodo();
        int termina = nodoActual.checkWin(conecta);

        if (termina != 0 || limiteMin > limite) {   //ToDo: Gestionar gane max o min o empate (tablero lleno) o nivel profundidad maximo
            return heuristicaCalcular(nodoActual, conecta);

        } else {

            int caminoMinimo = Integer.MAX_VALUE;                   //Iniciamos al valor mas alto
            for (int reCols = 0; reCols < tablero.getColumnas(); reCols++) {       //recorrer columnas
                if (hayFilas(reCols, nodoActual)) {
                    int reFilas = buscarFila(reCols, nodoActual);
                    if (nodoActual.matrizNodo[reFilas][reCols] == 0) {
//                        nodoActual.rellenarNodo(reFilas, reCols,conecta, Conecta4.PLAYER2);
                        nodoActual.rellenarNodoHijo(reCols, conecta, Conecta4.PLAYER2);
                        nodoActual.mostrarMatrizNodo();

                        beta = calcularMinimo2Num(beta, calcularMax(nodoActual.hijosNodo.elementAt(reCols), tablero, conecta, ++limiteMin, alfa, beta));
                        //aux = valorMax(nodoActual,tablero,conecta,++limiteMin);
                        if (beta < caminoMinimo) {
                            caminoMinimo = beta;
                            nodoActual.matrizNodo = copiarMatriz(nodoActual.hijosNodo.elementAt(reCols).matrizNodo, reFilas, reCols);
                            nodoActual.setheuristica(caminoMinimo);
                        }
                        nodoActual.matrizNodo[reFilas][reCols] = Conecta4.PLAYER2;
                        //matriz[i][j] = -1;
                    }
                }
            }
            return caminoMinimo;
        }
    }   // Fin MAX
    
    /**
     * Función intermedia para calcular la heurística total de un tablero
     * @param nodoActual    Nodo con la tabla y los datos.
     * @param conecta       Número de fichas seguidas para ganar.
     * @return              Valor de la heurística total.
     */
    public int heuristicaCalcular(Nodo nodoActual,int conecta){
        int resultado =(Conecta4.PLAYER1 * heuristica(nodoActual, conecta, Conecta4.PLAYER1)) + (Conecta4.PLAYER2 * heuristica(nodoActual, conecta, Conecta4.PLAYER2));
        return resultado;
    }
    
    /**
     * Calcula el mínimo entre dos números enteros
     * @param num1  número 1 a comparar
     * @param num2  número  a comparar
     * @return Devuelve el número más pequeño
     */
    private int calcularMinimo2Num(int num1, int num2) {
        if (num1 >= num2) {
            return num2;
        } else {
            return num1;
        }
    }
    
    /**
     * Calcula el máximo entre dos números enteros
     * @param num1  número 1 a comparar
     * @param num2  número 2 a comparar
     * @return Devuelve el número más grande
     */
    private int calcularMaximo2Num(int num1, int num2 ){
        if (num1 >= num2) {
            return num1;
        } else {
            return num2;
        }
    }
    
    /**
     * Calcula si la columna tiene filas libres
     * @param col       Columna en la que buscar fila
     * @param tablero   nodo con la matriz a añadir
     * @return  Devuelve verdad si encuentra fila libre en una columna o falso si no la encuentra
     */
    private boolean hayFilas(int col, Nodo tablero) {
        for (int fila = tablero.numFilas - 1; fila >= 0; fila--) {
            if (tablero.matrizNodo[fila][col] != Conecta4.PLAYER1 && tablero.matrizNodo[fila][col] != Conecta4.PLAYER2) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Dada una matriz de Integer, copia los datos a otra matriz.
     * 
     * @param origMatriz    Matriz de enteros
     * @param origFila      Número filas matriz original
     * @param origCol       Número columnas matriz original
     * @return  devuelve una matriz copiada paso a paso de la original.
     */
    public int[][] copiarMatriz(int[][] origMatriz, int origFila , int origCol){
        int nuevaMatriz[][] = new int[origFila][origCol];
        for (int fila = origFila - 1; fila >= 0; fila--) {
            for (int columna = 0; columna < origCol; columna++) {
                nuevaMatriz[fila][columna] = origMatriz[fila][columna];
            }
        }
        return nuevaMatriz;
    }
    
    /**
     * Calcula la fila libre dada una columna.
     * @param col       Columna en la que buscamos 
     * @param tablero   Nodo con la matriz
     * @return 
     */
    private int buscarFila(int col, Nodo tablero) {
        int cont = 0;
        for (int fila = tablero.numFilas - 1; fila >= 0; fila--) {
            if (tablero.matrizNodo[fila][col] != 1 && tablero.matrizNodo[fila][col] != -1) {
                cont = fila;
                break;
            }
        }
        return cont;
    }
    
    /**
     * Función que calcula el valor de ganar en cada casilla del nodo
     * @param nodoActual    Nodo desde el que partimos
     * @param conecta       número fichas seguidas para ganar
     * @param jugador       identificador del jugador para hacer heurística
     * @return 
     */
    private int heuristica(Nodo nodoActual, int conecta, int jugador) {
        int base=10;    // base sobre la que elevar
        
        int sumaJugador = 0;

        // Calcular Diagonal izquierda
        for (int filaMirar = nodoActual.numFilas - 1; filaMirar >= 0; filaMirar--) {
            for (int colMirar = 0; colMirar < nodoActual.numColumnas; colMirar++) {
                boolean calculoValido = true;
                int cantFichasVert = 0;
                if(colMirar - (conecta-1) >=0 && filaMirar -(conecta-1) >= 0 && nodoActual.matrizNodo[filaMirar][colMirar]!= 0){ //verificamos que podemos contar y que hay ficha para contar
                    int filaAux=filaMirar,colAux=colMirar;
                    
                    for(int diagonal=conecta-1;diagonal >=0; diagonal--){
                        if (nodoActual.matrizNodo[filaAux][colAux] == jugador) {
                            cantFichasVert++;
                            filaAux--; colAux--;
                        }else if(nodoActual.matrizNodo[filaAux][colAux] !=0){
                            calculoValido = false;
                        }
                    }
                    if(calculoValido){
                        sumaJugador += elevarPotencias(base, cantFichasVert);
                    }
                    
                }
   
            }
        }   // Fin Diagonal Izquierda
        
        // Calcular Vertical
        for (int colMirar = 0; colMirar < nodoActual.numColumnas; colMirar++) {     //recorremos columnas
            boolean calculoValido = true;
            int filaMirar = nodoActual.numFilas - 1;

            for (; filaMirar >= (filaMirar - (conecta - 1)); filaMirar--) {         //recorremos filas
                int cantFichasVert = 0;
                if (filaMirar - (conecta - 1) >= 0 && nodoActual.matrizNodo[filaMirar][colMirar] != 0) {   //ver si se puede subir
                    
                    if (nodoActual.matrizNodo[filaMirar][colMirar] == jugador) {
                        cantFichasVert++;
                    }else if(nodoActual.matrizNodo[filaMirar][colMirar] !=0){
                        calculoValido = false;
                    }
                    
                } else {
                    break;
                }
                if (calculoValido)
                sumaJugador += elevarPotencias(base, cantFichasVert);
            }
            
        }   // Fin Vertical

        
        // Calcular Diagonal Derecha
        for (int filaMirar = nodoActual.numFilas - 1; filaMirar >= 0; filaMirar--) {
            for (int colMirar = 0; colMirar < nodoActual.numColumnas; colMirar++) {
                boolean calculoValido = true;
                int cantFichasVert = 0;
                
                if(colMirar + (conecta-1) < nodoActual.numColumnas && filaMirar -(conecta-1) >= 0 && nodoActual.matrizNodo[filaMirar][colMirar]!= 0){ //verificamos que podemos contar y que hay ficha para contar
                    int filaAux=filaMirar,colAux=colMirar;
                    
                    for(int diagonal = conecta-1; diagonal >=0; diagonal--){
                        if (nodoActual.matrizNodo[filaAux][colAux] == jugador) {
                            cantFichasVert++;
                            filaAux--; colAux++;
                        }else if(nodoActual.matrizNodo[filaAux][colAux] !=0){
                            calculoValido = false;
                        }
                    }
                    if(calculoValido){
                        sumaJugador += elevarPotencias(base, cantFichasVert);
                    }
                    
                }
   
            }
        }   // Fin Diagonal Derecha
        
        // Calcular Horizontal
        for(int filaMirar = nodoActual.numFilas - 1; filaMirar >= 0; filaMirar--) {
            boolean calculoValido = true;
            int colMirar = 0;

            for (; colMirar < colMirar + (conecta - 1); colMirar++) {
                int cantFichasHor = 0;
                if (colMirar + (conecta - 1) >= nodoActual.numColumnas && nodoActual.matrizNodo[filaMirar][colMirar] != 0) {   //ver si se puede avanzar
                    if (nodoActual.matrizNodo[filaMirar][colMirar] == jugador) {
                        cantFichasHor++;
                    } else if (nodoActual.matrizNodo[filaMirar][colMirar] != 0) {
                        calculoValido = false;
                    }

                } else {
                    break;
                }
                if (calculoValido) {
                    sumaJugador += elevarPotencias(base, cantFichasHor);
                }
            }

        }   // Fin Horizontal
        

        // Calcular Diagonal Derecha
        
        
        
        return sumaJugador;
    }
    
    /**
     * Eleva una base a su exponente
     * @param base      Número base (será siempre 10)
     * @param exponente Exponente (número veces que se repite una ficha)
     * @return  potencia de exp base 10
     */
    private int elevarPotencias (int base, int exponente){
        return (int) Math.pow(base, exponente);
    }
    
    public void mostrarMatriz(int [][] matriz, int numColumnas, int numFilas){
         
        for (int fila = 0; fila < numFilas; fila++) {
            for (int col = 0; col < numColumnas; col++) {
                System.out.print(matriz[fila][col]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    
    
    public class Nodo{
        int numColumnas;        //  Número de columnas de la matriz
        int numFilas;           //  Número filas de la matriz
        int[][] matrizNodo;   //  matriz con el grid en enteros
        int ultimaCol;          //  ultima columna jugada
        int ultimaFila;         //  ultima fila jugada
        Vector<Nodo> hijosNodo;//  vector con los nodos hijos
        int valorHeuristicaNodo; 
//        int alfaNodo,betaNodo;
        
        /**
         * Crea un nodo a partir de un grid
         * @param tablero grid desde el que partimos
         */
        private Nodo (Grid tablero){
            numColumnas = tablero.getColumnas();
            numFilas = tablero.getFilas();
            matrizNodo = copiarMatriz(tablero.toArray(),tablero.getFilas(),tablero.getColumnas());    // copia a través del tablero del Grid
         
            ultimaCol = 0;                      //valor aleatorio
            ultimaFila = tablero.getFilas()-1;  //valor de la fila de abajo
            
            valorHeuristicaNodo = 0;
            hijosNodo = new Vector<Nodo>(tablero.getColumnas()-1);
//            alfaNodo = Integer.MAX_VALUE;
//            betaNodo = Integer.MIN_VALUE;     
        }
        
        /**
         * Constructor copia de nodo
         * @param orig Nodo original
         */
        private Nodo (Nodo orig){
            matrizNodo = orig.getMatrizNodo();
            numColumnas = orig.getColumnaNodo();
            numFilas = orig.getFilaNodo();
            
            ultimaCol = orig.ultimaCol;
            ultimaFila = orig.ultimaFila;
            
            valorHeuristicaNodo = orig.valorHeuristicaNodo;
//            alfaNodo = orig.alfaNodo;
//            betaNodo = orig.betaNodo;
            int i=0;                    
            do {
                if (orig.hijosNodo.size() != 0) {
                    hijosNodo.add(orig.hijosNodo.get(i));
                }
                i++;
            }while (orig.hijosNodo.get(i-1)!= orig.hijosNodo.lastElement());
                    
        }
        
        /**
         * Devuelve la matriz del nodo.
         * @return 
         */
        public int[][] getMatrizNodo(){
            return matrizNodo;
        }
        
//        public void setFilaNodo(int num){
//            ultimaFila=num;
//        }
        
        /**
         * Muestra el numero de filas que tiene la matriz del nodo
         * @return devuelve el número de filas
         */
        public int getFilaNodo(){
            return numFilas;            
        }
        
        /**
         * 
         * @param columna Coordenada de columna
         * @param fila Coordenada de fila
         * @return 
         */
        public int getTableroNodo(int columna,int fila){
            return matrizNodo[columna][fila];                   
        }
        
        public void setMatrizNodo(int matrizTablero[][]){
            matrizNodo=matrizTablero;            
        }
        
        public void setMatrizNodo(int col,int jugador){
            int fila = buscarFila(col, this);
            this.matrizNodo[fila][col] = jugador;            
        }
        
        public int getColumnaNodo() { 
            return numColumnas;
        }  
        
        /**
         * Añade todos los datos de una jugada al nodo
         * 
         * @param fila      Coordenada de la fila a añadir
         * @param col       Coordenada de la columna a añadir
         * @param jugador   jugador que se añade
         * @return Devuelve true siempre
         */
        public boolean rellenarNodo(int fila, int col,int conecta, int jugador){
            this.matrizNodo[fila][col] = jugador;
            
            this.hijosNodo.add(this);      //No se está usando
            valorHeuristicaNodo = heuristicaCalcular(this, conecta);
            this.ultimaCol=col;
            this.ultimaFila=fila;
            
            return true;
        }
        
        /**
         * Modifica el valor de la heurística
         * @param valorNuevo 
         */
        public void setheuristica(int valorNuevo){
            this.valorHeuristicaNodo = valorNuevo;
        }
        
        /**
         * Rellena el nodo hijo en la posición de la columna
         * @param col       columna donde se realiza la jugada
         * @param conecta   cantidad fichas seguidas a poner
         * @param jugador   jugador que pone la ficha
         */
        public void rellenarNodoHijo(int col,int conecta, int jugador){
            int fila = buscarFila(col, this);
            int [][] aux = copiarMatriz(matrizNodo, this.numFilas, this.numColumnas);
            Nodo auxNodo = new Nodo(this);
            this.hijosNodo.add(auxNodo);
            this.hijosNodo.elementAt(col).matrizNodo[fila][col]=jugador;
            
            this.hijosNodo.elementAt(col).ultimaCol = col;
            this.hijosNodo.elementAt(col).ultimaFila = fila;
            //this.hijosNodo.elementAt(col).valorHeuristicaNodo = heuristicaCalcular(this, conecta);
            
               
            
        }
        
        /**
         * muestra la matriz del nodo por pantalla
         */
        public void mostrarMatrizNodo(){ 
            for (int fila = 0; fila < numFilas; fila++) {
                for (int col = 0; col < numColumnas; col++) {
                    System.out.print(this.matrizNodo[fila][col]+" ");
                }
                System.out.println();
            }
            System.out.println();
        }
        
        /**
         * realiza una búsqueda en el nodo y se para en la primera casilla con datos
         */
        public void busquedaInicial(){
            //int[] vectorReturn= new int [2];
            for(int col=0;col<numColumnas; col++){
                for(int fila=this.numFilas-1;fila>=0;fila--){
                    if(this.matrizNodo[fila][col]!=0){
                        this.ultimaCol=col;
                        ultimaFila=fila;
                        break;
                    }
                }
            }
        }
         
        /**
         * Calcula si hay algún ganador
         * (Función adaptada de la original en el grid)
         * 
         * @param conecta   Número de filas seguidas para ganar
         * @return  Devuelve el valor del jugador ganador o 0 si nadie gana
         */
        public int checkWin(int conecta) {
            /*
		 *	x fila
		 *	y columna
             */

            //Comprobar vertical
            int ganar1 = 0;
            int ganar2 = 0;
            int ganador = 0;
            boolean salir = false;
            for (int i = 0; (i < numFilas) && !salir; i++) {
                if (this.matrizNodo[i][ultimaCol] != Conecta4.VACIO) {
                    if (matrizNodo[i][ultimaCol] == Conecta4.PLAYER1) {
                        ganar1++;
                    } else {
                        ganar1 = 0;
                    }
                    // Gana el jugador 1
                    if (ganar1 == conecta) {
                        ganador = Conecta4.PLAYER1;
                        salir = true;
                    }
                    if (!salir) {
                        if (matrizNodo[i][ultimaCol] == Conecta4.PLAYER2) {
                            ganar2++;
                        } else {
                            ganar2 = 0;
                        }
                        // Gana el jugador 2
                        if (ganar2 == conecta) {
                            ganador = Conecta4.PLAYER2;
                            salir = true;
                        }
                    }
                } else {
                    ganar1 = 0;
                    ganar2 = 0;
                }
            }
            // Comprobar horizontal
            ganar1 = 0;
            ganar2 = 0;
            for (int j = 0; (j < numColumnas) && !salir; j++) {
                if (matrizNodo[ultimaFila][j] != Conecta4.VACIO) {
                    if (matrizNodo[ultimaFila][j] == Conecta4.PLAYER1) {
                        ganar1++;
                    } else {
                        ganar1 = 0;
                    }
                    // Gana el jugador 1
                    if (ganar1 == conecta) {
                        ganador = Conecta4.PLAYER1;
                        salir = true;
                    }
                    if (ganador != Conecta4.PLAYER1) {
                        if (matrizNodo[ultimaFila][j] == Conecta4.PLAYER2) {
                            ganar2++;
                        } else {
                            ganar2 = 0;
                        }
                        // Gana el jugador 2
                        if (ganar2 == conecta) {
                            ganador = Conecta4.PLAYER2;
                            salir = true;
                        }
                    }
                } else {
                    ganar1 = 0;
                    ganar2 = 0;
                }
            }
            // Comprobar oblicuo. De izquierda a derecha
            ganar1 = 0;
            ganar2 = 0;
            int a = ultimaFila;
            int b = ultimaCol;
            while (b > 0 && a > 0) {
                a--;
                b--;
            }
            while (b < numColumnas && a < numFilas && !salir) {
                if (matrizNodo[a][b] != Conecta4.VACIO) {
                    if (matrizNodo[a][b] == Conecta4.PLAYER1) {
                        ganar1++;
                    } else {
                        ganar1 = 0;
                    }
                    // Gana el jugador 1
                    if (ganar1 == conecta) {
                        ganador = Conecta4.PLAYER1;
                        salir = true;
                    }
                    if (ganador != Conecta4.PLAYER1) {
                        if (matrizNodo[a][b] == Conecta4.PLAYER2) {
                            ganar2++;
                        } else {
                            ganar2 = 0;
                        }
                        // Gana el jugador 2
                        if (ganar2 == conecta) {
                            ganador = Conecta4.PLAYER2;
                            salir = true;
                        }
                    }
                } else {
                    ganar1 = 0;
                    ganar2 = 0;
                }
                a++;
                b++;
            }
            // Comprobar oblicuo de derecha a izquierda 
            ganar1 = 0;
            ganar2 = 0;
            a = ultimaFila;
            b = ultimaCol;
            //buscar posición de la esquina
            while (b < numColumnas - 1 && a > 0) {
                a--;
                b++;
            }
            while (b > -1 && a < numFilas && !salir) {
                if (matrizNodo[a][b] != Conecta4.VACIO) {
                    if (matrizNodo[a][b] == Conecta4.PLAYER1) {
                        ganar1++;
                    } else {
                        ganar1 = 0;
                    }
                    // Gana el jugador 1
                    if (ganar1 == conecta) {
                        ganador = Conecta4.PLAYER1;
                        salir = true;
                    }
                    if (ganador != Conecta4.PLAYER1) {
                        if (matrizNodo[a][b] == Conecta4.PLAYER2) {
                            ganar2++;
                        } else {
                            ganar2 = 0;
                        }
                        // Gana el jugador 2
                        if (ganar2 == conecta) {
                            ganador = Conecta4.PLAYER2;
                            salir = true;
                        }
                    }
                } else {
                    ganar1 = 0;
                    ganar2 = 0;
                }
                a++;
                b--;
            }

            return ganador;
        } // checkWin

    }

} // IAPlayer
