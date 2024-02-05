package fx.redbox.controller.user;

import fx.redbox.controller.api.ApiResponse;
import fx.redbox.controller.api.UserResponseMessage;
import fx.redbox.controller.user.form.SignInForm;
import fx.redbox.controller.user.form.SignUpForm;
import fx.redbox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/redbox")
public class SignController {
    private final UserService userService;

    @PostMapping("/login") //로그인
    public ApiResponse signIn(@RequestBody SignInForm signInForm) {
        return ApiResponse.success(UserResponseMessage.LOGIN_SUCCESS.getMessage(), userService.signIn(signInForm));
    }

//    @ResponseStatus
//    @ControllerAdvice
//    @RestControllerAdvice

    @PostMapping("/register") //회원가입
    public ApiResponse<Boolean> signUp(@RequestBody SignUpForm signUpForm) {
        boolean resultBoolean = userService.signUp(signUpForm);
        return ApiResponse.success(UserResponseMessage.CREATED_USER.getMessage(), resultBoolean);
    }

}
