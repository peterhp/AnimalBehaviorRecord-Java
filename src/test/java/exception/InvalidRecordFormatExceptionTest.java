package exception;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Sora on 2016/9/21.
 */
public class InvalidRecordFormatExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_catch_invalid_record_format_exception()
            throws Exception {
        thrown.expect(InvalidRecordFormatException.class);

        throw new InvalidRecordFormatException();
    }

    @Test
    public void should_catch_right_exception_message()
            throws Exception {
        thrown.expect(InvalidRecordFormatException.class);
        thrown.expectMessage("Invalid format message.");

        throw new InvalidRecordFormatException(
                "Invalid format message.");
    }
}