package fx.redbox.controller.board.form;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class InquiryListForm {
    private String title;
    private boolean hasAnswer;
    private Timestamp registrationDate;

    // 생성자, getter, setter 등 필요한 메서드 추가

    // 예시로 생성자와 getter를 추가한 코드
    public InquiryListForm(String title, boolean hasAnswer, Timestamp registrationDate) {
        this.title = title;
        this.hasAnswer = hasAnswer;
        this.registrationDate = registrationDate;
    }
}