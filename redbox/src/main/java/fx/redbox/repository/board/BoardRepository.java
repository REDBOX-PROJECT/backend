package fx.redbox.repository.board;


import fx.redbox.entity.boards.Board;
import fx.redbox.entity.boards.Inquiry;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository {

    void saveBoard(Board board);

    Board findByBoardId(Long boardId);

    void saveInquiryAnswer(Long boardId, String inquiryAnswerContent);

    List<Inquiry> findAllInquiry();

}
