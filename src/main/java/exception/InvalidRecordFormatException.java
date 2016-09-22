package exception;

/**
 * Created by Sora on 2016/9/21.
 */
public class InvalidRecordFormatException extends Exception {

    public InvalidRecordFormatException() {
        super("Invalid format.");
    }

    public InvalidRecordFormatException(String errInfo) {
        super(errInfo);
    }
}
