/**
 * @author alemolamg
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
//        Alumno ana("Ana","50617459W","correo@gmai.com");
        Alumno alemol=new Alumno();
        Alumno felipe=new Alumno_IA();
//        alemol.ActualizarDatosTeclado();
        alemol.muestraAlumno();
        felipe.calcularNotaMedia();
        
    }
    
}
