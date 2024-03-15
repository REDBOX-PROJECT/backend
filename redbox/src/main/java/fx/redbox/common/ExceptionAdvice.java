package fx.redbox.common;

import fx.redbox.common.Exception.DuplicateEmailException;
import fx.redbox.common.Exception.EmailNotFoundException;
import fx.redbox.common.Exception.PasswordMismatchException;
import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.api.UserResponseMessage;
import fx.redbox.controller.api.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(NoSuchFieldException.class)
//    public void myHandler(Exception e){
//        throw new MyCustomException("NoSuchFieldException!!");
//    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseApi userNotFoundException(Exception e) {
        log.error("회원을 찾을 수 없습니다.");
        return ResponseApi.fail(UserResponseMessage.CREATED_USER.getStatusCode(), UserResponseMessage.NOT_FOUND_USER.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseApi duplicateEmailException(Exception e) {
        log.error("이미 존재하는 회원입니다.");
        return ResponseApi.fail(UserResponseMessage.DUPLICATE_EMAIL.getStatusCode(), UserResponseMessage.DUPLICATE_EMAIL.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseApi passwordMismatchException(Exception e) {
        log.error("비밀번호가 일치하지 않습니다.");
        return ResponseApi.fail(UserResponseMessage.PASSWORD_MISMATCH.getStatusCode(), UserResponseMessage.PASSWORD_MISMATCH.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseApi emailNotFoundException(Exception e) {
        log.error("이메일을 찾을 수 없습니다.");
        return ResponseApi.fail(UserResponseMessage.NOT_FOUND_EMAIL.getStatusCode(), UserResponseMessage.NOT_FOUND_EMAIL.getMessage());
    }

}
