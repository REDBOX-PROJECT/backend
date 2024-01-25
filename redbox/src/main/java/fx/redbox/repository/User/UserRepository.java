package fx.redbox.repository.User;

import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save (UserAccount userAccount, UserInfo userInfo, User user);

    Optional<User> findByUserId(Long userId);

    List<User> findAll();

}
