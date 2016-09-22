package data;

import exception.ConflictDataException;

import java.util.*;

/**
 * Created by Sora on 2016/9/22.
 */
public class State {

    private Date date;
    private Map<String, AnimalState>
            aniStateMap = new HashMap<>();

    public State(Date date) {
        this.date = date;
    }

    public State(Record record)
            throws ConflictDataException {
        this.date = record.getDate();
        for (AnimalMotion aniMotion : record.getAllMotion()) {
            AnimalState aniState = new AnimalState(aniMotion);
            aniStateMap.put(aniState.getAnimal(), aniState);
        }
    }

    public State clone() {
        State state = new State(date);
        for (AnimalState aniState : aniStateMap.values()) {
            AnimalState copyState = aniState.clone();
            state.aniStateMap.put(
                    copyState.getAnimal(), copyState);
        }
        return state;
    }

    public Date getDate() {
        return date;
    }

    public List<AnimalState> getAllAniState() {
        return new LinkedList<>(aniStateMap.values());
    }

    public AnimalState getAniState(String animal) {
        return aniStateMap.get(animal);
    }

    public void update(Record record)
            throws ConflictDataException {
        if (date.compareTo(record.getDate()) >= 0) {
            throw new ConflictDataException(
                    "Update former record.");
        }
        date = record.getDate();

        for (AnimalMotion aniMotion : record.getAllMotion()) {
            String animal = aniMotion.getAnimal();
            if (aniStateMap.containsKey(animal)) {
                AnimalState aniState = aniStateMap.get(animal);
                aniState.update(aniMotion);
                aniStateMap.put(animal, aniState);
            } else {
                AnimalState aniState = new AnimalState(aniMotion);
                aniStateMap.put(animal, aniState);
            }
        }
    }

}
