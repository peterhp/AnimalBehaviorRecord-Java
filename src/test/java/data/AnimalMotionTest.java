package data;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Sora on 2016/9/22.
 */
public class AnimalMotionTest {

    @Test
    public void should_store_right_info_for_newcomer_motion_record()
            throws Exception {
        AnimalMotion motion = new AnimalMotion("cat", 10, 9);

        assertThat(motion.isNewComer(), is(true));
        assertThat(motion.getAnimal(), is("cat"));
        assertThat(motion.getPosX(), is(10));
        assertThat(motion.getPosY(), is(9));
    }

    @Test
    public void should_store_right_info_for_motion_record()
            throws Exception {
        AnimalMotion motion = new AnimalMotion("cat", 10, 9, 2, -1);

        assertThat(motion.isNewComer(), is(false));
        assertThat(motion.getPosX(), is(10));
        assertThat(motion.getPosY(), is(9));
        assertThat(motion.getMovX(), is(2));
        assertThat(motion.getMovY(), is(-1));
    }
}