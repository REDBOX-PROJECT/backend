package fx.redbox.controller.user;

import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.config.SessionConst;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.api.UserResponseMessage;
import fx.redbox.controller.user.form.SignInForm;
import fx.redbox.controller.user.form.SignUpForm;
import fx.redbox.entity.users.User;
import fx.redbox.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    @ApiResponse(responseCode = "200", description = "로그인 성공") //,content = @Content(schema = @Schema(implementation = SignInForm.class)))
    @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다.")
    public ResponseApi signIn(@RequestBody @Valid SignInForm signInForm, HttpServletRequest httpServletRequest) {

        User loginUser = userService.signIn(signInForm);

        if(loginUser == null)
            throw new UserNotFoundException();

        //로그인 성공 세션 생성

        //세션 생성 전 기존 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true); //session이 없으면
        //세션에 loginUser 넣음
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
        session.setMaxInactiveInterval(1800); //Session이 30분동안 유지

        return ResponseApi.success(UserResponseMessage.LOGIN_SUCCESS.getMessage());
    }

//    @ResponseStatus
//    @ControllerAdvice
//    @RestControllerAdvice

    @PostMapping("/register") //회원가입
    @Operation(summary = "회원가입",
            description = "회원가입 API"
    )
    @ApiResponse(responseCode = "200", description = "회원 가입 성공")
    @ApiResponse(responseCode = "404", description = "회원 가입 실패")
    public ResponseApi signUp(@RequestBody @Valid SignUpForm signUpForm) {
        boolean resultBoolean = userService.signUp(signUpForm);
        return ResponseApi.success(UserResponseMessage.CREATED_USER.getMessage(), resultBoolean);
    }

    @PostMapping("/logout") //회원가입
    @Operation(summary = "로그아웃",
            description = "로그아웃 API"
    )
    @ApiResponse(responseCode = "200", description = "로그아웃 성공")
    public ResponseApi logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return ResponseApi.success(UserResponseMessage.LOGOUT_SUCCESS.getMessage());
        }

        return ResponseApi.fail(UserResponseMessage.LOGOUT_FAIL.getStatusCode(),
                UserResponseMessage.LOGOUT_FAIL.getMessage());
    }

}
