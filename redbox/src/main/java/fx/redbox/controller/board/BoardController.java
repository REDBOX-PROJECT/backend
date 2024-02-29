package fx.redbox.controller.board;

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
            return ResponseApi.fail(400, "사용자 없음");

        boardService.saveBoard(board, user.get());
        return ResponseApi.success("게시글(문의 및 공지사항) 저장 성공");
    }


    @PostMapping("/inquiry/answer/{boardId}")
    public ResponseApi saveInquiry(@PathVariable Long boardId, @RequestBody InquiryAnswerForm inquiryAnswerForm) {

        //해당하는 게시글 불러오기
        AllBoardForm board = boardService.getBoard(boardId);

        //해당하는 타입이 문의가 맞으면
        if (board.getBoardType().equals(BoardType.문의)) {
            boardService.saveInquiryAnswer(board.getBoardId(), inquiryAnswerForm);
            return ResponseApi.success("게시글(문의 및 공지사항) 저장 성공");
        }

        return ResponseApi.fail(400, "문의게시글이 아닙니다.");
    }


    @GetMapping("/inquiry/list")
    public ResponseApi<List<InquiryListForm>> showInquiryList() {
        List<InquiryListForm> inquiryListForm = boardService.showInquiryList();
        return ResponseApi.success("성공", inquiryListForm);
    }
 }
