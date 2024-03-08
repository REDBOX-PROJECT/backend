package fx.redbox.entity.boards;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class Inquiry {
    private Long boardId;
    private String inquiryAnswerContent;
    private Timestamp registrationDate; //YYYY-MM-DD HH:MI:SS

    private String title;
}
