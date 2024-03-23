package fx.redbox.controller.api;

public enum RedboxResponseMessage {

    SUCCESS_REDBOX_GIVE("레드박스 기부 성공", 200);




    private final String message;
    private final int statusCode;

    RedboxResponseMessage(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }
    public int getStatusCode() {
        return statusCode;
    }

    }
