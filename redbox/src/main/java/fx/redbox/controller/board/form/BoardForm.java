package fx.redbox.controller.board.form;

import fx.redbox.entity.enums.BoardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardForm {

    private String content;
    private String title;
    private BoardType boardType;
    private Long userId;
}
