package fx.redbox.controller.api;

public enum DonorCardRequestResponseMessage {
    DONOR_CARD_REQUEST_SUCCESS("헌혈증 요청 성공", 200),

    DONOR_CARD_REQUEST_DUPLICATE("이미 등록된 요청", 400),
    DONOR_CARD_REQUEST_NOT_FOUND("헌혈증 요청 자료 없음", 404);

    private final String message;
    private final int statusCode;

    DonorCardRequestResponseMessage(String message, int statusCode) {
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
