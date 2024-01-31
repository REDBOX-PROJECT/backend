package fx.redbox.service.user;

import fx.redbox.controller.user.form.UserForm;
import fx.redbox.controller.user.form.UserInfoForm;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import fx.redbox.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

                .build();

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
