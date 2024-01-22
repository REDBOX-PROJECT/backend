package fx.redbox.service.user;

import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

    void signUp(UserAccount userAccount, UserInfo userInfo, User user);

}
