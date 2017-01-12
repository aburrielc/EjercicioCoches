
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
        //int contador = 0;
        int opcion = 0;
        int dorsal;
        int accion = 0;
        
        boolean crearCoches = true;
        boolean dorsalRepetido = false;
        boolean finCarrera = false;
        boolean acabaCarrera = false;
        
        //Coche coche1 = new Coche("Pepito",0);
        
        System.out.println("Introduzca el número de coches que quiere crear");
        int numCoches = leer.nextInt();
        
        Coche [] vCoches = new Coche[numCoches];
        Coche [] vClasificacion = new Coche[numCoches];
        
        do{
            do{
                Menu.menuInicial();
                opcion = leer.nextInt();
            }while(opcion < 1 || opcion > 2);
            
            if(opcion == 1){
                int hueco = Menu.buscarHueco(vCoches);
                
                if(hueco != -1){
                    System.out.println("");
                    System.out.println("Introduzca el nombre del piloto");
                    String nombrePiloto = leer.next();
                    do{
                        System.out.println("Introduzca el número de dorsal");
                        dorsal = leer.nextInt();

                        dorsalRepetido = Menu.comprobarDorsal(vCoches, dorsal);

                        if(dorsalRepetido == false){
                            System.out.println("");
                            System.out.println("Dorsal válido");
                        }else{
                            System.out.println("");
                            System.out.println("El dorsal está repetido");
                            System.out.println("");
                        }
                    }while(dorsalRepetido == true);

                    vCoches[hueco] = new Coche(nombrePiloto,dorsal);
                }else{
                    System.out.println("");
                    System.out.println("No se pueden crear más coches");
                }
            }
            
            if(opcion == 2){
                System.out.println("");
                System.out.println("Comienza el juego!");
                crearCoches = false;
            }
        }while(crearCoches == true);
        

        do{
           
            finCarrera = Menu.comprobarFinCarrera(vCoches);
                
            //do{
                for(int i=0;i<vCoches.length;i++){

                    if(vCoches[i].getEstado().equalsIgnoreCase("TERMINADO")){
                        System.out.println("");
                        System.out.println(vCoches[i].getNombrePiloto()+ " Ud. ha acabado la carrera");
                        acabaCarrera = true;

                        int hueco = Menu.buscarHueco(vClasificacion);

                        if(hueco != -1){
                            vClasificacion[hueco] = vCoches[i];
                        }else{
                            System.out.println("");
                            System.out.println("No se pueden clasificar más coches");
                        }
                    }else{
                        System.out.println("");
                        System.out.println(vCoches[i]);
                        do{
                            Menu.pintaAcciones();
                            accion = leer.nextInt();
                        }while(accion < 1 || accion > 4);

                        switch(accion){
                            case 1:
                                vCoches[i].arrancar();
                                break;
                            case 2:
                                vCoches[i].acelerar();
                                break;
                            case 3:
                                vCoches[i].frenar();
                                break;
                            case 4:
                                vCoches[i].rearrancar(finCarrera);
                                break;
                        }
                    }
                }
            //}while(acabaCarrera == false);
        }while(!finCarrera);
        
        Menu.pintaClasificacion(vClasificacion);
    }
}
