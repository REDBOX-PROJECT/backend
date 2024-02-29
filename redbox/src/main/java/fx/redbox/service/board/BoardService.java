package fx.redbox.service.board;

import fx.redbox.controller.board.form.AllBoardForm;
import fx.redbox.controller.board.form.BoardForm;
import fx.redbox.controller.board.form.InquiryAnswerForm;
import fx.redbox.controller.board.form.InquiryListForm;
import fx.redbox.entity.users.User;

import java.util.List;

public interface BoardService {

    void saveBoard(BoardForm board, User user);

    void saveInquiryAnswer(Long boardId, InquiryAnswerForm inquiryAnswerForm);

    AllBoardForm getBoard(Long boardId);

    List<InquiryListForm> showInquiryList();

}
