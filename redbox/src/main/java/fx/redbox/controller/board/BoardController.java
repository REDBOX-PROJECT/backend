package fx.redbox.controller.board;

import fx.redbox.common.Exception.BoardNotFoundException;
import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.controller.api.BoardResponseMessage;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.board.form.*;
import fx.redbox.entity.enums.BoardType;
import fx.redbox.entity.users.User;
import fx.redbox.service.board.BoardService;
import fx.redbox.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "BOARD API", description = "게시글 관리 API")
@RestController
@RequestMapping("/redbox")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/boards")
    @Operation(
            summary = "게시글(문의 or 공지사항) 저장",
            description = "게시글 타입에 따라 문의 or 공지사항이 구분됩니다."
    )
    @ApiResponse(responseCode = "200", description = "문의 or 공지사항 게시글이 저장되었습니다.")
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
    @Operation(
            summary = "문의에 대한 답변 저장",
            description = "문의에 대한 답변을 저장합니다."
    )
    @ApiResponse(responseCode = "200", description = "문의에 대한 답변이 저장되었습니다.")
    @ApiResponse(responseCode = "400", description = "게시글을 불러올 수 없습니다.")
    public ResponseApi saveInquiryAnswer(@PathVariable Long boardId, @RequestBody InquiryAnswerForm inquiryAnswerForm) {

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
    @Operation(
            summary = "문의 리스트",
            description = "문의 게시글을 모두 나타냅니다."
    )
    @ApiResponse(responseCode = "200", description = "문의게시글 목록 불러오기 성공")
    public ResponseApi showInquiryList() {
        List<InquiryListForm> inquiryListForm = boardService.showInquiryList();
        return ResponseApi.success(BoardResponseMessage.SUCCESS_SHOW_INQUIRY_LIST.getMessage(), inquiryListForm);
    }


    @GetMapping("/notice/list")
    public ResponseApi showNoticeList() {
        List<NoticeListForm> noticeListForm = boardService.showNoticeList();
        return ResponseApi.success("공지사항 리스트", noticeListForm);
    }
 }
