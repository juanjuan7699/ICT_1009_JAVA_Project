package structs;

import enums.BulletType;
import enums.TraceType;
import maths.GVector;
import rendering.Renderer;
import rendering.Texture;

public class Bullet extends Entity {
    private BulletType bulletType;
    private TraceType traceType;

    private float finalDamage; //this.damage from Entity + (instigator)player.damage only when shooting
    private float radius; //only for non TraceType.SINGLE traces
    private GVector velocity; //speed of non hitscan/laser bullets
    private float damageOverTime; // >1 if you want to deal baseDamage/damageOverTime damage every second instead of instant

    private boolean activateOnCollision; //activates on collision with another object
    private boolean activateAfterRange; //or/and activate once its reached its max range
    private boolean hitsAllies; //something like ana's heal/damage gun from overwatch

    public Bullet() {
        super(EntityType.BULLET_ENTITY);
        this.setSprite(Texture.loadTexture("resources/bulletTest.png"));
        System.out.println("bullet created");
    } //base bullet, can extend to be maybe missles, grenades, etc

    public void tryHit(Entity instigator, Entity target) { //on collision do this
        if (target.getEntityType() != EntityType.ANIMAL_ENTITY || target.getEntityType() != EntityType.PLAYER_ENTITY) {
            //deal damage here
            this.finalDamage = this.getDamage() + instigator.getDamage();
            target.updateHealth(-this.finalDamage);
            //also check if its damageovertime etc
            //check if its an ally, etc
        }
    }

    @Override
    public void render(Renderer renderer) {
        //update the vector of the
        this.setCurrentLocation(this.getCurrentLocation().add(this.getVelocity()));
        super.render(renderer);

    }

    //TODO: for cloning the same bullet everytime the player shoots
    public Bullet shootAgain() {
        return this; //change to cloned instead
    }

    public BulletType getBulletType() {
        return bulletType;
    }

    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
    }

    public TraceType getTraceType() {
        return traceType;
    }

    public void setTraceType(TraceType traceType) {
        this.traceType = traceType;
    }

    public float getFinalDamage() {
        return finalDamage;
    }

    public void setFinalDamage(float finalDamage) {
        this.finalDamage = finalDamage;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public GVector getVelocity() {
        return velocity;
    }

    public void setVelocity(GVector velocity) {
        this.velocity = velocity;
    }

    public float getDamageOverTime() {
        return damageOverTime;
    }

    public void setDamageOverTime(float damageOverTime) {
        this.damageOverTime = damageOverTime;
    }

    public boolean isActivateOnCollision() {
        return activateOnCollision;
    }

    public void setActivateOnCollision(boolean activateOnCollision) {
        this.activateOnCollision = activateOnCollision;
    }

    public boolean isActivateAfterRange() {
        return activateAfterRange;
    }

    public void setActivateAfterRange(boolean activateAfterRange) {
        this.activateAfterRange = activateAfterRange;
    }

    public boolean isHitsAllies() {
        return hitsAllies;
    }

    public void setHitsAllies(boolean hitsAllies) {
        this.hitsAllies = hitsAllies;
    }
}
