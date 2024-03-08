package fx.redbox.repository.mappper;

import fx.redbox.entity.boards.Inquiry;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InquiryMapper implements RowMapper<Inquiry> {

    @Override
    public Inquiry mapRow(ResultSet rs, int rowNum) throws SQLException {
        Inquiry inquiry = Inquiry.builder()
                .boardId(rs.getLong("board_id"))
                .inquiryAnswerContent(rs.getString("inquiry_answer_content"))
                .registrationDate(rs.getTimestamp("registration_date"))
                .title(rs.getString("title"))
                .build();

        return inquiry;
    }

}