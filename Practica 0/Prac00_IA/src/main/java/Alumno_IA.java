
import java.util.Scanner;

/**
 * @author molej
 */
public class Alumno_IA extends Alumno{
    
    float notaPracticas;
    int grupoPracticas;
    
    void calcularNotaMedia(){
        float vecNotas[];
        int numNota=0;
        while(numNota<=4){
            
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduzca nota "+ numNota+1 +": ");
        vecNotas[numNota] = new float teclado.nextFloat();
        if(vecNotas[numNota]<0 && vecNotas[numNota]>=10)
            numNota++;
        else
                System.out.println("Nota introducida incorrecta");
        }
        
        
    }
    
}
