package parser;

import data.History;
import data.Record;
import exception.ConflictDataException;
import exception.InvalidRecordFormatException;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Sora on 2016/9/21.
 */
public class HistoryParser {

    public List<String> splitToRecordString(String history) {
        history = history.replace("\r\n", "\n");
        history = history.replace("\r", "\n");

        List<String> recordList = new LinkedList<>();

        String[] records = history.split("\n\n");
        for (String record : records) {
            record = record.trim();
            if (!record.isEmpty()) {
                recordList.add(record);
            }
        }

        return recordList;
    }

    public List<Record> parseToRecord(String history)
            throws InvalidRecordFormatException {
        List<Record> records = new LinkedList<>();

        List<String> recordInfos = splitToRecordString(history);
        RecordParser parser = new RecordParser();
        for (String recordInfo : recordInfos) {
            Record record = parser.parse(recordInfo);
            records.add(record);
        }

        return records;
    }

    public History parse(String histData) throws Exception {
        List<Record> records;
        try {
            records = parseToRecord(histData);
        } catch (InvalidRecordFormatException e) {
            throw new Exception("Invalid format.");
        }

        History history = new History();
        for (Record record : records) {
            try {
                history.add(record);
            } catch (ConflictDataException e) {
                throw new Exception("Conflict found at " +
                        record.getId() + ".");
            }
        }

        return history;
    }
}
