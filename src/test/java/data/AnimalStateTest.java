package data;

import exception.ConflictDataException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Sora on 2016/9/22.
 */
public class AnimalStateTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_create_state_for_entered_motion_record()
            throws Exception {
        AnimalMotion aniEnter = new AnimalMotion("cat", 10, 9);
        AnimalState aniState = new AnimalState(aniEnter);

        assertThat(aniState.getAnimal(), is("cat"));
        assertThat(aniState.getPosX(), is(10));
        assertThat(aniState.getPosY(), is(9));
    }

    @Test
    public void should_catch_exception_while_create_state_for_moved_record()
            throws Exception {
        thrown.expect(ConflictDataException.class);

        AnimalMotion aniMove = new AnimalMotion("cat", 10, 9, 2, -1);
        AnimalState aniState = new AnimalState(aniMove);
    }

    @Test
    public void should_update_state_for_moved_record()
            throws Exception {
        AnimalState aniState = new AnimalState("cat", 10, 9);
        AnimalMotion aniMove = new AnimalMotion("cat", 10, 9, 2, -1);
        aniState.update(aniMove);

        assertThat(aniState.getPosX(), is(12));
        assertThat(aniState.getPosY(), is(8));
    }

    @Test
    public void should_catch_exception_while_update_conflict_record()
            throws Exception {
        AnimalState aniState = new AnimalState("cat", 10, 9);
        AnimalMotion aniMove = new AnimalMotion("cat", 11, 9, 2, -1);

        thrown.expect(ConflictDataException.class);

        aniState.update(aniMove);
    }

    @Test
    public void should_copy_another_state_based_on_old_one()
            throws Exception {
        AnimalState oldState = new AnimalState("cat", 10, 9);
        AnimalState newState = oldState.clone();

        assertThat(newState == oldState, is(false));
        assertThat(newState.getAnimal(), is("cat"));
        assertThat(newState.getPosX(), is(10));
        assertThat(newState.getPosY(), is(9));
    }
}