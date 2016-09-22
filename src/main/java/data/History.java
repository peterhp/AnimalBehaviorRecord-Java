package data;

import exception.ConflictDataException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sora on 2016/9/22.
 */
public class History {

    private Map<String, State> stateMap = new HashMap<>();
    private List<String> idList = new LinkedList<>();

    public void add(Record record)
            throws ConflictDataException {
        if (stateMap.containsKey(record.getId())) {
            throw new ConflictDataException(
                    "Repeated record id.");
        }

        State curState;
        if (idList.isEmpty()) {
            curState = new State(record);
        } else {
            State lastState = stateMap.get(
                    idList.get(idList.size() - 1));
            curState = lastState.clone();
            curState.update(record);
        }

        stateMap.put(record.getId(), curState);
        idList.add(record.getId());
    }

    public State getState(String id) {
        return stateMap.get(id);
    }

    public List<State> getStateSeq() {
        List<State> stateList = new LinkedList<>();
        for (String id : idList) {
            stateList.add(stateMap.get(id));
        }
        return stateList;
    }

}
