package battleshipdinámico;
public class PlayerTutorial extends Player{

    public PlayerTutorial(String username, String password) {
        super(username, password);
    }
    
    @Override
    public void mostrarTablero(char tablero[][]){
        for(char t[]: tablero){
            for(char c: t){
                System.out.print(" "+c+" ");
            }
            System.out.println("");
        }
    }
}
