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
    public void signUp(UserForm userForm) {
        User user = User.builder()
                .name(userForm.getName())
                .birth(userForm.getBirth())
                .gender(userForm.getGender())
                .bloodType(userForm.getBloodType())
                .build();
        UserAccount userAccount = UserAccount.builder()
                .email(userForm.getEmail())
                .password(userForm.getPassword())
                .build();
        UserInfo userInfo = UserInfo.builder()
                .phone(userForm.getPhone())
                .address(userForm.getAddress())
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
