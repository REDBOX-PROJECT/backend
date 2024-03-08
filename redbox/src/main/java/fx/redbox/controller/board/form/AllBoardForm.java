package fx.redbox.controller.board.form;

import fx.redbox.entity.boards.Board;
import fx.redbox.entity.enums.BoardType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AllBoardForm {

    private Long boardId;
    private String title;
    private String content;
    private Timestamp registrationDate; //YYYY-MM-DD HH:MI:SS
    private BoardType boardType;
    private Long userId;

    public AllBoardForm(Board board) {
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.registrationDate = board.getRegistrationDate();
        this.boardType = board.getBoardType();
        this.userId = board.getUserId();
    }
}
