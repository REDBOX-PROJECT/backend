package fx.redbox.service.user;

import fx.redbox.controller.user.form.FindMailOrPasswordForm;
import fx.redbox.controller.user.form.SignRequestForm;
import fx.redbox.controller.user.form.SignResponseForm;
import fx.redbox.controller.user.form.UpdateForm;
import fx.redbox.entity.users.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserService {

    boolean signUp(SignRequestForm signRequestForm);
    SignResponseForm signIn(SignRequestForm signRequestForm) throws Exception;
    Optional<User> findByUserId(Long userId);

    List<UserInfoForm> findAll();

    Long update(Long userId, User user);

    void deleteUser(String email) throws Exception;
}