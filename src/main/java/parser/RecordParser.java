package parser;

import data.AnimalMotion;
import data.Record;
import exception.InvalidRecordFormatException;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sora on 2016/9/21.
 */
public class RecordParser {

    public Record parse(String info)
            throws InvalidRecordFormatException {
        String[] fields = info.split("\n");
        if (fields.length < 3) {
            throw new InvalidRecordFormatException(
                    "No enough fields in record string.");
        }

        String id = parseId(fields[0].trim());
        Date date = parseDate(fields[1].trim());

        Record record = new Record(id, date);
        for (int index = 2; index < fields.length; ++index) {
            AnimalMotion motion = parseMotion(fields[index].trim());
            if (!record.addMotion(motion)) {
                throw new InvalidRecordFormatException(
                        "Repeated motion records.");
            }
        }

        return record;
    }

    private String parseId(String info)
            throws InvalidRecordFormatException {
        if (info.isEmpty() || info.contains(" ")) {
            throw new InvalidRecordFormatException(
                    "Invalid record id.");
        }
        return info;
    }

    private Date parseDate(String info)
            throws InvalidRecordFormatException {
        String datePattern = "yyyy/MM/dd HH:mm:ss";
        if (info.length() != datePattern.length()) {
            throw new InvalidRecordFormatException(
                    "Invalid date format.");
        }

        SimpleDateFormat dateFmt = new SimpleDateFormat(datePattern);
        ParsePosition pos = new ParsePosition(0);
        Date date = dateFmt.parse(info, pos);
        if (date == null) {
            throw new InvalidRecordFormatException(
                    "Invalid date format.");
        }
        return date;
    }

    private AnimalMotion parseMotion(String info)
            throws InvalidRecordFormatException {
        String[] fields = info.split(" ");
        if (fields.length != 3 && fields.length != 5) {
            throw new InvalidRecordFormatException(
                    "Invalid motion record format.");
        }

        String animal = fields[0].trim();
        int posx, posy;
        int movx, movy;

        try {
            posx = Integer.valueOf(fields[1].trim());
            posy = Integer.valueOf(fields[2].trim());
        } catch (NumberFormatException e) {
            throw new InvalidRecordFormatException(
                    "Invalid motion record format.");
        }
        if (fields.length == 3) {
            return new AnimalMotion(animal, posx, posy);
        }

        try {
            movx = Integer.valueOf(fields[3].trim());
            movy = Integer.valueOf(fields[4].trim());
        } catch (NumberFormatException e) {
            throw new InvalidRecordFormatException(
                    "Invalid motion record format.");
        }
        return new AnimalMotion(animal, posx, posy, movx, movy);
    }

}
