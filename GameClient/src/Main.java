import states.GameState;
import structs.GPosition;
import structs.Player;

public class Main {
    //the lower the delay, the more accurate the game is but the higher cpu usage it takes.
    public static int tickDelay = 50; //1000 is 1 tick, 100 is 10 ticks, 10 is 100 ticks
    public GameState gameStateInstance; //change to array for multiple matches, but probably not


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) { //check if all uids are unique
            Player p = new Player(); //player will auto generate a UUID
            p.tryTeleportToLocation(new GPosition(0,0));
            System.out.println(p.getUID());
        }

        Player p = new Player(); //player will auto generate a UUID
        p.setCurrentLocation(new GPosition(100, 200));
        System.out.println(p.getCurrentLocation().getRangeTo(new GPosition(200, 250)));

    }
}
