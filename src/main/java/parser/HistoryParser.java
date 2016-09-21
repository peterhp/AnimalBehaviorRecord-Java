package parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sora on 2016/9/21.
 */
public class HistoryParser {

    public List<String> splitToRecord(String history) {
        history = history.replace("\r\n", "\n");
        history = history.replace("\r", "\n");

        List<String> recordList = new ArrayList<>();

        String[] records = history.split("\n\n");
        for (String record : records) {
            record = record.trim();
            if (!record.isEmpty()) {
                recordList.add(record);
            }
        }

        return recordList;
    }

}
