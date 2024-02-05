package fx.redbox.controller.api;

public enum UserResponseMessage {
    LOGIN_SUCCESS("로그인 성공"),
    LOGIN_FAIL("로그인 실패"),
    PASSWORD_MISMATCH("패스워드가 일치하지 않습니다."),
    READ_USER("회원 정보 조회 성공"),
    NOT_FOUND_USER("회원을 찾을 수 없습니다."),
    NOT_FOUND_EMAIL("이메일을 찾을 수 없습니다."),

    CREATED_USER("회원 가입 성공"),
    FAIL_CREATED_USER("회원 가입 실패"),
    UPDATE_USER("회원 정보 수정 성공"),
    DELETE_USER("회원 탈퇴 성공"),
    INTERNAL_SERVER_ERROR("서버 내부 에러"),
    DB_ERROR("데이터베이스 에러");

    private final String message;

    UserResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}