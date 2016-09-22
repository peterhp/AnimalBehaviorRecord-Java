package data;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sora on 2016/9/21.
 */
public class Record {

    private String id;
    private Date date;

    private Map<String, AnimalMotion>
            motionMap = new HashMap<>();

    public Record(String id, Date date) {
        this.id = id;
        this.date = date;
    }

    public boolean addMotion(AnimalMotion motion) {
        boolean added = false;
        if (!motionMap.containsKey(motion.getAnimal())) {
            motionMap.put(motion.getAnimal(), motion);
            added = true;
        }
        return added;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getFormatDate(String pattern) {
        SimpleDateFormat dateFmt =
                new SimpleDateFormat(pattern);
        return dateFmt.format(date);
    }

    public AnimalMotion getMotion(String animal) {
        return motionMap.get(animal);
    }

    public List<AnimalMotion> getAllMotion() {
        return new LinkedList<>(motionMap.values());
    }
}
