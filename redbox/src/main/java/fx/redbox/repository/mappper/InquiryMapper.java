package fx.redbox.repository.mappper;

import fx.redbox.entity.boards.Inquiry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class InquiryMapper implements RowMapper<Inquiry> {

    @Value("${inquiry.column.board_id}") private String boardId;
    @Value("${inquiry.column.inquiry_answer_content}") private String inquiryAnswerContent;
    @Value("${inquiry.column.registration_date}") private String registrationDate;
    @Value("${inquiry.column.title}") private String title;

    @Override
    public Inquiry mapRow(ResultSet rs, int rowNum) throws SQLException {
        Inquiry inquiry = Inquiry.builder()
                .boardId(rs.getLong(boardId))
                .inquiryAnswerContent(rs.getString(inquiryAnswerContent))
                .registrationDate(rs.getTimestamp(registrationDate).toLocalDateTime())
                .title(rs.getString(title))
                .build();

        return inquiry;
    }
}