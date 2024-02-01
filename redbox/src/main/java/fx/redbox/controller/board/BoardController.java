package fx.redbox.controller.board;

import fx.redbox.controller.api.ApiResponse;
import fx.redbox.entity.boards.Board;
import fx.redbox.entity.enums.BoardType;
import fx.redbox.repository.BoardRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("redbox/boards")
public class BoardController {

    private final BoardRepository boardRepository;

    //전체 게시글 조회
    @GetMapping
    public ApiResponse board(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return ApiResponse.res(HttpStatus.OK.value(), "게시글 전체 조회 완료", boards);
    }
    //단일 게시글 조회
    @GetMapping("/{boardId}")
    public ApiResponse board(@PathVariable Long boardId, Model model) {
        Board board = boardRepository.findById(boardId);
        model.addAttribute("board",board);
        return ApiResponse.res(HttpStatus.OK.value(), "단일 게시글 조회 완료",board);
    }
    //게시글 등록
    @PostMapping("/add")
    public ApiResponse addBoard(Board board, Model model) {
        boardRepository.save(board);
        model.addAttribute("board",board);
        return ApiResponse.res(HttpStatus.OK.value(), "게시글 저장 완료");
    }
    //게시글 수정
    @PostMapping("/{boardId}/edit")
    public ApiResponse edit(@PathVariable Long boardId, @ModelAttribute Board board) {
        boardRepository.update(boardId,board);
        return ApiResponse.res(HttpStatus.OK.value(), "게시글 수정 완료");
    }
    //테스트 데이터 생성
    @PostConstruct
    public void init() {
        boardRepository.save(new Board("boardA","contentA", new Timestamp(19), BoardType.문의,10L));
        boardRepository.save(new Board("boardB","contentB", new Timestamp(20), BoardType.문의,11L));
    }
}
