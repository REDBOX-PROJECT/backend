package fx.redbox.repository.board;

import fx.redbox.entity.boards.Board;
import fx.redbox.entity.boards.Inquiry;
import fx.redbox.entity.enums.BoardType;
import fx.redbox.repository.mappper.BoardMapper;
import fx.redbox.repository.mappper.InquiryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BoardMapper boardMapper;

    @Override
    public void saveBoard(Board board) {
        insertBoardData(board);
    }


    @Override
    public Board findByBoardId(Long boardId) {
        String sql = "SELECT * FROM boards WHERE board_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{boardId}, boardMapper);
    }

    @Override
    public void saveInquiryAnswer(Long boardId, String inquiryAnswerContent) {
        String inquirySql = "UPDATE inquiry SET inquiry_answer_content = ?, registration_date = ? WHERE board_id = ?";
        jdbcTemplate.update(inquirySql, inquiryAnswerContent, Timestamp.valueOf(LocalDateTime.now()), boardId);
    }

    @Override
    public List<Inquiry> findAllInquiry() {
        String sql = "SELECT boards.board_id, boards.title, boards.registration_date, inquiry.inquiry_answer_content " +
                "FROM boards boards " +
                "LEFT JOIN inquiry inquiry ON boards.board_id = inquiry.board_id " +
                "WHERE boards.board_type = '문의'";
        return jdbcTemplate.query(sql, new InquiryMapper());
    }

    private long insertBoardData(Board board) {
        SimpleJdbcInsert boardsJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("boards")
                .usingGeneratedKeyColumns("board_id");
        Map<String, Object> boardsParam = new ConcurrentHashMap<>();
        boardsParam.put("title", board.getTitle());
        boardsParam.put("content", board.getContent());
        boardsParam.put("registration_date", board.getRegistrationDate());
        boardsParam.put("board_type", board.getBoardType().name());
        boardsParam.put("user_id", board.getUserId());

        long board_id = boardsJdbcInsert.executeAndReturnKey(boardsParam).longValue();


        //문의
        if(board.getBoardType().equals(BoardType.문의)) {
            SimpleJdbcInsert inquiryJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("inquiry");
            Map<String, Object> inquryParam = new ConcurrentHashMap<>();
            inquryParam.put("board_id", board_id);
            inquiryJdbcInsert.execute(inquryParam);

            return board_id;

        }


        //공지사항
        SimpleJdbcInsert noticeJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("notice");
        Map<String, Object> noticeParam = new ConcurrentHashMap<>();
        noticeParam.put("board_id", board_id);
        noticeJdbcInsert.execute(noticeParam);
        return board_id;
    }
}
