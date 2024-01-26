package fx.redbox.repository;

import fx.redbox.entity.boards.Board;
import fx.redbox.entity.enums.BoardType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardRepositoryTest {
    BoardRepository boardRepository = new BoardRepository();
    //매 테스트 마다 저장소 초기화
    @AfterEach
    void afterEach() {
        boardRepository.clear();
    }

    @Test
    void save() {

        //given
        Board board = new Board("boardA","contentA", new Timestamp(19), BoardType.문의,10L);
        //when
        Board savedBoard = boardRepository.save(board);
        //then
        Board findBoard = boardRepository.findById(board.getBoardId());
        Assertions.assertThat(findBoard).isEqualTo(savedBoard);
    }



    @Test
    void update() {

        //given
        Board board = new Board("boardA","contentA", new Timestamp(19), BoardType.문의,10L);

        Board savedBoard = boardRepository.save(board);
        Long boardId = savedBoard.getBoardId();

        //when
        Board updateParam = new Board("boardB","contentB", new Timestamp(20), BoardType.문의,11L);
        boardRepository.update(boardId,updateParam);

        Board findBoard = boardRepository.findById(boardId);

        //then
        Assertions.assertThat(findBoard.getTitle()).isEqualTo(updateParam.getTitle());
        Assertions.assertThat(findBoard.getContent()).isEqualTo(updateParam.getContent());
        Assertions.assertThat(findBoard.getRegistrationDate()).isEqualTo(updateParam.getRegistrationDate());
    }

    @Test
    void findAll() {

        //given
        Board board1 = new Board("boardA","contentA", new Timestamp(19), BoardType.문의,10L);
        Board board2 = new Board("boardB","contentB", new Timestamp(20), BoardType.문의,11L);

        boardRepository.save(board1);
        boardRepository.save(board2);

        //when
        List<Board> result = boardRepository.findAll();

        //then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(board1,board2);
    }


}