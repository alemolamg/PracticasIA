
import java.util.Scanner;

/**
 * @author molej
 */
public class Alumno_IA extends Alumno{

    
    
    public Alumno_IA() {
        super();
        this.notaPracticas = 5;
        this.grupoPracticas = 1;
    }
    
    float notaPracticas;
    int grupoPracticas;
    
    void calcularNotaMedia(){
        float [] vecNotas= new float [4];
        int numNota=0;
        
        while(numNota<=4){
            Scanner teclado = new Scanner(System.in);
            System.out.print("\nIntroduzca nota "+ (numNota+1) +": ");
            vecNotas[numNota] = teclado.nextFloat();
            if(vecNotas[numNota]>=0 && vecNotas[numNota]<=10)
                numNota++;
            else
                System.out.println("Nota introducida incorrecta");
        }
        for (int i=0;i<numNota;++i){
            notaPracticas+=vecNotas[i];
        }
        System.out.println("\nLa nota media es: "+notaPracticas);
        
    }
    
}
