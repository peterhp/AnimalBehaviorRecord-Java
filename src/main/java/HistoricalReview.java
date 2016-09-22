import data.AnimalState;
import data.History;
import data.State;
import parser.HistoryParser;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Sora on 2016/9/22.
 */
public class HistoricalReview {

    public String getStateInfo(State state) {
        List<AnimalState> aniStateList = state.getAllAniState();
        Collections.sort(aniStateList, new Comparator<AnimalState>() {
            @Override
            public int compare(AnimalState state1, AnimalState state2) {
                return state1.getAnimal().compareTo(state2.getAnimal());
            }
        });

        String msg = "";
        for (AnimalState aniState : aniStateList) {
            msg += String.format("%s %d %d\n", aniState.getAnimal(),
                    aniState.getPosX(), aniState.getPosY());
        }

        return msg;
    }

    public String getSnapshot(String historyData, String id) {
        HistoryParser parser = new HistoryParser();
        History history;

        try {
            history = parser.parse(historyData);
        } catch (Exception e) {
            return e.getMessage();
        }

        State state = history.getState(id);
        if (null == state) {
            return "No record at " + id + ".";
        }

        return getStateInfo(state);
    }

}
