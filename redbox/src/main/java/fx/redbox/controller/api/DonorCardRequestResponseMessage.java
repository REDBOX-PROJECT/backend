package fx.redbox.controller.api;

public enum DonorCardRequestResponseMessage {
    CREATED_DONORCARD_REQUEST("헌혈증 요청 생성 성공"),
    READ_DONORCARD_REQUEST("헌혈증 요청 조회 성공"),
    UPDATE_DONORCARD_REQUEST("헌혈증 요청 상태 수정 성공"),
    UPDATE_DONORCARD_REQUEST_FORM("헌혈증 요청서 수정 성공"),
    DELETE_DONORCARD_REQUEST("헌혈증 요청 삭제 성공"),
    NOT_FOUND_DONORCARD_REQUEST("헌혈증 요청 정보를 찾을 수 없습니다."),
    NOT_FOUND_DONORCARD_REQUEST_FORM("헌혈증 요청서를 찾을 수 없습니다."),

    INTERNAL_SERVER_ERROR("서버 내부 에러"),
    DB_ERROR("데이터베이스 에러");

    private final String message;

    DonorCardRequestResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
