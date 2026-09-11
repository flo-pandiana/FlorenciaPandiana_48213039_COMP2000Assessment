public class Fire {

    private int intensity;
    private int ticksBurning = 0;

    public Fire(int i){
        intensity = i;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int amount){
        intensity += amount;
    }

    public void setIntensityAbsolute(int value) {
        intensity = value;
    }

    public void incrementTicksBurning() {
        ticksBurning++;
    }

    public int getTicksBurning() {
        return ticksBurning;
    }

    public Boolean isExtinguished(){
        if(intensity <= 0){
            return true;
        }
        else{
            return false;
        }
    }
}