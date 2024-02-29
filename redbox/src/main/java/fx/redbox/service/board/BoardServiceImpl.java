package fx.redbox.service.board;


import fx.redbox.controller.board.form.AllBoardForm;
import fx.redbox.controller.board.form.BoardForm;
import fx.redbox.controller.board.form.InquiryAnswerForm;
import fx.redbox.controller.board.form.InquiryListForm;
import fx.redbox.entity.boards.Board;
import fx.redbox.entity.boards.Inquiry;
import fx.redbox.entity.users.User;
import fx.redbox.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public void saveBoard(BoardForm boardForm, User user) {

        Board board = Board.builder()
                .title(boardForm.getTitle())
                .registrationDate(Timestamp.valueOf(LocalDateTime.now()))
                .content(boardForm.getContent())
                .boardType(boardForm.getBoardType())
                .userId(user.getUserId())
                .build();

        boardRepository.saveBoard(board);
    }

    @Override
    public AllBoardForm getBoard(Long boardId) {
        Board AllBoard = boardRepository.findByBoardId(boardId);
        return new AllBoardForm(AllBoard);
    }

    @Override
    public void saveInquiryAnswer(Long boardId, InquiryAnswerForm inquiryAnswerForm) {
        boardRepository.saveInquiryAnswer(boardId, inquiryAnswerForm.getInquiryAnswerContent());

    }

    @Override
    public List<InquiryListForm> showInquiryList() {
        List<Inquiry> inquiries = boardRepository.findAllInquiry();
        List<InquiryListForm> result = new ArrayList<>();

        for (Inquiry inquiry : inquiries) {
            InquiryListForm build = InquiryListForm.builder()
                    .title(inquiry.getTitle())
                    .hasAnswer(inquiry.getInquiryAnswerContent() != null)
                    .registrationDate(inquiry.getRegistrationDate())
                    .build();
            result.add(build);
        }

        return result;
    }
}
