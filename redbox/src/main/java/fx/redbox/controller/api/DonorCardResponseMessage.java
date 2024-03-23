package fx.redbox.controller.api;

public enum DonorCardResponseMessage {
    CREATED_DONORCARD("헌혈증 생성 성공", 200),
    DUPLICATE_DONORCARD("이미 등록된 헌혈증", 404),
    READ_DONORCARD("헌혈증 단건 조회 성공", 200),
    READ_ALL_DONORCARD("헌혈증 전체 조회 성공", 200),
    NOT_FOUND_DONORCARD("헌혈증 정보를 찾을 수 없습니다.", 404),
    READ_REDBOX_DASHBOARD("REDBOX 대시보드 조회 성공", 200),

    INTERNAL_SERVER_ERROR("서버 내부 에러", 500),
    DB_ERROR("데이터베이스 에러", 500);

    private final String message;
    private final int statusCode;

    DonorCardResponseMessage(String message, int statusCode) {
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
