package fx.redbox.repository.mappper;


import fx.redbox.entity.boards.Board;
import fx.redbox.entity.enums.BoardType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BoardMapper implements RowMapper<Board> {

    @Override
    public Board mapRow(ResultSet rs, int rowNum) throws SQLException {

        Board board = Board.builder()
                .boardId(rs.getLong("board_id"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .registrationDate(rs.getTimestamp("registration_date").toLocalDateTime())
                .boardType(BoardType.valueOf(rs.getString("board_type")))
                .userId(rs.getLong("user_id"))
                .build();

        return board;
    }
}