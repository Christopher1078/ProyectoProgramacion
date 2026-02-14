package battleshipdinámico;
public class PlayerArcade extends Player{

    public PlayerArcade(String username, String password) {
        super(username, password);
    }
    
    @Override
    public void mostrarTablero(char tablero[][]){
        for(char t[]: tablero){
            for(char c: t){
                if(c=='~' || c=='P' || c=='A' || c=='D' || c=='S')
                    System.out.print(" "+'~'+" ");
                else System.out.print(" "+c+" ");
            }
            System.out.println("");
        }
    }
}
