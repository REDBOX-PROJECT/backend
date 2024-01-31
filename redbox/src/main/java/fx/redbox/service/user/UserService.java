package fx.redbox.service.user;

import fx.redbox.controller.user.form.UserForm;
import fx.redbox.controller.user.form.UserInfoForm;
import fx.redbox.entity.users.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserService {

    boolean signUp(SignRequestForm signRequestForm);
    Optional<User> findByUserId(Long userId);

    List<UserInfoForm> findAll();

    Long update(Long userId, User user);

    void deleteByUserId(Long userId);
}
