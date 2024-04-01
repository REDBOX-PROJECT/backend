package fx.redbox.repository.mappper;

import fx.redbox.entity.boards.Inquiry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class InquiryMapper implements RowMapper<Inquiry> {

    @Override
    public Inquiry mapRow(ResultSet rs, int rowNum) throws SQLException {
        Inquiry inquiry = Inquiry.builder()
                .boardId(rs.getLong("board_id"))
                .inquiryAnswerContent(rs.getString("inquiry_answer_content"))
                .registrationDate(rs.getTimestamp("registration_date").toLocalDateTime())
                .title(rs.getString("title"))
                .build();

        return inquiry;
    }
}