package fx.redbox.service.user;

import fx.redbox.controller.api.ResponseMessage;
import fx.redbox.controller.user.form.*;
import fx.redbox.entity.enums.Permission;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import fx.redbox.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public boolean signUp(SignUpForm signUpForm) { // 기술 누수

        try {
            CreateUserData userData = getCreateUserData(signUpForm);
            //중복 이메일 검증
            if(userRepository.existsByEmail(userData.userAccount().getEmail()))
                throw new RuntimeException();

            userRepository.save(userData.userAccount(), userData.userInfo(), userData.user());

        } catch (Exception e) {
            throw new RuntimeException(String.valueOf(ResponseMessage.NOT_FOUND_USER));

        }
        return true;
    }

    @Override
    public SignInForm signIn(SignInForm signInForm) {
        User user = userRepository.findByEmail(signInForm.getEmail()).orElseThrow(() ->
                new RuntimeException(String.valueOf(ResponseMessage.NOT_FOUND_USER)));

        if(!signInForm.getPassword().equals(user.getUserAccount().getPassword())){
            throw new RuntimeException(String.valueOf(ResponseMessage.NOT_FOUND_USER));
        }

        return SignInForm.builder()
                .email(user.getUserAccount().getEmail()).build();
    }

    @Override
    public UserInfoForm getUser(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception(String.valueOf(ResponseMessage.NOT_FOUND_USER)));
        return new UserInfoForm(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public String getEmail(FindMailForm findMailForm) throws Exception {
        User user = userRepository.findEmail(findMailForm.getName(), findMailForm.getPhone())
                .orElseThrow(() -> new Exception(String.valueOf(ResponseMessage.NOT_FOUND_USER)));
        return user.getUserAccount().getEmail();
    }

    @Override
    public void editUserInfo(String email, UpdateForm updateForm) throws Exception {
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new Exception(String.valueOf(ResponseMessage.NOT_FOUND_USER)));
        userRepository.update(user.getUserId(), updateForm.getBirth(), updateForm.getPhone(), updateForm.getAddress() );
    }

    @Override
    public String findPassword(FindPasswordForm findPasswordForm) {
        //name과 email로 계정 존재 유뮤 확인
        if(!userRepository.existsByNameAndEmail(findPasswordForm.getName(), findPasswordForm.getEmail()))
            throw new RuntimeException();

        //SMTP를 사용해 사용자의 이메일에 인증번호 전송
        /*
        *
        */

        //랜덤 비밀번호 생성
        UUID uid = UUID.randomUUID();
        String tempPassword = uid.toString().substring(0, 10); //비밀번호 10자리

        //새 비밀번호 암호화 후 DB저장
        userRepository.insertPassword(findPasswordForm.getEmail(), tempPassword);

        //임시비밀번호 반환
        return tempPassword;
    }

    @Override
    public void deleteUser(String email) throws Exception {
        User userId = userRepository.findByEmail(email).
                orElseThrow(() -> new Exception(String.valueOf(ResponseMessage.NOT_FOUND_USER)));
        userRepository.deleteByUserId(userId.getUserId());
    }




    private static CreateUserData getCreateUserData(SignUpForm signUpForm) {
        User user = User.builder()
                .name(signUpForm.getName())
                .birth(signUpForm.getBirth())
                .gender(signUpForm.getGender())
                .bloodType(signUpForm.getBloodType())
                .build();
        UserAccount userAccount = UserAccount.builder()
                .email(signUpForm.getEmail())
                .password(signUpForm.getPassword())
                .build();
        UserInfo userInfo = UserInfo.builder()
                .phone(signUpForm.getPhone())
                .address(signUpForm.getAddress())
                .build();

        userInfo.setPermission(Permission.ROLE_USER); // 기본값 USER 로 지정

        userAccount.setPassword(userAccount.getPassword());
        CreateUserData userData = new CreateUserData(user, userAccount, userInfo);
        return userData;
    }

    private record CreateUserData(User user, UserAccount userAccount, UserInfo userInfo) {
    }

}
