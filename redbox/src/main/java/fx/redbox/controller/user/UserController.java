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


    @GetMapping("/user/{email}") //회원조회
    public ApiResponse<SignResponseForm> getUser(@PathVariable String email) {
        try {
            return ApiResponse.res(HttpStatus.OK.value(),"회원 조회 성공", userService.getUser(email));
        } catch (Exception e) {
            return ApiResponse.res(HttpStatus.BAD_REQUEST.value(),"존재하지 않는 회원입니다.");
        }
    }

    @GetMapping("/admin/{email}") //관리자권한 회원조회
    public ApiResponse<SignResponseForm> getUserForAdmin(@PathVariable String email) {
        try {
            return ApiResponse.res(HttpStatus.OK.value(), "ADMIN : 회원 조회 성공",userService.getUser(email));
        } catch (Exception e) {
            return ApiResponse.res(HttpStatus.BAD_REQUEST.value(), "ADMIN : 회원 조회 실패");
        }
    }

    @PostMapping("/user/findEmail") //이메일 찾기
    public ApiResponse<FindMailOrPasswordForm> getEmail(@RequestBody FindMailOrPasswordForm findMailOrPasswordForm) throws Exception {
        try {
            return ApiResponse.res(HttpStatus.OK.value(), "사용자 이메일 찾기 성공",userService.getEmail(findMailOrPasswordForm));
        } catch (Exception e) {
            return ApiResponse.res(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 회원입니다.");
        }
    }

    @PatchMapping("/user/{email}") //회원 정보 수정 - 생년얼일, 전화번호, 주소
    public ApiResponse update(@PathVariable String email,
                                                      @RequestBody UpdateForm updateForm) {
        try {
            userService.update(email, updateForm);
            return ApiResponse.res(HttpStatus.OK.value(), "사용자 정보 수정 성공");
        } catch (Exception e) {
            return ApiResponse.res(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 회원입니다.");
        }
    }

    @PostMapping("/user/findPassword") //비밀번호 찾기, 임시비밀번호 발급
    public ApiResponse<String> getPassword(@RequestBody FindMailOrPasswordForm findMailOrPasswordForm) {
        try {
            return ApiResponse.res(HttpStatus.OK.value(), "사용자 임시 비밀번호 발급", userService.findPassword(findMailOrPasswordForm));
        } catch (Exception e) {
            return ApiResponse.res(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 회원입니다.");
        }
    }

    @DeleteMapping("/user/{email}") //회원 탈퇴
    public ApiResponse deleteUser(@PathVariable String email) {
        try {
            userService.deleteUser(email);
            return ApiResponse.res(HttpStatus.OK.value(), "회원 탈퇴 성공");
        } catch (Exception e) {
            return ApiResponse.res(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 회원입니다.");
        }
    }
}
