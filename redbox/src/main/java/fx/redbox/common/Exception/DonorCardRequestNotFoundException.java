package fx.redbox.common.Exception;

public class DonorCardRequestNotFoundException extends RuntimeException{

    public DonorCardRequestNotFoundException() {
        super();
    }

    public DonorCardRequestNotFoundException(String message) {
        super(message);
    }

    public DonorCardRequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DonorCardRequestNotFoundException(Throwable cause) {
        super(cause);
    }

    protected DonorCardRequestNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
