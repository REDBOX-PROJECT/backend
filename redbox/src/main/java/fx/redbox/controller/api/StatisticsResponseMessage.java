package fx.redbox.controller.api;

public enum StatisticsResponseMessage {

    STATISTICS_SUCCESS("통계 성공");

    private final String message;

    StatisticsResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
