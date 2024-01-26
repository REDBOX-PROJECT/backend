package fx.redbox.entity.boards;

import fx.redbox.entity.enums.BoardType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Board {
    private Long boardId;
    private String title;
    private String content;
    private Timestamp registrationDate; //YYYY-MM-DD HH:MI:SS
    private BoardType boardType;
    private Long userId;

    public Board(String title, String content,
                 Timestamp registrationDate, BoardType boardType, Long userId) {

        this.title = title;
        this.content = content;
        this.registrationDate = registrationDate;
        this.boardType = boardType;
        this.userId = userId;
    }
}
