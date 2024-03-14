package fx.redbox.entity.boards;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Inquiry {
    private Long boardId;
    private String inquiryAnswerContent;
    private LocalDateTime registrationDate; //YYYY-MM-DD HH:MI:SS

    private String title;
}
