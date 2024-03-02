package fx.redbox.controller.api;

public enum BoardResponseMessage {

    NOT_FOUND_BOARD("게시글을 불러올 수 없습니다."),
    CREATED_INQUIRY_BOARD("문의 게시글이 저장되었습니다."),
    CREATED_NOTICE_BOARD("공지사항 게시글이 저장되었습니다."),
    SUCCESS_SHOW_INQUIRY_LIST("문의게시글 목록 불러오기 성공");


    private final String message;

    BoardResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
