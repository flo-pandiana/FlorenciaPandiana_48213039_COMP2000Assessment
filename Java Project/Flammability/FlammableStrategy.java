package Flammability;
public abstract interface FlammableStrategy {
    public default boolean canBurn(){
        return false;
    };
    public default boolean isBurnedOut(){
        return false;
    };
    public default boolean isBurning(){
        return false;
    }
    public void burn(int intensity, float dampener);
    public void ignite();
    public int getFuel();
    public int getBurnRate();
}