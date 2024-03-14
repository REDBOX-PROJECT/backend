package fx.redbox.repository.user;

import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User saveUser (UserAccount userAccount, UserInfo userInfo, User user);

    Optional<User> findByUserId(Long userId);

    Optional<User> findByEmail(String email);

    List<User> findAllUser();

    void update(Long userId, LocalDate birth, String phone, String address);

    void deleteByUserId(Long userId);

    boolean existsByEmail(String email);

    Optional<User> findEmail(String name, String phone);
    boolean existsByNameAndEmail(String name, String email);
    void insertPassword(String email, String password);


}
