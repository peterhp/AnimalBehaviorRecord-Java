package parser;

import data.Record;
import exception.InvalidRecordFormatException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Sora on 2016/9/21.
 */
public class RecordParserTest {

    private RecordParser parser = new RecordParser();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_throw_exception_for_wrong_id_format()
            throws Exception {
        String errRecord = "351055db-33e6-4f9b bfe1-16f1ac446ac1\n" +
                "2016/09/02 22:30:52\n" +
                "cat1 10 9 2 -1\n" +
                "cat2 2 3";

        thrown.expect(InvalidRecordFormatException.class);

        parser.parse(errRecord);
    }

    @Test
    public void should_throw_exception_for_wrong_date_format()
            throws Exception {
        String errRecord = "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n" +
                "2016/09/02 22:30\n" +
                "cat1 10 9 2 -1\n" +
                "cat2 2 3";

        thrown.expect(InvalidRecordFormatException.class);

        parser.parse(errRecord);
    }

    @Test
    public void should_throw_exception_for_wrong_motion_state_format()
            throws Exception {
        String errRecord = "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n" +
                "2016/09/02 22:30:52\n" +
                "cat1 10 2 -1 \n" +
                "cat2 2 3";

        thrown.expect(InvalidRecordFormatException.class);

        parser.parse(errRecord);
    }

    @Test
    public void should_get_correct_record_info()
            throws Exception {
        String recordInfo = "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n" +
                "2016/09/02 22:30:52\n" +
                "cat1 10 9 2 -1\n" +
                "cat2 2 3";

        Record record = parser.parse(recordInfo);

        assertThat(record.getId(),
                is("351055db-33e6-4f9b-bfe1-16f1ac446ac1"));
        assertThat(record.getFormatDate("yyyy/MM/dd HH:mm:ss"),
                is("2016/09/02 22:30:52"));

        assertThat(record.getAllMotion().size(), is(2));
        assertThat(record.getMotion("cat1").isNewComer(), is(false));
        assertThat(record.getMotion("cat1").getMovY(), is(-1));
        assertThat(record.getMotion("cat2").isNewComer(), is(true));
        assertThat(record.getMotion("cat2").getPosX(), is(2));
    }
}