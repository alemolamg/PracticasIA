
import java.util.Scanner;

/**
 * @author molej
 */

public class Alumno {
    
    private String nombre;
    private String dni;
    String correoE;

    public Alumno(String nombre, String dni, String correoE) {
        this.nombre = nombre;
        this.dni = dni;
        this.correoE = correoE;
    }

    public Alumno() {
        this.nombre =   "Alumno";
        this.dni =      "1234567895";
        this.correoE=   "correoE@email.com";
    }
    
     
   void muestraAlumno(){
       String st1="\nNombre Alumno: " + this.getNombre();
       String st2="\ndni: "+ getDni();
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

    void calcularNotaMedia() {
        throw new UnsupportedOperationException("calcularNotaMedia::Alumno no implementada"); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }
        
}
