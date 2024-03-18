package fx.redbox.common;

import fx.redbox.common.Exception.DonorCardNotFoundException;
import fx.redbox.common.Exception.DuplicateCertificateNumberException;
import fx.redbox.controller.api.DonorCardResponseMessage;
import fx.redbox.controller.api.ResponseApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class DonorCardExceptionAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateCertificateNumberException.class)
    public ResponseApi duplicateCertificateNumberException() {
        log.error("이미 등록된 헌혈증입니다.");
        return ResponseApi.fail(DonorCardResponseMessage.DUPLICATE_DONORCARD.getStatusCode(),
                DonorCardResponseMessage.DUPLICATE_DONORCARD.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DonorCardNotFoundException.class)
    public ResponseApi donorCardNotFoundException() {
        log.error("헌혈증 정보를 찾을 수 없습니다.");
        return ResponseApi.fail(DonorCardResponseMessage.NOT_FOUND_DONORCARD.getStatusCode(),
                DonorCardResponseMessage.NOT_FOUND_DONORCARD.getMessage());
    }
}
