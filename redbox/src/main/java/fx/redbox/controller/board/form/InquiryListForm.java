package fx.redbox.controller.board.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InquiryListForm {
    private String title;
    private boolean hasAnswer;
    private LocalDateTime registrationDate;
    private Long boardId;

}