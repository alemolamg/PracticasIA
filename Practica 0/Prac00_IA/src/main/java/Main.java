import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Ana
 */
public class Main {

    /**
     * Main donde está el código de ejecución principal
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String cadena2=new String ("pares.txt");
        
        Alumno [] vecExtra= leerArchivos("datos.txt");
        vecExtra=guardarPares(vecExtra);
        boolean wanda=guardarArchivos(cadena2,vecExtra);
        System.out.println("\n"+wanda);
       } 
    
    /**
     * Lee alumnos desde un archivo externo
     * @param nombreArchivo (String)    Nombre del archivo desde donde se leen los alumnos
     * @return vecAl        (Alumno []) Vector con los alumnos leidos
     */
    static Alumno [] leerArchivos(String nombreArchivo){
        Alumno [] vecAl=new Alumno[4]; 
        int contVec=0;
        
        try{
            BufferedReader br= new BufferedReader(new FileReader(nombreArchivo));
            
            String linea=br.readLine();
            while(linea!=null){
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
    
    
    /**
     * @param nombreArchivo (String)    Nombre del archivo donde guardar los Alumnos
     * @param vecAlum       (Alumno []) vector con los alumnos
     * @return True     Alunmos guardados correctamente
     * @return False    alumnos no guardados correctamente
     */
    static boolean guardarArchivos(String nombreArchivo,Alumno [] vecAlum){
        
        try {
            BufferedWriter bw= new BufferedWriter(new FileWriter(nombreArchivo));
            for(int cont=0;cont<vecAlum.length;cont++){
                String textAlumno=vecAlum[cont].getNombre()+", "+vecAlum[cont].getDni()+", "+
                        vecAlum[cont].correoE;
                bw.write(textAlumno);
                bw.newLine();
            }
            bw.flush();
               
        } catch (Exception e) {
            return false;
        }
        return true;
    }  
    
    /**
     * Guarda en un nuevo vector los alumnos cuyos dni son par
     * @param vecAlum    (Alumno[]) Vector con todos los alumnos
     * @return vecReturn (Alumno[]) Vector con los alumnos con dni par
     */
    static Alumno [] guardarPares(Alumno[] vecAlum){
        int numVecPares=0;
        Alumno [] aux1 = new Alumno[vecAlum.length];
    
        for(int i=0;i<vecAlum.length;i++){
            if((Integer.parseInt(vecAlum[i].getDni())%2==0) && (vecAlum[i]!=null)){
                aux1[numVecPares]=vecAlum[i];
                numVecPares++;
            }     
        }
    
        Alumno [] vecReturn = new Alumno[numVecPares];
        for (int i=0;i<vecReturn.length;i++){
            vecReturn[i]=aux1[i];
        }
    
        return vecReturn;
    } 

}