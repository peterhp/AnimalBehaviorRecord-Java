package data;

/**
 * Created by Sora on 2016/9/22.
 */
public class AnimalMotion {

    public enum MotionType {
        ENTER,
        MOVE
    }

    private String animal;

    private MotionType type = MotionType.ENTER;

    private int posx = 0;
    private int posy = 0;

    private int movx = 0;
    private int movy = 0;

    public AnimalMotion(String animal, int posx, int posy) {
        this.animal = animal;
        this.posx = posx;
        this.posy = posy;
        this.type = MotionType.ENTER;
    }

    public AnimalMotion(String animal,
            int posx, int posy, int movx, int movy) {
        this.animal = animal;
        this.posx = posx;
        this.posy = posy;
        this.movx = movx;
        this.movy = movy;
        this.type = MotionType.MOVE;
    }

    public boolean isNewComer() {
        return (type == MotionType.ENTER);
    }

    public String getAnimal() {
        return animal;
    }

    public int getPosX() {
        return posx;
    }

    public int getPosY() {
        return posy;
    }

    public int getMovX() {
        return movx;
    }

    public int getMovY() {
        return movy;
    }
}
