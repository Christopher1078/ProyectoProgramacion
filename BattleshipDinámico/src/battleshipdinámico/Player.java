package battleshipdinámico;
abstract public class Player {
    protected String username, password, logs[]=new String[10];
    protected int puntos;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        puntos=0;
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    abstract void mostrarTablero(char tablero[][]);

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPuntos() {
        return puntos;
    }
    
    public void sumarPuntos(int puntos){
        this.puntos+=puntos;
    }
    
    public void agregarLog(String nuevoLog){
        agregarLog(nuevoLog, 8);
    }
    
    private void agregarLog(String nuevoLog, int contador){
        if(contador>=0){
            logs[contador+1]=logs[contador];
            agregarLog(nuevoLog, contador-1);
            return;
        }
        logs[0]=nuevoLog;
    }
    
    public void mostrarLogs(){
        mostrarLogs(0);
    }
    
    private void mostrarLogs(int contador){
        if(contador<10){
            System.out.print((contador+1)+". ");
            if(logs[contador]!=null){
                System.out.print(logs[contador]);   
            }
            System.out.println();
            mostrarLogs(contador+1);
        }
    }
    
    public void mostrarDatos(){
        System.out.println("Username: "+username+"\nPuntos: "+puntos);
    }
}
