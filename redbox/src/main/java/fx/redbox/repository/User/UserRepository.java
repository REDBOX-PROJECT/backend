package fx.redbox.repository.User;

import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;

public interface UserRepository {
    User save (UserAccount userAccount, UserInfo userInfo, User user);

}
