
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
            System.out.println("");
            System.out.println(coche1);
            
            if(coche1.getEstado().equalsIgnoreCase("TERMINADO")){
                System.out.println("");
                System.out.println("Se ha acabado la carrera");
                finCarrera = true;
            }else{
                do{
                    Menu.pintaAcciones();
                    accion = leer.nextInt();
                }while(accion < 1 || accion > 4);

                switch(accion){
                    case 1:
                        coche1.arrancar();
                        break;
                    case 2:
                        coche1.acelerar();
                        break;
                    case 3:
                        coche1.frenar();
                        break;
                    case 4:
                        coche1.rearrancar(finCarrera);
                        break;
                }
            }
        }while(!finCarrera);
    }
}
