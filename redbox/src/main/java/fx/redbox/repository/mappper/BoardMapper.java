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

    @Value("${board.column.board_id}") private String boardId;
    @Value("${board.column.title}") private String title;
    @Value("${board.column.content}") private String content;
    @Value("${board.column.registration_date}") private String registrationDate;
    @Value("${board.column.board_type}") private String boardType;
    @Value("${board.column.user_id}") private String userId;

    @Override
    public Board mapRow(ResultSet rs, int rowNum) throws SQLException {

        Board board = Board.builder()
                .boardId(rs.getLong(boardId))
                .title(rs.getString(title))
                .content(rs.getString(content))
                .registrationDate(rs.getTimestamp(registrationDate).toLocalDateTime())
                .boardType(BoardType.valueOf(rs.getString(boardType)))
                .userId(rs.getLong(userId))
                .build();

        return board;
    }
}