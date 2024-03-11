package fx.redbox.service.user;

import fx.redbox.common.Exception.DuplicateEmailException;
import fx.redbox.common.Exception.EmailNotFoundException;
import fx.redbox.common.Exception.PasswordMismatchException;
import fx.redbox.common.Exception.UserNotFoundException;
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
    public boolean signUp(SignUpForm signUpForm){ // 기술 누수

        ConvertSignUpFormToUser userData = getConvertSignUpFormToUser(signUpForm);

        //중복 이메일 검증
        boolean existsUserMail = userRepository.existsByEmail(userData.userAccount().getEmail());
        if(existsUserMail) {
            throw new DuplicateEmailException();
        }

        userRepository.saveUser(userData.userAccount(), userData.userInfo(), userData.user());
        return true;
    }

    @Override
    public User signIn(SignInForm signInForm) {
        User user = userRepository.findByEmail(signInForm.getEmail())
                .orElseThrow(() -> new EmailNotFoundException(1));

        if(!signInForm.getPassword().equals(user.getUserAccount().getPassword())){
            throw new PasswordMismatchException();
        }

        return user;
    }

    @Override
    public UserInfoForm getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
        return new UserInfoForm(user);
    }

    @Override
    public Optional<User> findByEmail(String email){
        User userEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
        return Optional.ofNullable(userEmail);
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
       User byUserId = userRepository.findByUserId(userId)
               .orElseThrow(() -> new UserNotFoundException());
        return Optional.ofNullable(byUserId);
    }

    @Override
    public String getEmail(FindMailForm findMailForm) {
        User user = userRepository.findEmail(findMailForm.getName(), findMailForm.getPhone())
                .orElseThrow(() -> new UserNotFoundException());
        return user.getUserAccount().getEmail();
    }

    @Override
    public void editUserInfo(String email, UpdateForm updateForm) {
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException());
        userRepository.update(user.getUserId(), updateForm.getBirth(), updateForm.getPhone(), updateForm.getAddress() );
    }

    @Override
    public String findPassword(FindPasswordForm findPasswordForm) {
        //name과 email로 계정 존재 유뮤 확인
        if(!userRepository.existsByNameAndEmail(findPasswordForm.getName(), findPasswordForm.getEmail())) {
            throw new UserNotFoundException();
        }
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
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new UserNotFoundException());
        userRepository.deleteByUserId(user.getUserId());
    }




    private static ConvertSignUpFormToUser getConvertSignUpFormToUser(SignUpForm signUpForm) {
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
        ConvertSignUpFormToUser userData = new ConvertSignUpFormToUser(user, userAccount, userInfo);
        return userData;
    }

    private record ConvertSignUpFormToUser(User user, UserAccount userAccount, UserInfo userInfo) {
    }

}
