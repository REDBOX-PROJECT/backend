package fx.redbox.service.user;

import fx.redbox.controller.user.form.FindMailOrPasswordForm;
import fx.redbox.controller.user.form.SignRequestForm;
import fx.redbox.controller.user.form.SignResponseForm;
import fx.redbox.controller.user.form.UpdateForm;
import fx.redbox.entity.enums.Permission;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import fx.redbox.repository.user.UserRepository;
import fx.redbox.token.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder, UserRepository userRepository, @Lazy JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean signUp(SignRequestForm signRequestForm) {
        try {
            User user = User.builder()
                    .name(signRequestForm.getName())
                    .birth(signRequestForm.getBirth())
                    .gender(signRequestForm.getGender())
                    .bloodType(signRequestForm.getBloodType())
                    .build();
            UserAccount userAccount = UserAccount.builder()
                    .email(signRequestForm.getEmail())
                    .password(signRequestForm.getPassword())
                    .build();
            UserInfo userInfo = UserInfo.builder()
                    .phone(signRequestForm.getPhone())
                    .address(signRequestForm.getAddress())
                    .build();

            userInfo.setPermission(Permission.ROLE_USER); // 기본값 USER 로 지정

            userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
            //중복 이메일 검증
            if(userRepository.existsByEmail(userAccount.getEmail()))
                throw new RuntimeException();

            userRepository.save(userAccount, userInfo, user);

        }catch (Exception e) {
            throw new RuntimeException("계정이 존재하거나 잘못된 요청입니다.");

        }
        return true;
    }

    @Override
    public SignResponseForm signIn(SignRequestForm signRequestForm) throws Exception{
        User user = userRepository.findByEmail(signRequestForm.getEmail()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if(!passwordEncoder.matches(signRequestForm.getPassword(), user.getUserAccount().getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        return SignResponseForm.builder()
                .userId(user.getAccountId())
                .name(user.getName())
                .email(user.getUserAccount().getEmail())
                .permission(user.getUserInfo().getPermission())
                .token(jwtProvider.createToken(user.getUserAccount().getEmail(), user.getUserInfo().getPermission()))
                .build();
    }

    @Override
    public SignResponseForm getUser(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponseForm(user);
    }

        userRepository.save(userAccount, userInfo, user);
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public FindMailOrPasswordForm getEmail(FindMailOrPasswordForm findMailOrPasswordForm) throws Exception {
        User user = userRepository.findEmail(findMailOrPasswordForm.getName(), findMailOrPasswordForm.getPhone())
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new FindMailOrPasswordForm(user);
    }

    @Override
    public void update(String email, UpdateForm updateForm) throws Exception {
        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new Exception("게정을 찾을 수 없습니다."));
        userRepository.update(user.getUserId(), updateForm.getBirth(), updateForm.getPhone(), updateForm.getAddress() );
    }

    @Override
    public String findPassword(FindMailOrPasswordForm findPasswordForm) {
        //name과 email로 계정 존재 유뮤 확인
        if(!userRepository.existsByNameAndEmail(findPasswordForm.getName(), findPasswordForm.getEmail()))
            throw new RuntimeException();

        //SMTP를 사용해 사용자의 이메일에 인증번호 전송
        /*
        *
        */

        //랜덤 비밀번호 생성
        UUID uid = UUID.randomUUID();
        String tempPassword = uid.toString().substring(10); //비밀번호 10자리

        //새 비밀번호 암호화 후 DB저장
        userRepository.insertPassword(findPasswordForm.getEmail(), passwordEncoder.encode(tempPassword));

        //임시비밀번호 반환
        return tempPassword;
    }

    @Override
    public void deleteUser(String email) throws Exception {
        User userId = userRepository.findByEmail(email).
                orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        userRepository.deleteByUserId(userId.getUserId());
    }
}
