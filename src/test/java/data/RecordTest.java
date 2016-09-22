package data;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Sora on 2016/9/22.
 */
public class RecordTest {

    @Test
    public void should_store_right_record_info()
            throws Exception {
        String id = "351055db-33e6-4f9b-bfe1-16f1ac446ac1";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.SEPTEMBER, 2, 22, 30, 52);
        Date date = calendar.getTime();
        Record record = new Record(id, date);

        AnimalMotion motion1 =
                new AnimalMotion("cat1", 10, 9, 2, -1);
        AnimalMotion motion2 = new AnimalMotion("cat2", 2, 3);
        record.addMotion(motion1);
        record.addMotion(motion2);

        assertThat(record.getId(), is(id));
        assertThat(record.getDate(), is(date));
        assertThat(record.getFormatDate("yyyy/MM/dd HH:mm:ss"),
                is("2016/09/02 22:30:52"));

        assertThat(record.getAllMotion().size(), is(2));
        assertThat(record.getMotion(motion1.getAnimal()),
                is(motion1));
        assertThat(record.getMotion(motion2.getAnimal()),
                is(motion2));
    }
}