package fx.redbox.common.Exception;

public class DonorCardRequestExhaustedException extends RuntimeException {
    public DonorCardRequestExhaustedException() {
    }

    public DonorCardRequestExhaustedException(String message) {
        super(message);
    }

    public DonorCardRequestExhaustedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DonorCardRequestExhaustedException(Throwable cause) {
        super(cause);
    }

    public DonorCardRequestExhaustedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
