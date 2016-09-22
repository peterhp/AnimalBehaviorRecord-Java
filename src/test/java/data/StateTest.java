package data;

import exception.ConflictDataException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import parser.RecordParser;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Sora on 2016/9/22.
 */
public class StateTest {

    private Record initRecord;
    private Record nextRecord;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void should_create_state_for_init_record()
            throws Exception {
        State state = new State(initRecord);
        List<AnimalState> aniStates = state.getAllAniState();

        assertThat(aniStates.size(), is(1));
        assertThat(aniStates.get(0).getPosX(), is(10));
        assertThat(aniStates.get(0).getPosY(), is(9));
    }

    @Test
    public void should_catch_exception_while_create_state_for_next_record()
            throws Exception {
        thrown.expect(ConflictDataException.class);

        State state = new State(nextRecord);
    }

    @Test
    public void should_update_for_next_record()
            throws Exception {
        State state = new State(initRecord);
        state.update(nextRecord);

        assertThat(state.getAllAniState().size(), is(2));
        assertThat(state.getAniState("cat1").getPosX(), is(12));
        assertThat(state.getAniState("cat2").getPosY(), is(3));
    }

    @Test
    public void should_clone_another_instance_from_old_one()
            throws Exception {
        State state = new State(initRecord);
        State copyState = state.clone();

        assertThat(copyState == state, is(false));
        assertThat(copyState.getAniState("cat1") ==
                state.getAniState("cat1"), is(false));
        assertThat(copyState.getAniState("cat1").getPosX(), is(10));
    }
}