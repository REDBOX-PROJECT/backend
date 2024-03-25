package fx.redbox.common;

import fx.redbox.common.Exception.DonorCardRequestExhaustedException;
import fx.redbox.common.Exception.DonorCardRequestNotFoundException;
import fx.redbox.common.Exception.DuplicateDonorCardRequestException;
import fx.redbox.controller.api.DonorCardRequestResponseMessage;
import fx.redbox.controller.api.ResponseApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DonorCardRequestExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateDonorCardRequestException.class)
    public ResponseApi duplicateDonorCardRequestException() {
        log.error("이미 등록된 헌혈증 요청입니다.");
        return ResponseApi.fail(DonorCardRequestResponseMessage.DONOR_CARD_REQUEST_DUPLICATE.getStatusCode(),
                DonorCardRequestResponseMessage.DONOR_CARD_REQUEST_DUPLICATE.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DonorCardRequestNotFoundException.class)
    public ResponseApi donorCardRequestNotFoundException() {
        log.error("헌혈증 요청 자료를 찾을 수 없습니다.");
        return ResponseApi.fail(DonorCardRequestResponseMessage.DONOR_CARD_REQUEST_NOT_FOUND.getStatusCode(),
                DonorCardRequestResponseMessage.DONOR_CARD_REQUEST_NOT_FOUND.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DonorCardRequestExhaustedException.class)
    public ResponseApi donorCardRequestExhausted() {
        log.error("레드박스 헌혈증이 없습니다.");
        return ResponseApi.fail(DonorCardRequestResponseMessage.DONOR_CARD_REQUEST_EXHAUSTED.getStatusCode(),
                DonorCardRequestResponseMessage.DONOR_CARD_REQUEST_EXHAUSTED.getMessage());
    }
}
