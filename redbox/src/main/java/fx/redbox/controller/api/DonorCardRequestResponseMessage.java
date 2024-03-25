package fx.redbox.controller.api;

public enum DonorCardRequestResponseMessage {
    DONOR_CARD_REQUEST_SUCCESS("헌혈증 요청 생성 성공", 200),
    DONOR_CARD_REQUEST_LIST_SUCCESS("헌혈증 요청 전체 조회 성공", 200),
    DONOR_CARD_REQUEST_SELECT_SUCCESS("헌혈증 요청서 단건 조회 성공", 200),
    DONOR_CARD_REQUEST_UPDATE_SUCCESS("헌혈증 요청 심사 업데이트 성공", 200),

    DONOR_CARD_REQUEST_DUPLICATE("이미 등록된 요청", 400),
    DONOR_CARD_REQUEST_NOT_FOUND("헌혈증 요청 자료 없음", 404),
    DONOR_CARD_REQUEST_EXHAUSTED("레드박스 헌혈증이 없습니다.", 404);

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
