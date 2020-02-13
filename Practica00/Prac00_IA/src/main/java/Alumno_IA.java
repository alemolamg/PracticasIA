
import java.util.Scanner;
import java.util.Vector;

/**
 * Clase Alumno_IA hereda de Alumno. 
 * Es un alumno matriculado de la asignatura de Inteligencia Artificial
 * @author AlemolAMG
 */
public class Alumno_IA extends Alumno{
    //  Atributos //
    float notaPracticas;
    int grupoPracticas;
    
    
    //  metodos  //
    
/**
 *  Constructor parametrizado de la clase Alumno_IA 
 * @param notaPracticas     (float)     nota del alumno en las practicas
 * @param grupoPracticas    (int)       numero del grupo de practicas
 * @param nombre            (string)    nombre del alumno
 * @param dni               (string)    dni del alumno
 * @param correoE            (string)   correo del alumno
 */
    public Alumno_IA(float notaPracticas, int grupoPracticas, String nombre, String dni, String correoE) {
        super(nombre, dni, correoE);
        this.notaPracticas = notaPracticas;
        this.grupoPracticas = grupoPracticas;
    }

    
    /**
     *  Constructor por defecto de la clase Alumno_IA
     */
    public Alumno_IA() {
        super();
        this.notaPracticas = 0;
        this.grupoPracticas = 1;
    }
    
    
    /**
     *  Calcula la nota media de 4 notas que se le pasan por teclado
     */
    public void calcularNotaMedia(){
        float [] vecNotas= new float [4];
//        Vector<Float> vecNotas=new float [4]
        int numNota=0;
        
        while(numNota<4){
            Scanner teclado = new Scanner(System.in);
            System.out.print("\nIntroduzca nota "+ ((char)numNota+1) +": ");
            vecNotas[numNota] = teclado.nextFloat();
            if(vecNotas[numNota]>=0 && vecNotas[numNota]<=10)
                numNota++;
            else
                System.out.println("Nota introducida incorrecta");
        }
        for (int i=0;i<numNota;++i){
            notaPracticas+=vecNotas[i];
        }
        notaPracticas=notaPracticas/numNota;
        System.out.println("\nLa nota media es: "+notaPracticas);
        
    }
    
}
