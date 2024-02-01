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
    //게시글 등록
    @PostMapping("/add")
    public ApiResponse addBoard(Board board, Model model) {
        boardRepository.save(board);
        model.addAttribute("board",board);
        return ApiResponse.res(HttpStatus.OK.value(), "게시글 저장 완료");
    }
