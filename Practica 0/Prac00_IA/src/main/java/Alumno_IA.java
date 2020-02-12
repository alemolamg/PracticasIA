
import java.util.Scanner;
import java.util.Vector;

/**
 * @author Alemol
 */
public class Alumno_IA extends Alumno{

    public Alumno_IA(float notaPracticas, int grupoPracticas, String nombre, String dni, String correoE) {
        super(nombre, dni, correoE);
        this.notaPracticas = notaPracticas;
        this.grupoPracticas = grupoPracticas;
    }

    
    
    public Alumno_IA() {
        super();
        this.notaPracticas = 0;
        this.grupoPracticas = 1;
    }
    
    float notaPracticas;
    int grupoPracticas;
    
    
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
