package fx.redbox.controller.user;

import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.config.argumentresolver.Login;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.api.UserResponseMessage;
import fx.redbox.controller.user.form.*;
import fx.redbox.entity.users.User;
import fx.redbox.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "USER API", description = "사용자 관리 API")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController { // resource : users

    private final UserService userService;

    @PostMapping("/findEmail") //이메일 찾기
    @Operation(
            summary = "사용자 이메일 찾기",
            description = "이름, 휴대폰번호로 사용자 이메일을 찾습니다."
    )
    @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공")
    @ApiResponse(responseCode = "404", description = "이메일을 찾을 수 없습니다.")
    public ResponseApi getEmail(@RequestBody @Valid FindMailForm findMailForm) {
        String email = userService.getEmail(findMailForm);
        return ResponseApi.success(UserResponseMessage.READ_USER.getMessage(), email);
    }

    @PatchMapping("/{email}") //회원 정보 수정 - 생년얼일, 전화번호, 주소
    @Operation(summary = "사용자 정보 수정",
            description = "사용자의 생년월일, 전화번호, 주소를 변경할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공")
    @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다.")
    public ResponseApi editUserInfo(@PathVariable String email,
                                    @RequestBody @Valid UpdateForm updateForm) {
        userService.editUserInfo(email, updateForm);
        return ResponseApi.success(UserResponseMessage.UPDATE_USER.getMessage());
    }

    @PostMapping("/findPassword") //비밀번호 찾기, 임시비밀번호 발급
    @Operation(summary = "사용자 임시 비밀번호 발급",
            description = "사용자의 이메일, 이름으로 임시 비밀번호를 발급받을 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공")
    @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다.")
    public ResponseApi getPassword(@RequestBody @Valid FindPasswordForm findPasswordForm) {
        return ResponseApi.success(UserResponseMessage.READ_USER.getMessage(), userService.findPassword(findPasswordForm));
    }

    @DeleteMapping("/{email}") //회원 탈퇴
    @Operation(summary = "사용자 회원 탈퇴",
            description = "회원 탈퇴 기능입니다."
    )
    @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공")
    @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없습니다.")
    public ResponseApi deleteUser(@PathVariable String email, @Login User loginUser) {
        if(loginUser == null)
            throw new UserNotFoundException();

        userService.deleteUser(loginUser);
        return ResponseApi.success(UserResponseMessage.DELETE_USER.getMessage());
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
