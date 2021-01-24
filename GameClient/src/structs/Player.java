package structs;

public class Player extends Entity {

    private boolean canAttack;

    public Player() { //probably add spawn location etc
        super(EntityType.PLAYER_ENTITY); //generate default player
        this.setAttackSpeed(999);
        this.setDamage(1000);
        this.setMovable(true);
        this.setVisible(true);
        this.setCollision(true);
        this.canAttack = true;
    }

    public void tryAttack() { //attack stuff here ettc etc
        if (canAttack) {
            System.out.println("a attack");
        }
        else {
            System.out.println("cannot attack");
        }
    }
}
