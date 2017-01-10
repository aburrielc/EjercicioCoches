
package ejerciciocoches;

import java.util.Scanner;

/**
 *
 * @author Andrés Burriel Cantisano
 */
public class EjercicioCoches {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        int accion = 0;
        boolean finCarrera = false;
        
        Coche coche1 = new Coche("Hola",0);
        
        do{
            do{
                Menu.pintaAcciones();
                accion = leer.nextInt();
            }while(accion < 1 || accion > 2);
            
            if(accion == 1){
                coche1.acelerar();
            }else{
                coche1.frenar();
            }
            
        }while(!finCarrera);
    }
}
