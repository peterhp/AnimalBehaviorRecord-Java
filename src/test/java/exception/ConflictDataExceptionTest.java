package exception;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Sora on 2016/9/22.
 */
public class ConflictDataExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_catch_conflict_data_exception()
            throws Exception {
        thrown.expect(ConflictDataException.class);

        throw new ConflictDataException();
    }

    @Test
    public void should_catch_right_exception_message()
            throws Exception {
        thrown.expect(ConflictDataException.class);
        thrown.expectMessage("Conflict data message.");

        throw new ConflictDataException(
                "Conflict data message.");
    }
}