package battleshipdinámico;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
public class Battleship {
    private ArrayList<Player> jugadores= new ArrayList<>();
    private Player logIn, ranking[];
    private int dificultad=2, modoJuego=1, vidas1[], vidas2[];
    private String codigos1[], codigos2[];
    private char[][] tablero1=new char[8][8], tablero2=new char[8][8];
    static Scanner n=new Scanner(System.in);

    
    public boolean validarLogin(String username, String password){
        for(Player u: jugadores){
            if(u.getUsername().equals(username)){
                if(u.getPassword().equals(password)){
                    logIn=u;
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean jugadorRepetido(String username){
        return logIn.getUsername().equals(username);
    }
    
    public boolean existeUsername(String username){
        for(Player p: jugadores){
            if(p.getUsername().equals(username))
                return true;
        }
        return false;
    }
    
    public boolean crearPlayer(String username, String password){
            if(existeUsername(username) || password.isBlank())
                return false;
        Player nuevoJugador=new PlayerArcade(username, password);
        jugadores.add(nuevoJugador);
        logIn=nuevoJugador;
        return true;
    }
    
    public void logout(){
        logIn=null;
    }
    
    public boolean modificarDatos(String username, String password){
        if(!username.equals(logIn.getUsername())){
                if(existeUsername(username))
                    return false;
        }
        logIn.setUsername(username);
        logIn.setPassword(password);
        return true;
    }
    
    public boolean eliminarCuenta(){
        if(logIn==null)
            return false;
        jugadores.remove(logIn);
        logIn=null;
        return true;
    }
    
    public void mostrarRanking(){
        Player temporal;
        ranking =new Player[jugadores.size()];
        for(int i=0;i<jugadores.size();i++){
            ranking[i]=jugadores.get(i);
        }
        for (Player r : ranking) {
            for(int j=1;j<ranking.length;j++){
                if(ranking[j-1].getPuntos()<ranking[j].getPuntos()){
                    temporal=ranking[j];
                    ranking[j]=ranking[j-1];
                    ranking[j-1]=temporal;
                }
            }
        }
        System.out.println("RANKING DE JUGADORES");
        for(Player p: ranking){
            System.out.println(p.getUsername()+" Puntos: "+p.getPuntos());
        }
    }
    
    public void mostrarReportes(){
        logIn.mostrarLogs();    
    }
    
    public void mostrarDatos(){
        logIn.mostrarDatos();
    }
    
    public void cambiarDificultad(int dificultad){
        if(dificultad<1 || dificultad>4){
            return;
        }
        this.dificultad=dificultad;
    }
    
    private int cantBarcos(){
        switch(dificultad){
            case 1: 
                return 5;
            case 2:
                return 4;
            case 3:
                return 2;
            case 4:
                return 1;
        }
        return 0;
    }
    
    public void cambiarModoJuego(int modoJuego){
        if(modoJuego<1 || modoJuego>2){
            return;
        }
        this.modoJuego=modoJuego;
    }
    
    public Player encontrarJugador(String username){
        for(Player p: jugadores){
            if(p.getUsername().equals(username))
                return p;
        }
        return null;
    }
    
    public void iniciarPartida(String usernameJugador2){
        Player jugador1;
        Player jugador2=encontrarJugador(usernameJugador2);
        if(modoJuego==1){
            jugador1 = new PlayerTutorial(logIn.getUsername(),logIn.getPassword());
            jugador2 = new PlayerTutorial(jugador2.getUsername(), jugador2.getPassword());
        }
        else{
            jugador1 = new PlayerArcade(logIn.getUsername(),logIn.getPassword());
            jugador2 = new PlayerArcade(jugador2.getUsername(), jugador2.getPassword());
        }
        
        inicializarTablero(tablero1);
        inicializarTablero(tablero2);
        codigos1=new String[cantBarcos()];
        codigos2=new String[cantBarcos()];
        vidas1=new int[cantBarcos()];
        vidas2=new int[cantBarcos()];
        System.out.println("JUGADOR 1, colocar barcos");
        pedirBarcos(tablero1, codigos1, vidas1, cantBarcos());
        System.out.println("JUGADOR 2, colocar barcos");
        pedirBarcos(tablero2, codigos2, vidas2, cantBarcos());
        cicloJuego(jugador1, jugador2);
    }
    
    private void inicializarTablero(char[][] tablero){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                tablero[i][j]='~';
            }
        }
    }
    
    private void quitarMarcas(char[][] tablero){
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(tablero[i][j]=='X' || tablero[i][j]=='F')
                    tablero[i][j]='~';
            }
        }    
    }
    
    private int tamanoBarcos(String codigo){
        switch(codigo){
            case "PA":
                return 5;
            case "AZ":
                return 4;
            case "SM":
                return 3;
            case "DT":
                return 2;
        }
        return 0;
    }
    
    private boolean tipoDisponible(String codigo, String[] codigos, int usados){
        for(int i=0;i<usados;i++){
            if(codigos[i].equalsIgnoreCase(codigo)){
                if(dificultad!=1 || !codigo.equalsIgnoreCase("DT")){
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean puedeColocarse(char[][] tablero, int fila, int columna, int tamano, boolean horizontal){
        if(horizontal){
            for(int i=columna;i<tamano+columna;i++){
                if(i>=8)
                    return false;
                if(tablero[fila][i]!='~'){
                    return false;
                }
            }
            return true;
        }
        for(int i=fila;i<tamano+fila;i++){
            if(i>=8)
                return false;
            if(tablero[i][columna]!='~'){
                return false;
            }
        }
        return true;
    }
    
    private void pedirBarcos(char[][] tablero, String[] codigos, int[] vidas, int maxBarcos){
        pedirBarcos(tablero, codigos, vidas, maxBarcos, 0);
    }
    
    private void pedirBarcos(char[][] tablero, String[] codigos, int[] vidas, int maxBarcos, int contador ){
        String codigo, respuesta;
        int fila, columna, tamano;
        Boolean horizontal;
        if(contador<maxBarcos){
            System.out.println("    Barcos       Codigos   Tamaño/Vidas");
            System.out.println(" Portaaviones      PA           5      ");
            System.out.println("  Acorazado        AZ           4      ");
            System.out.println("  Submarino        SM           3      ");
            System.out.println("  Destructor       DT           2      ");
            System.out.println("Ingrese el CODIGO del barco que desea ingresar");
            codigo=n.nextLine().toUpperCase();
            tamano=tamanoBarcos(codigo);
            if(tamano==0){
                System.out.println("Codigo ingresado invalido");
                pedirBarcos(tablero, codigos, vidas, maxBarcos, contador);
                return;               
            }
            if(!tipoDisponible(codigo, codigos, contador)){
                System.out.println("El barco que intento colocar no esta disponible");
                pedirBarcos(tablero, codigos, vidas, maxBarcos, contador);
                return;
            }
            for(char[] c: tablero){
                for(char t: c){
                    System.out.print(" "+t+" ");
                }
                System.out.println("");
            }
            System.out.println("Ingrese la fila en la que quiere colocar el barco (1-8)");
            try{
                fila=n.nextInt()-1;
            }catch(Exception e){
                System.out.println("Valor ingresado invalido");
                n.nextLine();
                pedirBarcos(tablero, codigos, vidas, maxBarcos, contador);
                return;
            }
            if(fila<0 || fila>=8){
                System.out.println("El valor ingresado esta fuera de los limites");
                n.nextLine();
                pedirBarcos(tablero, codigos, vidas, maxBarcos, contador);
                return;
            }
            System.out.println("Ingrese la columna en la que quiere colocar el barco (1-8)");
            try{
                columna=n.nextInt()-1;
            }catch(Exception e){
                System.out.println("Valor ingresado invalido");
                n.nextLine();                
                pedirBarcos(tablero, codigos, vidas, maxBarcos, contador);
                return;
            }
            if(columna<0 || columna>=8){
                System.out.println("El valor ingresado esta fuera de los limites");
                n.nextLine();                
                pedirBarcos(tablero, codigos, vidas, maxBarcos, contador);
                return;
            }
            System.out.println("Ingrese HORIZONTAL o VERTICAL segun la orientacion en la que quiera colocar su barco");
            n.nextLine();
            respuesta=n.nextLine();
            if(respuesta.equalsIgnoreCase("HORIZONTAL")){
                horizontal=true;
            }
            else if(respuesta.equalsIgnoreCase("VERTICAL")){
                horizontal=false;
            }
            else{
                System.out.println("La orientacion que ingreso no es valida");
                pedirBarcos(tablero, codigos, vidas, maxBarcos, contador);
                return;
            }
            if(!puedeColocarse(tablero, fila, columna, tamano, horizontal)){
                System.out.println("El barco ingresado no se puede colocar en la posicion deseada");
                pedirBarcos(tablero, codigos, vidas, maxBarcos, contador);
                return;
            }
            System.out.println("Barco colocado exitosamente");
            codigos[contador]=codigo;
            vidas[contador]=tamano;
            colocarBarcos(tablero, codigo, fila, columna, tamano, horizontal);
            contador++;
            if(contador<maxBarcos)
                pedirBarcos(tablero, codigos, vidas, maxBarcos, contador);
        }
    }
    
    private void colocarBarcos(char[][] tablero, String codigo, int fila, int columna, int tamano, boolean horizontal){
        if(horizontal){
            for(int i=columna;i<tamano+columna;i++){
                tablero[fila][i]=codigo.charAt(0);
            }
        }
        else{
            for(int i=fila;i<tamano+fila;i++){
                tablero[i][columna]=codigo.charAt(0);
            }
        }            
    }
    
    private String getDificultad(){
        switch(dificultad){
            case 1:
                return "EASY";
            case 2:
                return "NORMAL";
            case 3:
                return "EXPERT";
            case 4:
                return "GENIUS";
        }
        return null;
    }
            
    private void cicloJuego(Player jugador1, Player jugador2){
        do{
            System.out.println("Turno: "+jugador1.getUsername());
            if(turno(jugador1, jugador2, tablero2, codigos2, cantBarcos(), vidas2))
                break;
            quitarMarcas(tablero2);
            System.out.println("Turno: "+jugador2.getUsername());
            if(turno(jugador2, jugador1, tablero1, codigos1, cantBarcos(), vidas1))
                break;
            quitarMarcas(tablero1);
        }while(!finPartida(vidas1) || (!finPartida(vidas2)));
        if(finPartida(vidas1)){
            finJuego(jugador2, jugador1, jugador2.getUsername()+" hundio todos los barcos de "+jugador1.getUsername()+" en modo "+getDificultad());
        }
        else if(finPartida(vidas2)) finJuego(jugador1, jugador2, jugador1.getUsername()+" hundio todos los barcos de "+jugador2.getUsername()+" en modo "+getDificultad());
    }
    
    private boolean turno(Player activo, Player otroJugador, char[][] tableroOtro, String[] codigosOtro, int cantBarcos, int[] vidasOtro){
        otroJugador.mostrarTablero(tableroOtro);
        Boolean valido;
        int fila=0, columna=0;
        System.out.println("Ingresa la fila que desea atacar (Ingrese -1 para rendirse)");
        do{
            try{
                fila=n.nextInt()-1;
                valido=true;
            }catch(Exception e){
                System.out.println("El valor ingresado es invalido");
                System.out.println("Vuelva a intentarlo: ");
                valido=false;
                n.nextLine();
            }
        }while(!valido);
        System.out.println("Ingrese la columna que desea atacar (Ingrese -1 para rendirse)");
        do{
            try{
                columna=n.nextInt()-1;
                valido=true;
            }catch(Exception e){
                System.out.println("El valor ingresado es invalido");
                System.out.println("Vuelva a intentarlo: ");
                valido=false;
                n.nextLine();
            }
        }while(!valido);
        n.nextLine();
        if(fila==-2 && columna==-2){
            System.out.println("Esta seguro de que quiere retirarse?");
            if(n.nextLine().equalsIgnoreCase("si")){
                finJuego(otroJugador, activo, activo.getUsername()+" se retiro del juego dejando como ganador a "+otroJugador.getUsername());
                return true;
            }
            else{
                System.out.println("Regresando al juego");
                turno(activo, otroJugador, tableroOtro, codigosOtro, cantBarcos, vidasOtro);
                return false;
            }                
        }else if(fila<=-1 || columna<=-1 || fila>=8 || columna>=8){
            System.out.println("Uno de los valores ingresados se salen de los limites");
            turno(activo, otroJugador, tableroOtro, codigosOtro, cantBarcos, vidasOtro);
            return false;
        }        
        if(atacar(tableroOtro, fila, columna, codigosOtro, vidasOtro, cantBarcos))
            tableroOtro[fila][columna]='X';
        else tableroOtro[fila][columna]='F';
        otroJugador.mostrarTablero(tableroOtro);
        if(tableroOtro[fila][columna]=='X'){
            inicializarTablero(tableroOtro);
            regenerarTablero(tableroOtro, codigosOtro, vidasOtro, cantBarcos);
        }
        return finPartida(vidasOtro);
    }
    
    private boolean atacar(char[][] tablero, int fila, int columna, String[] codigos, int[] vidas, int cantBarcos){
        if(tablero[fila][columna]=='~'){
            System.out.println("Ataque fallido");
            return false;
        }
        int indice=barcoAtacado(tablero, fila, columna, codigos, cantBarcos);
        vidas[indice]-=1;
        switch(codigos[indice].charAt(0)){
            case 'P':
                System.out.println("Se ha golpeado al portaaviones");
                break;
            case 'A':
                System.out.println("Se ha golpeado al acorazado");
                break;
            case 'S':
                System.out.println("Se ha golpeado al submarino");
                break;
            case 'D':
                System.out.println("Se ha golpeado al destructor");
                break;
        }
        if(barcoHundido(vidas, indice)){
            switch(codigos[indice].charAt(0)){
                case 'P':
                    System.out.println("El portaaviones ha caido");
                    break;
                case 'A':
                    System.out.println("El acorazado ha caido");
                    break;
                case 'S':
                    System.out.println("El submarino ha caido");
                    break;
                case 'D':
                    System.out.println("El destructor ha caido");
                    break;
            }
        }
        return true;
    }
    
    private int barcoAtacado(char[][] tablero, int fila, int columna, String[] codigos, int cantBarcos){
        char letra=tablero[fila][columna];
        for(int i=0;i<cantBarcos;i++){
            if(letra==codigos[i].charAt(0))
                return i;
        }
        return -1;
    }
    
    private boolean finPartida(int[] vidas){
        for(int v: vidas){
            if(v!=0){
                return false;
            }
        }
        return true;
    }
    
    private boolean barcoHundido(int[] vidas, int indice){
        return vidas[indice]==0;
    }
    
    private void regenerarTablero(char[][] tablero, String[] codigos, int[] vidas, int cantBarcos){
        regenerarTablero(tablero, codigos, vidas, cantBarcos, 0);
    }
    
    private void regenerarTablero(char[][] tablero, String[] codigos, int[] vidas, int cantBarcos, int contador){
        int fila, columna, tamano;
        Random r=new Random();
        boolean horizontal;
        tamano=tamanoBarcos(codigos[contador]);
        fila=Math.abs(r.nextInt()%7);
        columna=Math.abs(r.nextInt()%7);
        horizontal=r.nextBoolean();
        if(vidas[contador]>0){
            if(puedeColocarse(tablero, fila, columna, tamano, horizontal)){
                colocarBarcos(tablero, codigos[contador], fila, columna, tamano, horizontal);
            }
            else {
                regenerarTablero(tablero, codigos, vidas, cantBarcos, contador);
                return;
            }    
        }
        contador++;
        if(contador<cantBarcos){
            regenerarTablero(tablero, codigos, vidas, cantBarcos, contador);
        }
    }
    
    private void finJuego(Player ganador, Player perdedor, String mensaje){
        System.out.println(mensaje);
        ganador.sumarPuntos(3);
        if(ganador.equals(logIn))
            logIn.sumarPuntos(3);
        else encontrarJugador(ganador.getUsername()).sumarPuntos(3);
        logIn.agregarLog(mensaje);
    }
}
