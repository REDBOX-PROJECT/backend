package fx.redbox.common;

import fx.redbox.common.Exception.DuplicateEmailException;
import fx.redbox.common.Exception.EmailNotFoundException;
import fx.redbox.common.Exception.PasswordMismatchException;
import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.controller.api.ApiResponse;
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

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse userNotFoundException(UserNotFoundException e) {
        log.error("회원을 찾을 수 없습니다.");
        return ApiResponse.fail(StatusCode.NOT_FOUND, UserResponseMessage.NOT_FOUND_USER.getMessage());
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse duplicateEmailException(Exception e) {
        log.error("이미 존재하는 회원입니다.");
        return ApiResponse.fail(StatusCode.BAD_REQUEST, UserResponseMessage.FAIL_CREATED_USER.getMessage());
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse passwordMismatchException(Exception e) {
        log.error("비밀번호가 일치하지 않습니다.");
        return ApiResponse.fail(StatusCode.BAD_REQUEST, UserResponseMessage.PASSWORD_MISMATCH.getMessage());
    }


    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse emailNotFoundException(Exception e) {
        log.error("이메일을 찾을 수 없습니다.");
        return ApiResponse.fail(StatusCode.BAD_REQUEST, UserResponseMessage.NOT_FOUND_EMAIL.getMessage());
    }

}
