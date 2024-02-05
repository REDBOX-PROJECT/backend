package fx.redbox.service.user;

import fx.redbox.controller.user.form.*;
import fx.redbox.entity.users.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserService {

    boolean signUp(SignUpForm signUpForm);
    SignInForm signIn(SignInForm signInForm);
    UserInfoForm getUser(String email) throws Exception;
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long userId);
    String getEmail(FindMailForm findMailForm) throws Exception;
    void editUserInfo(String email, UpdateForm updateForm) throws Exception;
    String findPassword(FindPasswordForm findPasswordForm);
    void deleteUser(String email) throws Exception;
}