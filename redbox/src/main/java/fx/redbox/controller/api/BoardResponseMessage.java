package fx.redbox.controller.api;

public enum BoardResponseMessage {

    NOT_FOUND_BOARD("게시글을 불러올 수 없습니다.", 404),
    CREATED_INQUIRY_BOARD("문의 게시글이 저장되었습니다.", 200),
    CREATED_NOTICE_BOARD("공지사항 게시글이 저장되었습니다.", 200),
    SUCCESS_SHOW_INQUIRY_LIST("문의게시글 목록 불러오기 성공", 200),
    CREATED_INQUIRY_ANSWER_BOARD("문의에 대한 답변이 저장되었습니다.", 200);


    private final String message;
    private final int statusCode;

    BoardResponseMessage(String message, int statusCode) {
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
