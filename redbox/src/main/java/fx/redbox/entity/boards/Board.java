package fx.redbox.entity.boards;

import fx.redbox.entity.enums.BoardType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Board {
    private Long boardId;
    private String title;
    private String content;
    private LocalDateTime registrationDate; //YYYY-MM-DD HH:MI:SS
    private BoardType boardType;

    private Long userId;

    // OneToMany
    private Inquiry inquiry;
    private Notice notice;

}
