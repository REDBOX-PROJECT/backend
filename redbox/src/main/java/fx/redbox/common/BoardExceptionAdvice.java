package fx.redbox.common;

import fx.redbox.common.Exception.BoardNotFoundException;
import fx.redbox.controller.api.BoardResponseMessage;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.api.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BoardExceptionAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseApi boardNotFoundException() {
        log.error("게시글을 불러올 수 없습니다.");
        return ResponseApi.fail(BoardResponseMessage.NOT_FOUND_BOARD.getStatusCode(), BoardResponseMessage.NOT_FOUND_BOARD.getMessage());
    }


}
