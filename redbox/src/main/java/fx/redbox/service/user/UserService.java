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
    SignResponseForm getUser(String email) throws Exception;
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long userId);
    FindMailOrPasswordForm getEmail(FindMailOrPasswordForm findMailOrPasswordForm) throws Exception;
    void update(String email, UpdateForm updateForm) throws Exception;
    String findPassword(FindMailOrPasswordForm findPasswordForm);
    void deleteUser(String email) throws Exception;
}