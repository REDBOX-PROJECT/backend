package fx.redbox.controller.user;

import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.api.UserResponseMessage;
import fx.redbox.controller.user.form.SignInForm;
import fx.redbox.controller.user.form.SignUpForm;
import fx.redbox.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "SIGN API", description = "사용자 인증 관리 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping()
public class SignController {
    private final UserService userService;

    @PostMapping("/login") //로그인
    @Operation(summary = "로그인",
            description = "로그인 API"
    )
    @ApiResponse(responseCode = "200", description = "로그인 성공")
    @ApiResponse(responseCode = "400", description = "회원을 찾을 수 없습니다.")
    public ResponseApi signIn(@RequestBody @Valid SignInForm signInForm) {
        return ResponseApi.success(UserResponseMessage.LOGIN_SUCCESS.getMessage(), userService.signIn(signInForm));
    }

//    @ResponseStatus
//    @ControllerAdvice
//    @RestControllerAdvice

    @PostMapping("/register") //회원가입
    @Operation(summary = "회원가입",
            description = "회원가입 API"
    )
    @ApiResponse(responseCode = "200", description = "회원 가입 성공")
    @ApiResponse(responseCode = "400", description = "회원 가입 실패")
    public ResponseApi signUp(@RequestBody @Valid SignUpForm signUpForm) {
        boolean resultBoolean = userService.signUp(signUpForm);
        return ResponseApi.success(UserResponseMessage.CREATED_USER.getMessage(), resultBoolean);
    }

}
