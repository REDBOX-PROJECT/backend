package fx.redbox.entity.boards;

import fx.redbox.entity.enums.BoardType;
import fx.redbox.entity.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Builder
public class Board {
    private Long boardId;
    private String title;
    private String content;
    private Timestamp registrationDate; //YYYY-MM-DD HH:MI:SS
    private BoardType boardType;

    private Long userId;

    // OneToMany
    private Inquiry inquiry;
    private Notice notice;

}
