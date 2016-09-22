package data;

import org.junit.Before;
import org.junit.Test;
import parser.RecordParser;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Sora on 2016/9/22.
 */
public class HistoryTest {

    private Record initRecord;
    private Record nextRecord;

    @Before
    public void setUp() throws Exception {
        String recStr1 = "e4e87cb2-8e9a-4749-abb6-26c59344dfee\n" +
                "2016/09/02 22:30:46\n" +
                "cat1 10 9";
        String recStr2 = "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n" +
                "2016/09/02 22:30:52\n" +
                "cat1 10 9 2 -1\n" +
                "cat2 2 3";
        RecordParser parser = new RecordParser();

        initRecord = parser.parse(recStr1);
        nextRecord = parser.parse(recStr2);
    }

    @Test
    public void should_create_history_based_on_record_sequence()
            throws Exception {
        History history = new History();
        history.add(initRecord);
        history.add(nextRecord);

        List<State> stateSeq = history.getStateSeq();
        assertThat(stateSeq.size(), is(2));
        assertThat(stateSeq.get(0).getAniState("cat1")
                .getPosX(), is(10));
        assertThat(stateSeq.get(1).getAniState("cat1")
                .getPosY(), is(8));
        assertThat(stateSeq.get(1).getAniState("cat2")
                .getPosX(), is(2));
    }
}