import data.AnimalState;
import data.History;
import data.State;
import parser.HistoryParser;
import util.TextFile;

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
        for (int i = 0; i < aniStateList.size(); ++i) {
            AnimalState aniState = aniStateList.get(i);
            msg += String.format("%s %d %d", aniState.getAnimal(),
                    aniState.getPosX(), aniState.getPosY());
            if (i < aniStateList.size() - 1) {
                msg += "\n";
            }
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

    public String readResFile(String res) {
        TextFile textFile = new TextFile();
        String path = textFile.getResPath(res);
        return textFile.readFromTextFile(path);
    }

    public static void main(String[] args) {
        HistoricalReview review = new HistoricalReview();

        String invalidData = review.readResFile(
                "history_invalid.txt");
        System.out.println(review.getSnapshot(invalidData,
                "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155"));

        String errorData = review.readResFile(
                "history_error.txt");
        System.out.println(review.getSnapshot(errorData,
                "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155"));

        String data = review.readResFile("history.txt");
        System.out.println(review.getSnapshot(data,
                "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155"));
    }

}
