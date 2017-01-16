
package ejerciciocoches;

import java.util.InputMismatchException;
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
        System.out.println("Bienvenid@s a la carrera de coches de ANDRÉS BURRIEL CANTISANO");
        
        Scanner leer = new Scanner(System.in);
        int numCoches = 0;
        
        do{
            System.out.print("Introduzca el número de coches que van a participar: ");
            try{
                numCoches = leer.nextInt();

                if(numCoches < 2){
                    System.out.println("AVISO: Una carrera requiere al menos 2 participantes.");
                    System.out.println("");
                }
            }catch(InputMismatchException ex){
                System.out.println("AVISO: El número de participantes debe ser un valor numérico (entero).");
                System.out.println("Por favor, introduzca el número de participantes de la carrera.");
                System.out.println("");
                leer.next(); //Limpia el valor introducido que no es correcto.
            }
            
        }while(numCoches < 2);
        
        Carrera mCarrera = new Carrera(numCoches);
        
        int opcion = 0;
        boolean empiezaCarrera = false;
        
        do{
            Menu.mostrarMenu();
            System.out.print("Seleccione una opcion: ");
            try{
                opcion = leer.nextInt();
            
                switch(opcion){

                    case 1:
                        //INSCRIBIR COCHE
                        if(mCarrera.quedanPlazas()){
                            System.out.println("");
                            System.out.print("Introduzca el nombre del piloto: ");
                            String nombrePiloto = leer.next();
                            
                            int dorsal;
                            boolean repetido = false;
                            
                            do{
                                System.out.print("Introduzca el dorsal: ");
                                dorsal = leer.nextInt();
                                
                                repetido = mCarrera.comprobarDorsal(dorsal);
                                
                                if(repetido){
                                    System.out.println("El dorsal '" + dorsal + "' ya esta asignado. Por favor, introduzca otro.");
                                }else{
                                    System.out.println("Dorsal valido.");
                                }
                            }while(repetido);
                            
                            mCarrera.anadirCoche(new Coche(nombrePiloto, dorsal));
                        }else{
                            System.out.println("No se pueden inscribir mas coches. Todas las plazas ya están ocupadas.");
                        }
                        break;

                    case 2:
                        //EMPEZAR CARRERA
                        if(mCarrera.puedeEmpezar()){
                            System.out.println("COMIENZA LA CARRERA!");
                            if(mCarrera.getNumApuntados() < mCarrera.getNumCompetidores()){
                                System.out.println("AVISO: No se han llenado todas las plazas.");
                                System.out.println("TOTAL: " + mCarrera.getNumCompetidores() + ". Corriendo: " + mCarrera.getNumApuntados() + ".");
                            }
                            empiezaCarrera = true;
                        }else{
                            System.out.println("AVISO: La carrera no puede empezar."); 
                            System.out.println("Faltan " + (numCoches - mCarrera.getNumApuntados()) + " participantes.");
                        }
                        break;
                        
                    case 3:
                        //SALIR DEL PROGRAMA
                        System.exit(0);
                        break;

                    default:
                       System.err.println("Opción no disponible. Por favor, seleccione "
                               + "una entre las siguientes.");
                        System.out.println("");
                }
            }catch(InputMismatchException ex){
                System.out.println("AVISO: Las opciones solo pueden ser numericas (enteros).");
                System.out.println("Por favor, seleccione una entre las disponibles.");
                System.out.println("");
                leer.next(); //Limpia el valor introducido que no es correcto.
            }
        }while(!empiezaCarrera);
        
        //EMPIEZA CARRERA
        mCarrera.empezar();
        
        Coche[] mCoches = mCarrera.getCoches();
        
        boolean carreraTerminada = false;
        int espacio = 0;
        
        do{
            carreraTerminada = mCarrera.haTerminadoCarrera();
            
            if(!carreraTerminada){
                
                for(int i = 0; i < mCarrera.getNumApuntados(); i++){
                    Coche c = mCoches[i];
                    
                    System.out.println("");
                    System.out.println(c);
                    
                    if(c.getEstado().equalsIgnoreCase("ACCIDENTADO") && !mCarrera.haTerminadoCoche()){
                        c.rearrancar();
                        mCarrera.quitarCocheAccidentado();
                    }else if (!c.getEstado().equalsIgnoreCase("ACCIDENTADO") && !c.getEstado().equalsIgnoreCase("TERMINADO")){
                        do{
                            Menu.mostrarAccionesCoche();
                            System.out.print("Por favor, seleccione una acción: ");
                            opcion = leer.nextInt();

                            switch(opcion){
                                case 1:
                                    //ACELERAR
                                    espacio = c.acelerar();
                                    System.out.println("Espacio recorrido en este turno: " + espacio + " Km.");
                                    if(c.getEstado().equalsIgnoreCase("ACCIDENTADO")){
                                        mCarrera.sumarCocheAccidentado();
                                    }else{
                                        mCarrera.comprobarTerminado(c);
                                    }
                                    break;

                                case 2:
                                    //FRENAR
                                    espacio = c.frenar();
                                    System.out.println("Espacio recorrido en este turno: " + espacio + " Km.");
                                    mCarrera.comprobarTerminado(c);
                                    break;
                            }
                        }while(opcion != 1 && opcion != 2);
                    }
                }
            }
        }while(!carreraTerminada);
        
        System.out.println("-------------------------");
        System.out.println("LA CARRERA HA FINALIZADO!");
        System.out.println("-------------------------");
        
        //Se añaden los accidentados.
        for(int i = 0; i < mCarrera.getNumApuntados(); i++){
            Coche c = mCoches[i];
            
            if(c.getEstado().equalsIgnoreCase("ACCIDENTADO")){
               mCarrera.anadirCocheClasificacion(c);
            }
        }   
        
        mCoches = mCarrera.getCochesClasificados();
        
        for(int i = 0; i < mCarrera.getNumClasificados(); i++){
            Coche c = mCoches[i];
            
            if(c.getEstado().equalsIgnoreCase("TERMINADO")){
                System.out.println("-- Nº " + (i+1) + " --");
            }else{
                System.out.println("-- FUERA DE CARRERA --"); 
            }
            
            System.out.println("Nombre del piloto: " + c.getNombrePiloto());
            System.out.println("Dorsal: " + c.getDorsal());
            System.out.println("----------------------");
        }
    }
}
