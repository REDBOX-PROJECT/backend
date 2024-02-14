package fx.redbox.controller.user;

import fx.redbox.controller.api.ApiResponse;
import fx.redbox.controller.api.UserResponseMessage;
import fx.redbox.controller.user.form.*;
import fx.redbox.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/redbox/users")
public class UserController { // resource : users

    private final UserService userService;

    @PostMapping("/findEmail") //이메일 찾기
    public ApiResponse<String> getEmail(@RequestBody @Valid FindMailForm findMailForm) {
        String email = userService.getEmail(findMailForm);
        return ApiResponse.success(UserResponseMessage.READ_USER.getMessage(), email);
    }

    @PatchMapping("/{email}") //회원 정보 수정 - 생년얼일, 전화번호, 주소
    public ApiResponse editUserInfo(@PathVariable String email,
                                                      @RequestBody @Valid UpdateForm updateForm) {
        userService.editUserInfo(email, updateForm);
        return ApiResponse.success(UserResponseMessage.UPDATE_USER.getMessage(), null);
    }

    @PostMapping("/findPassword") //비밀번호 찾기, 임시비밀번호 발급
    public ApiResponse<String> getPassword(@RequestBody @Valid FindPasswordForm findPasswordForm) {
        return ApiResponse.success(UserResponseMessage.READ_USER.getMessage(), userService.findPassword(findPasswordForm));
    }

    @DeleteMapping("/{email}") //회원 탈퇴
    public ApiResponse deleteUser(@PathVariable String email) {
        userService.deleteUser(email);
        return ApiResponse.success(UserResponseMessage.DELETE_USER.getMessage(), null);
    }

//    @GetMapping("/admin/{email}") //관리자권한 회원조회
//    public ApiResponse<UserInfoForm> getUserForAdmin(@PathVariable String email) {
//        try {
//            return ApiResponse.success(StatusCode.OK, ResponseMessage.READ_USER.getMessage(),userService.getUser(email));
//        } catch (Exception e) {
//            return ApiResponse.fail(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER.getMessage());
//        }
//    }
}
