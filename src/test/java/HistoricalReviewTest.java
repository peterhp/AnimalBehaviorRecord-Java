import data.Record;
import data.State;
import org.junit.Test;
import parser.HistoryParser;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Sora on 2016/9/22.
 */
public class HistoricalReviewTest {

    String validData = "e4e87cb2-8e9a-4749-abb6-26c59344dfee\n" +
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
            "cat1 12 8 3 4";
    String invalidData = "e4e87cb2-8e9a-4749-abb6-26c59344dfee\n" +
            "2016/09/02 22:30:46\n" +
            "cat1 10 9\n" +
            "\n" +
            "351055db-33e6-4f9b-bfe1-16f1ac446ac1\n" +
            "2016/09/02 22:30:52\n" +
            "cat1 10 9 -1\n" +
            "cat2 2 3\n" +
            "\n" +
            "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155\n" +
            "2016/09/02 22:31:02\n" +
            "cat1 12 8 3 4";
    String errorData = "e4e87cb2-8e9a-4749-abb6-26c59344dfee\n" +
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
    String id = "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155";

    HistoricalReview review = new HistoricalReview();

    @Test
    public void should_print_right_state_info()
            throws Exception {
        HistoryParser parser = new HistoryParser();
        List<Record> records = parser.parseToRecord(validData);

        State state = new State(records.get(0));
        assertThat(review.getStateInfo(state),
                is("cat1 10 9"));

        state.update(records.get(1));
        assertThat(review.getStateInfo(state),
                is("cat1 12 8\ncat2 2 3"));

        state.update(records.get(2));
        assertThat(review.getStateInfo(state),
                is("cat1 15 12\ncat2 2 3"));
    }

    @Test
    public void should_print_error_message_for_invalid_data()
            throws Exception {
        String msg = review.getSnapshot(invalidData, id);
        assertThat(msg, is("Invalid format."));
    }

    @Test
    public void should_print_error_message_for_error_data()
            throws Exception {
        String msg = review.getSnapshot(errorData, id);
        assertThat(msg, is("Conflict found at " +
                "dcfa0c7a-5855-4ed2-bc8c-4accae8bd155."));
    }

    @Test
    public void should_print_review_message_for_certain_data()
            throws Exception {
        String msg = review.getSnapshot(validData, id);
        assertThat(msg, is("cat1 15 12\ncat2 2 3"));
    }
}