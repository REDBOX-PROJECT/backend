package fx.redbox.service.user;

import fx.redbox.controller.user.form.*;
import fx.redbox.entity.users.User;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Transactional
public interface UserService {

    boolean signUp(SignUpForm signUpForm);
    User signIn(SignInForm signInForm);
    UserInfoForm getUser(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long userId);
    String getEmail(FindMailForm findMailForm);
    void editUserInfo(User user, UpdateForm updateForm);
    String findPassword(FindPasswordForm findPasswordForm);
    void deleteUser(User user);
    Boolean duplicateEmailCheck(String email);
}