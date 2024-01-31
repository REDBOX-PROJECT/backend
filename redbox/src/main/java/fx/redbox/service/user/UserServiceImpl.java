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

        userAccount.setPassword(passwordEncoder.encrypt(userAccount.getPassword()));
        if(userRepository.existsByEmail(userAccount.getEmail()))
            throw new RuntimeException();

        userRepository.save(userAccount, userInfo, user);
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<UserInfoForm> findAll() {
        List<User> userList = userRepository.findAll();
        List<UserInfoForm> userInfoList = new ArrayList<>();

        for (User user : userList) {
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

            userInfoList.add(userInfo);
        }

        return userInfoList;
    }

    @Override
    public Long update(Long userId, User user) {
        String encryptedPassword = passwordEncoder.encrypt(user.getUserAccount().getPassword());
        user.getUserAccount().setPassword(encryptedPassword);
        return userRepository.update(userId, user);
    }

    @Override
    public void deleteByUserId(Long userId) {
        userRepository.deleteByUserId(userId);
    }

}
