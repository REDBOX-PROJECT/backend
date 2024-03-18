package fx.redbox.common.Exception;

public class DuplicateCertificateNumberException extends RuntimeException {
    public DuplicateCertificateNumberException() {
        super();
    }

    public DuplicateCertificateNumberException(String message) {
        super(message);
    }

    public DuplicateCertificateNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCertificateNumberException(Throwable cause) {
        super(cause);
    }

    protected DuplicateCertificateNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
