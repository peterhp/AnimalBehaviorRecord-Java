package exception;

/**
 * Created by Sora on 2016/9/22.
 */
public class ConflictDataException extends Exception {

    public ConflictDataException() {
        super("Conflict data.");
    }

    public ConflictDataException(String errInfo) {
        super(errInfo);
    }
}
