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

    @GetMapping("/users/{userId}")
    public ApiResponse findByUserId(@PathVariable("userId") Long userId) {
        Optional<User> findUser = userService.findByUserId(userId);
        if(findUser.isPresent()) {
            User user = findUser.get();
            UserInfoForm userInfo = UserInfoForm.builder()
                    .userId(user.getUserId())
                    .email(user.getUserAccount().getEmail())
                    .name(user.getName())
                    .birth(user.getBirth())
                    .gender(user.getGender())
                    .bloodType(user.getBloodType())
                    .grade(user.getGrade())
                    .phone(user.getUserInfo().getPhone())
                    .donationCount(user.getUserInfo().getDonationCount())
                    .permission(user.getUserInfo().getPermission())
                    .build();
            return ApiResponse.res(HttpStatus.OK.value(), "회원 단건 조회 성공", userInfo);
    @PostMapping("/register") //회원가입
    public ApiResponse<Boolean> signUp(@RequestBody SignRequestForm signRequestForm) throws Exception {
        return ApiResponse.res(HttpStatus.OK.value(), "회원가입 성공", userService.signUp(signRequestForm));
    }

        }
        return ApiResponse.res(HttpStatus.BAD_REQUEST.value(), "존재하지 않는 회원입니다.");
    }

    @GetMapping("/users")
    public ApiResponse findAll() {
        List<UserInfoForm> all = userService.findAll();
        return ApiResponse.res(HttpStatus.OK.value(), "회원 전체 조회 성공", all);
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
