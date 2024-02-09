package fx.redbox.service.user;

import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import fx.redbox.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserAccount userAccount, UserInfo userInfo, User user) {
        userAccount.setPassword(passwordEncoder.encrypt(userAccount.getPassword()));
        if(userRepository.existsByEmail(userAccount.getEmail()))
            return; //예외처리하기!

        userRepository.save(userAccount, userInfo, user);
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
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
