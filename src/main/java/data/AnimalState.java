package data;

import exception.ConflictDataException;

/**
 * Created by Sora on 2016/9/22.
 */
public class AnimalState implements Cloneable {

    private String animal;
    private int posx = 0;
    private int posy = 0;

    public AnimalState(String animal, int posx, int posy) {
        this.animal = animal;
        this.posx = posx;
        this.posy = posy;
    }

    public AnimalState(AnimalMotion motion)
            throws ConflictDataException {
        if (!motion.isNewComer()) {
            throw new ConflictDataException(
                    "Not a newer animal motion record.");
        }

        this.animal = motion.getAnimal();
        this.posx = motion.getPosX();
        this.posy = motion.getPosY();
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

    public void update(AnimalMotion motion)
            throws ConflictDataException {
        if (motion.isNewComer() ||
                !motion.getAnimal().equals(this.animal) ||
                motion.getPosX() != this.posx ||
                motion.getPosY() != this.posy) {
            throw new ConflictDataException(
                    "Updated data conflict.");
        }

        this.posx += motion.getMovX();
        this.posy += motion.getMovY();
    }

    public AnimalState clone() {
        AnimalState state = null;
        try {
            state = (AnimalState)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return state;
    }

}
