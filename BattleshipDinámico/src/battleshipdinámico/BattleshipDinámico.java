package battleshipdinámico;
import java.util.Scanner;
public class BattleshipDinámico {
    static Scanner n=new Scanner(System.in);
    static void menuInicio(Battleship b){
        int opcion=0;
        boolean valido;
        String username, password;
        System.out.println("      Menu de Inicio      ");    
        System.out.println("Elija una opcion");
        System.out.println("1. Login\n2. Crear Player\n3. Salir");
        do{
            try{
                opcion=n.nextInt();
                valido=true;
            }catch(Exception e){
                valido=false;
                System.out.println("El valor ingresado es invalido");
                System.out.println("Vuelva a intentar: ");
            }
        }while(!valido);
        n.nextLine();
        switch(opcion){
            case 1:
                System.out.println("Ingrese su username");
                username=n.nextLine();
                System.out.println("Ingrese su password");
                password=n.nextLine();
                if(!b.validarLogin(username, password)){
                    System.out.println("Username o password invalido");
                    menuInicio(b);
                }
                menuPrincipal(b);
                break;
            case 2:
                System.out.println("Ingrese un username");
                username=n.nextLine();
                System.out.println("Ingrese un password");
                password=n.nextLine();
                if(!b.crearPlayer(username, password)){
                    System.out.println("El username ingresado ya existe");
                    menuInicio(b);
                }
                menuPrincipal(b);
                break;
            case 3:
                System.out.println("Cerrando juego");
                break;
            default:
                System.out.println("Opcion no valida");
                menuInicio(b);
        }
    }
    
    static void menuPrincipal(Battleship b){
        int opcion=0;
        boolean valido;
        String username, password;
        System.out.println("     Menu Principal     ");
        System.out.println("Elige una de las opciones");
        System.out.println("1. Jugar Battleship\n2. Configuracion\n3. Reportes\n4. Mi perfil\n5. Cerrar Sesion ");
        do{
            try{
                opcion=n.nextInt();
                valido=true;
            }catch(Exception e){
                valido=false;
                System.out.println("El valor ingresado es invalido");
                System.out.println("Vuelva a intentarlo: ");
            }
        }while(!valido);
        n.nextLine();
        switch(opcion){
            case 1:
                System.out.println("Ingrese el username del jugador 2");
                username=n.nextLine();
                if(!b.existeUsername(username) || b.jugadorRepetido(username)){
                    do{
                        System.out.println("Este username no existe o ya inicio sesion, intente de nuevo (Escriba EXIT para regresar al menu principal)");
                        username=n.nextLine();
                        if(username.equalsIgnoreCase("exit"))
                            menuPrincipal(b);
                    }while(!b.existeUsername(username) || b.jugadorRepetido(username));
                }
                b.iniciarPartida(username);
                menuPrincipal(b);
                break;
            case 2:
                configuracion(b);
                break;
            case 3:
                reportes(b);
                break;
            case 4:
                do{
                    System.out.println("     Mi Perfil     ");
                    System.out.println("Elija una de las opciones");
                    System.out.println("1. Ver mis datos\n2. Modificar mis datos\n3. Eliminar mi cuenta\n4. Regresar al menu principal");
                    opcion=n.nextInt();
                    n.nextLine();
                    switch(opcion){
                        case 1:
                            System.out.println("Datos: ");
                            b.mostrarDatos();
                            break;
                        case 2:
                            System.out.println("Ingrese su nuevo username");
                            username=n.nextLine();
                            System.out.println("Ingrese su nuevo password");
                            password=n.nextLine();
                            if(b.modificarDatos(username, password))
                                System.out.println("Cambios hechos con exito");
                            else System.out.println("El nombre de usuario ya existe, no se hicieron modificaciones");
                            break;
                        case 3:
                            b.eliminarCuenta();
                            System.out.println("Cuenta eliminada con exito, regresando al menu inicio");
                            menuInicio(b);
                            break;
                        case 4:
                            System.out.println("Regresando al menu principal");
                    }
                }while(opcion!=4);
                menuPrincipal(b);
                break;
            case 5:
                System.out.println("Regresando al menu inicio");
                menuInicio(b);
                break;
            default:
                System.out.println("Opcion no valida, vuelva a intentar");
                menuPrincipal(b);
        }
    }
    
    public static void configuracion(Battleship b){
        int opcion=0;
        boolean valido;
        System.out.println("     Configuracion     ");
        System.out.println("Elija una de las opciones");
        System.out.println("1. Dificultad\n2. Modo de juego\n3. Regresar a menu principal");
        do{
            try{
                opcion=n.nextInt();
                valido=true;
            }catch(Exception e){
                valido=false;
                System.out.println("El valor ingresado es invalido");
                System.out.println("Vuelva a intentarlo: ");
            }
        }while(!valido);
        switch(opcion){
            case 1: 
                System.out.println("Elija la dificultad de juego");
                System.out.println("1. EASY - 5 Barcos\n2. NORMAL - 4 Barcos\n3. EXPERT - 2 Barcos\n4. GENIUS - 1 Barcos");
                do{
                    try{
                        opcion=n.nextInt();
                        valido=true;
                    }catch(Exception e){
                        valido=false;
                        System.out.println("El valor ingresado es invalido");
                        System.out.println("Vuelva a intentarlo: ");
                    }
                }while(!valido);
                b.cambiarDificultad(opcion);
                configuracion(b);
                break;
            case 2:
                System.out.println("Seleccione uno de estos modos de juego");
                System.out.println("1. Tutorial - Todos los barcos son visibles\n2. Arcade - Esconde todos los barcos");
                    do{
                        try{
                            opcion=n.nextInt();
                            valido=true;
                        }catch(Exception e){
                            valido=false;
                            System.out.println("El valor ingresado es invalido");
                            System.out.println("Vuelva a intentarlo: ");
                        }
                    }while(!valido);
                    b.cambiarModoJuego(opcion);
                    configuracion(b);
                    break;
            case 3:
                System.out.println("Regresando al menu principal");
                menuPrincipal(b);
                break;
        }
    }
    
    public static void reportes(Battleship b){
        int opcion=0;
        boolean valido;
        System.out.println("     Reportes     ");
        System.out.println("Elija una de las opciones");
        System.out.println("1. Descripcion de mis ultimos 10 juegos\n2. Ranking de jugadores\n3. Regresar al menu principal");
        do{
            try{
                opcion=n.nextInt();
                valido=true;
            }catch(Exception e){
                valido=false;
                System.out.println("El valor ingresado es invalido");
                System.out.println("Vuelva a intentarlo: ");
            }
        }while(!valido);
        switch(opcion){
            case 1:
                System.out.println("Descripcion de los ultimos 10 juegos: ");
                b.mostrarReportes();
                reportes(b);
                break;
            case 2:
                System.out.println();
                b.mostrarRanking();
                reportes(b);
                break;
            case 3:
                System.out.println("Regresando al menu principal");
                menuPrincipal(b);
                break;
            default:
                System.out.println("Opcion invalida");
                reportes(b);
        }
    }
    
    public static void main(String[] args){
        Battleship b=new Battleship();
        menuInicio(b);
    }
}
