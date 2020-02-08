
import java.util.Scanner;

/**
 * @author molej
 */

public class Alumno {
    
    String nombre;
    String dni;
    String correoE;

    public Alumno(String nombre, String dni, String correoE) {
        this.nombre = nombre;
        this.dni = dni;
        this.correoE = correoE;
    }

    public Alumno() {
        this.nombre =   "Alumno";
        this.dni =      "123456789X";
        this.correoE=   "correoE@email.com";
    }
    
     
   void muestraAlumno(){
       String st1="\nNombre Alumno: " + this.nombre;
       String st2="\ndni: "+ dni;
       String st3="\ncorreo Electronico: "+this.correoE;
       
       System.out.println(st1+st2+st3);
   } 
   
    void ActualizarDatosTeclado(){
       
        Scanner teclado = new Scanner(System.in);
        System.out.print("Introduzca nombre: ");
        this.nombre = teclado.nextLine();
        System.out.println("Introduzca dni: ");
        this.dni = teclado.nextLine();
        System.out.println("Introduzca correo: ");
        this.correoE = teclado.nextLine();
        
   }
    
}
