package fx.redbox.controller;

import fx.redbox.entity.boards.Board;
import fx.redbox.entity.enums.BoardType;
import fx.redbox.repository.BoardRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("redbox/boards")
public class BoardController {

    private final BoardRepository boardRepository;

    @GetMapping
    public String board(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "redbox/boards";
    }

    @GetMapping("/{boardId}")
    public String board(@PathVariable Long boardId, Model model) {
        Board board = boardRepository.findById(boardId);
        model.addAttribute("board",board);
        return "redbox/board";
    }

    @PostMapping("/add")
    public String addBoard(Board board, Model model) {
        boardRepository.save(board);
        model.addAttribute("board",board);
        return "redbox/board";
    }

    @GetMapping("/{boardId}/edit")
    public String editForm(@PathVariable Long boardId, Model model) {
        Board board = boardRepository.findById(boardId);
        model.addAttribute("board",board);
        return "redbox/editForm";
    }
    @PostMapping("/{boardId}/edit")
    public String edit(@PathVariable Long boardId, @ModelAttribute Board board) {
        boardRepository.update(boardId,board);
        return "redirect:/redbox/boards/{boardId}";
    }
    //테스트 데이터 생성
    @PostConstruct
    public void init() {
        boardRepository.save(new Board("boardA","contentA", new Timestamp(19), BoardType.문의,10L));
        boardRepository.save(new Board("boardB","contentB", new Timestamp(20), BoardType.문의,11L));
    }
}
