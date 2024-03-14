package fx.redbox.controller.api;

public enum UserResponseMessage {
    LOGIN_SUCCESS("로그인 성공", 200),
    LOGIN_FAIL("로그인 실패", 404),
    LOGOUT_SUCCESS("로그아웃 성공", 200),
    LOGOUT_FAIL("로그아웃 실패", 404),

    PASSWORD_MISMATCH("패스워드가 일치하지 않습니다.", 404),
    READ_USER("회원 정보 조회 성공", 200),
    NOT_FOUND_USER("회원을 찾을 수 없습니다.", 404),
    NOT_FOUND_EMAIL("이메일을 찾을 수 없습니다.", 404),

    CREATED_USER("회원 가입 성공", 201),
    FAIL_CREATED_USER("회원 가입 실패", 404),
    UPDATE_USER("회원 정보 수정 성공", 200),
    DELETE_USER("회원 탈퇴 성공", 200),
    INTERNAL_SERVER_ERROR("서버 내부 에러", 500),
    DB_ERROR("데이터베이스 에러", 500);

    private final String message;
    private final int statusCode;

    UserResponseMessage(String message, int statusCode) {
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