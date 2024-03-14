package fx.redbox.common.Exception;

public class DuplicateDonorCardRequestException extends RuntimeException{
    public DuplicateDonorCardRequestException() {
        super();
    }

    public DuplicateDonorCardRequestException(String message) {
        super(message);
    }

    public DuplicateDonorCardRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateDonorCardRequestException(Throwable cause) {
        super(cause);
    }

    protected DuplicateDonorCardRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
