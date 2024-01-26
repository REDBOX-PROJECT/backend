package fx.redbox.repository;

import fx.redbox.entity.boards.Board;
import fx.redbox.entity.users.User;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardRepository {
    private static final Map<Long, Board> store = new HashMap<>();
    private static long sequence = 0L;

    public Board save(Board board) {

        board.setBoardId(++sequence);
        store.put(board.getBoardId(),board);
        return board;
    }

    public Board findById(Long id) {
        return store.get(id);
    }

    public void update(Long boardId, Board updateParam) {
        Board findBoard = findById(boardId);
        findBoard.setTitle(updateParam.getTitle());
        findBoard.setContent(updateParam.getContent());
        findBoard.setRegistrationDate(updateParam.getRegistrationDate());
    }

    public List<Board> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clear() {
        store.clear();
    }
}
