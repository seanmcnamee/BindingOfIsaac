package app.game.gamefield.elements;

/**
 * Destroyable
 */
public interface Destroyable {
    public int maxHealth = 0, health = 3; // A full heart is 2. A half heart is 1

    public boolean damage(int damage);

    public boolean isDead();
}