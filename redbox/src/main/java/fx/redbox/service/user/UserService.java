package fx.redbox.service.user;

import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserService {

    void signUp(UserAccount userAccount, UserInfo userInfo, User user);

    Optional<User> findByUserId(Long userId);

    List<User> findAll();

    Long update(Long userId, User user);
}
