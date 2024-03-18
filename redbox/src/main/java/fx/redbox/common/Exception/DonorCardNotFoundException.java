package fx.redbox.common.Exception;

public class DonorCardNotFoundException extends RuntimeException{
    public DonorCardNotFoundException() {
        super();
    }

    public DonorCardNotFoundException(String message) {
        super(message);
    }

    public DonorCardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DonorCardNotFoundException(Throwable cause) {
        super(cause);
    }

    protected DonorCardNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
