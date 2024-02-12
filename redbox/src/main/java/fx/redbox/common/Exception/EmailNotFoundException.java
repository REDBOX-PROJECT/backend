package fx.redbox.common.Exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class EmailNotFoundException extends EmptyResultDataAccessException {

    public EmailNotFoundException(int expectedSize) {
        super(expectedSize);
    }

    public EmailNotFoundException(String msg, int expectedSize) {
        super(msg, expectedSize);
    }

    public EmailNotFoundException(String msg, int expectedSize, Throwable ex) {
        super(msg, expectedSize, ex);
    }
}
