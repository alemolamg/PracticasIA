import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 * @author alemolamg
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Vector<Alumno> vecAlumnos=new Vector<Alumno>();
        String cadena=new String ("datos.txt");
        Alumno [] vectoAlum=null;
        
//        Alumno ana("Ana","50617459W","correo@gmai.com");
//        Alumno alemol=new Alumno();
        Alumno felipe=new Alumno_IA();
//        alemol.ActualizarDatosTeclado();
//        alemol.muestraAlumno();
//        felipe.calcularNotaMedia();
        
//        Vector<Alumno> vecAlumnos=leerArchivos(cadena);
//        Alumno [] vectoAlum=leerArchivos(cadena);
        
        vectoAlum[0]=new Alumno("Felipe", "2250", "felipe@gmail.com");
        vectoAlum[1]=new Alumno_IA(6,1,"Alemol", "2665", "Alemol@gomez.com");
        vectoAlum[2]=new Alumno();
        boolean wanda=guardarArchivos(cadena,vectoAlum);
        
       } 
    
    
    static Alumno [] leerArchivos(String nombreArchivo){
//        Vector<Alumno> vecAl=null;
        Alumno [] vecAl=null;
        int contVec=0;
        
        try{
            BufferedReader br= new BufferedReader(new FileReader(nombreArchivo));
            
            String linea=br.readLine();
            while(linea!=null){
//                System.out.println(linea);
                String [] lecturas= linea.split(", ");
                for (int i=0;i<lecturas.length;i++){
                    System.out.println(lecturas[i]);
                }
                
                Alumno alTemp=new Alumno(lecturas[0],lecturas[1],lecturas[2]);
                
               vecAl[contVec]=alTemp;
               ++contVec;
               linea=br.readLine();                
            }
        
            br.close();
        }catch(IOException e){
            System.out.println("Error leerArchivos: "+e);
        }
        
        return vecAl;
    }

    
    static boolean guardarArchivos(String nombreArchivo,Alumno [] vecAl){
        
        try {
            BufferedWriter bw= new BufferedWriter(new FileWriter(nombreArchivo));
            for(int cont=0;cont<vecAl.length;cont++){
                String textAlumno=vecAl[cont].nombre+", "+vecAl[cont].dni+", "+
                        vecAl[cont].correoE;
                bw.write(textAlumno);
                bw.newLine();
            }
            bw.flush();
               
        } catch (Exception e) {
            return false;
        }
        return true;
    }  
}
