package fx.redbox.controller.user;

import fx.redbox.controller.api.ApiResponse;
import fx.redbox.controller.user.form.FindMailOrPasswordForm;
import fx.redbox.controller.user.form.SignRequestForm;
import fx.redbox.controller.user.form.SignResponseForm;
import fx.redbox.controller.user.form.UpdateForm;
import fx.redbox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/redbox")
public class UserController {

    private final UserService userService;

    @PostMapping("/login") //로그인
    public ApiResponse<SignResponseForm> signIn(@RequestBody SignRequestForm signRequestForm) throws Exception {
        return ApiResponse.res(HttpStatus.OK.value(), "로그인 성공", userService.signIn(signRequestForm));
    }

    @PostMapping("/register") //회원가입
    public ApiResponse<Boolean> signUp(@RequestBody SignRequestForm signRequestForm) throws Exception {
        return ApiResponse.res(HttpStatus.OK.value(), "회원가입 성공", userService.signUp(signRequestForm));
    }

    @GetMapping("/user/{email}") //회원조회
    public ApiResponse<SignResponseForm> getUser(@PathVariable String email) throws Exception {
        return ApiResponse.res(HttpStatus.OK.value(),"회원 조회 성공", userService.getUser(email));
    }

    @GetMapping("/admin/{email}") //관리자권한 회원조회
    public ApiResponse<SignResponseForm> getUserForAdmin(@PathVariable String email) throws Exception {
        return ApiResponse.res(HttpStatus.OK.value(), "ADMIN : 회원 조회 성공",userService.getUser(email));
    }

    @GetMapping("/user/findEmail") //이메일 찾기
    public ApiResponse<FindMailOrPasswordForm> getEmail(@RequestBody FindMailOrPasswordForm findMailOrPasswordForm) throws Exception {
        return ApiResponse.res(HttpStatus.OK.value(), "사용자 이메일 찾기 성공",userService.getEmail(findMailOrPasswordForm));
    }

    @PatchMapping("/user/{email}") //회원 정보 수정 - 생년얼일, 전화번호, 주소
    public ApiResponse<FindMailOrPasswordForm> update(@PathVariable String email,
                                                      @RequestBody UpdateForm updateForm) throws Exception{
        userService.update(email, updateForm);
        return ApiResponse.res(HttpStatus.OK.value(), "사용자 정보 수정 성공");
    }

    @PostMapping("/user/findPassword") //비밀번호 찾기, 임시비밀번호 발급
    public ApiResponse<String> getPassword(@RequestBody FindMailOrPasswordForm findMailOrPasswordForm) {
        return ApiResponse.res(HttpStatus.OK.value(), "사용자 임시 비밀번호 발급", userService.findPassword(findMailOrPasswordForm));
    }

    @DeleteMapping("/user/{email}") //회원 탈퇴
    public ApiResponse deleteUser(@PathVariable String email) throws Exception {
        userService.deleteUser(email);
        return ApiResponse.res(HttpStatus.OK.value(), "회원 탈퇴 성공");
    }
}
