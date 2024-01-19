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


}
