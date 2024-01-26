package fx.redbox.controller.user;

import fx.redbox.controller.api.ApiResponse;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import fx.redbox.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/redbox")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ApiResponse signUp(@RequestBody UserData signUpData) throws NoSuchAlgorithmException {
        User user = User.builder()
                .name(signUpData.getName())
                .birth(signUpData.getBirth())
                .gender(signUpData.getGender())
                .bloodType(signUpData.getBloodType())
                .build();
        UserAccount userAccount = UserAccount.builder()
                .email(signUpData.getEmail())
                .password(signUpData.getPassword())
                .build();
        UserInfo userInfo = UserInfo.builder()
                .phone(signUpData.getPhone())
                .address(signUpData.getAddress())
                .build();
        userService.signUp(userAccount, userInfo, user);
        return ApiResponse.res(HttpStatus.CREATED.value(), "회원가입 성공");
    }

    @GetMapping("/users/{userId}")
    public ApiResponse findByUserId(@PathVariable("userId") Long userId) {
        Optional<User> findUser = userService.findByUserId(userId);
        return ApiResponse.res(HttpStatus.OK.value(), "회원 단건 조회 성공", findUser);
    }

    @GetMapping("/users")
    public ApiResponse findAll() {
        List<User> all = userService.findAll();
        return ApiResponse.res(HttpStatus.OK.value(), "회원 전체 조회 성공", all);
    }

    @PatchMapping("/users/{userId}")
    public ApiResponse update(@PathVariable("userId") Long userId, @RequestBody UserData userData) { //비번 휴대폰 주소
        Optional<User> updateUser = userService.findByUserId(userId);

        User user = User.builder()
                .userId(updateUser.get().getUserId())
                .userAccount(
                        UserAccount.builder()
                                .password(userData.getPassword())
                                .build())
                .userInfo(
                        UserInfo.builder()
                                .phone(userData.getPhone())
                                .address(userData.getAddress())
                                .build())
                .build();
        userService.update(userId, user);

        return ApiResponse.res(HttpStatus.OK.value(), "회원 정보 수정 성공");
    }

    @DeleteMapping("/users/{userId}")
    public ApiResponse deleteByUserId(@PathVariable("userId") Long userId) {
        Optional<User> deleteUserId = userService.findByUserId(userId);
        if(deleteUserId.isPresent()) {
            userService.deleteByUserId(userId);
            return ApiResponse.res(HttpStatus.OK.value(), "회원 삭제 성공");
        }
        return ApiResponse.res(HttpStatus.BAD_REQUEST.value(), "회원 삭제 실패");
    }
}
