package fx.redbox.controller.board.form;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class NoticeListForm {

    private String title;
    private Timestamp registrationDate;
    //조회수는 나중에 추가하는걸로


}
