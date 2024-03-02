package fx.redbox.controller.board;

import fx.redbox.common.Exception.BoardNotFoundException;
import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.controller.api.BoardResponseMessage;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.board.form.AllBoardForm;
import fx.redbox.controller.board.form.BoardForm;
import fx.redbox.controller.board.form.InquiryAnswerForm;
import fx.redbox.controller.board.form.InquiryListForm;
import fx.redbox.entity.enums.BoardType;
import fx.redbox.entity.users.User;
import fx.redbox.service.board.BoardService;
import fx.redbox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/redbox")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/boards")
    public ResponseApi saveBoard(@RequestBody BoardForm board) {

        //테스트 아이디입니다.
        Optional<User> user = userService.findByUserId(1L);
        if(user.isEmpty())
            throw new UserNotFoundException();

        boardService.saveBoard(board, user.get());
        if(board.getBoardType().equals(BoardType.문의))
            return ResponseApi.success(BoardResponseMessage.CREATED_INQUIRY_BOARD.getMessage());

        return ResponseApi.success(BoardResponseMessage.CREATED_NOTICE_BOARD.getMessage());
    }


    @PostMapping("/inquiry/answer/{boardId}")
    public ResponseApi saveInquiry(@PathVariable Long boardId, @RequestBody InquiryAnswerForm inquiryAnswerForm) {

        //해당하는 게시글 불러오기
        AllBoardForm board = boardService.getBoard(boardId);

        //문의 게시글이 아니면
        if (!board.getBoardType().equals(BoardType.문의)) {
            throw new BoardNotFoundException();
        }

        //문의 게시글이면
        boardService.saveInquiryAnswer(board.getBoardId(), inquiryAnswerForm);
        return ResponseApi.success(BoardResponseMessage.CREATED_INQUIRY_ANSWER_BOARD.getMessage());
    }


    @GetMapping("/inquiry/list")
    public ResponseApi<List<InquiryListForm>> showInquiryList() {
        List<InquiryListForm> inquiryListForm = boardService.showInquiryList();
        return ResponseApi.success(BoardResponseMessage.SUCCESS_SHOW_INQUIRY_LIST.getMessage(), inquiryListForm);
    }
 }
