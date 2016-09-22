package parser;

import data.History;
import data.Record;
import data.State;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Sora on 2016/9/21.
 */
public class HistoryParserTest {

    private HistoryParser parser = new HistoryParser();
    private String historyData;
    private String[] records;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        historyData = "\n" +
                "e4e87cb2-8e9a-4749-abb6-26c59344dfee\n" +
                "2016/09/02 22:30:46\n" +
                "cat1 10 9\n" +
                "\n" +
                "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n" +
                "2016/09/02 22:30:52\n" +
                "cat1 10 9 2 -1\n" +
                "cat2 2 3\n" +
                "\n" +
                "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155\n" +
                "2016/09/02 22:31:02\n" +
                "cat1 12 8 3 4\n" +
                "\n";

        records = new String[3];
        records[0] = "e4e87cb2-8e9a-4749-abb6-26c59344dfee\n" +
                "2016/09/02 22:30:46\n" +
                "cat1 10 9";
        records[1] = "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n" +
                "2016/09/02 22:30:52\n" +
                "cat1 10 9 2 -1\n" +
                "cat2 2 3";
        records[2] = "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155\n" +
                "2016/09/02 22:31:02\n" +
                "cat1 12 8 3 4";
    }

    @Test
    public void should_split_history_to_right_records()
            throws Exception {
        List<String> recordList = parser.splitToRecordString(historyData);

        assertThat(recordList.size(), is(3));
        assertThat(recordList.get(0), is(records[0]));
        assertThat(recordList.get(1), is(records[1]));
        assertThat(recordList.get(2), is(records[2]));
    }

    @Test
    public void should_get_records_info_from_history_date()
            throws Exception {
        List<Record> records = parser.parseToRecord(historyData);

        assertThat(records.size(), is(3));
        assertThat(records.get(0).getId(),
                is("e4e87cb2-8e9a-4749-abb6-26c59344dfee"));
        assertThat(records.get(1).getMotion("cat1").isNewComer(), is(false));
        assertThat(records.get(2).getFormatDate("yyyy/MM/dd HH:mm:ss"),
                is("2016/09/02 22:31:02"));
    }

    @Test
    public void should_create_history_based_on_history_data()
            throws Exception {
        History history = parser.parse(historyData);
        List<State> stateSeq = history.getStateSeq();

        assertThat(stateSeq.size(), is(3));
        assertThat(stateSeq.get(0).getAniState("cat1")
                .getPosX(), is(10));
        assertThat(stateSeq.get(1).getAniState("cat1")
                .getPosX(), is(12));
        assertThat(stateSeq.get(1).getAniState("cat2")
                .getPosX(), is(2));
        assertThat(stateSeq.get(2).getAniState("cat1")
                .getPosX(), is(15));
        assertThat(stateSeq.get(2).getAniState("cat2")
                .getPosX(), is(2));
    }

    @Test
    public void should_catch_exception_for_invalid_history_data()
            throws Exception {
        String invalidData = "\n" +
                "e4e87cb2-8e9a-4749-abb6-26c59344dfee\n" +
                "2016/09/02 22:30:46\n" +
                "cat1 10 9\n" +
                "\n" +
                "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n" +
                "2016/09/02 22:30:52\n" +
                "cat1 10 9 2\n" +
                "cat2 2 3\n";

        thrown.expect(Exception.class);
        thrown.expectMessage("Invalid format.");

        parser.parse(invalidData);
    }

    @Test
    public void should_catch_exception_for_error_history_data()
            throws Exception {
        String errData =
                "e4e87cb2-8e9a-4749-abb6-26c59344dfee\n" +
                "2016/09/02 22:30:46\n" +
                "cat1 10 9\n" +
                "\n" +
                "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n" +
                "2016/09/02 22:30:52\n" +
                "cat1 10 9 2 -1\n" +
                "cat2 2 3\n" +
                "\n" +
                "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155\n" +
                "2016/09/02 22:31:02\n" +
                "cat1 11 8 3 4";

        thrown.expect(Exception.class);
        thrown.expectMessage("Conflict found at " +
                "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155.");

        parser.parse(errData);
    }
}