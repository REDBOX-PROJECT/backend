package fx.redbox.service.user;

import fx.redbox.common.Exception.DuplicateEmailException;
import fx.redbox.controller.user.form.*;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.users.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.Optional;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    SignUpForm signUpForm = new SignUpForm();
    @BeforeEach
    void beforeEach() {
        signUpForm.setName("testName");
        signUpForm.setBirth(Date.valueOf("2000-01-01"));
        signUpForm.setGender(Gender.남);
        signUpForm.setEmail("testEmail");
        signUpForm.setPassword("testPassword");
        signUpForm.setPhone("010-0000-0000");
        signUpForm.setAddress("충청북도 충주시");

        userService.signUp(signUpForm);
    }

    @AfterEach
    void afterEach() {
        userService.deleteUser("testEmail");
    }

    @Test
    void 회원가입() {
        Assertions.assertThat(signUpForm.getEmail()).isEqualTo("testEmail");
        Assertions.assertThat(signUpForm.getPassword()).isEqualTo("testPassword");
    }

    @Test
    void 중복회원가입() {
        //예외 발생하면 성공
        Assertions.assertThatThrownBy(() -> userService.signUp(signUpForm))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void 로그인() {

        SignInForm signInForm = SignInForm.builder()
                .email("testEmail")
                .password("testPassword")
                .build();

        //예외 발생하지 않으면 성공
        Assertions.assertThatNoException()
                .isThrownBy(() -> userService.signIn(signInForm));
    }

    @Test
    void getUser() {

        UserInfoForm user = userService.getUser("testEmail");

        Assertions.assertThat(user.getEmail()).isEqualTo("testEmail");
        Assertions.assertThat(user.getName()).isEqualTo("testName");
    }

    @Test
    void findByEmail() {
        Optional<User> findByEmail = userService.findByEmail("testEmail");

        User user = findByEmail.get();

        Assertions.assertThat(user.getName()).isEqualTo("testName");
        Assertions.assertThat(user.getUserAccount().getEmail()).isEqualTo("testEmail");
        Assertions.assertThat(user.getUserAccount().getPassword()).isEqualTo("testPassword");
    }

    @Test
    void findByUserId() {
    }

    @Test
    void getEmail() {
        FindMailForm findMailForm = new FindMailForm();
        findMailForm.setName("testName");
        findMailForm.setPhone("010-0000-0000");

        String email = userService.getEmail(findMailForm);

        Assertions.assertThat(email).isEqualTo("testEmail");
    }

    @Test
    void editUserInfo() {
        UpdateForm updateForm = new UpdateForm();
        updateForm.setBirth(Date.valueOf("2002-12-12"));
        updateForm.setPhone("010-1111-1111");
        updateForm.setAddress("서울시 중구");

        userService.editUserInfo("testEmail", updateForm);

        Optional<User> tesMail = userService.findByEmail("testEmail");
        User editedUser = tesMail.get();

        Assertions.assertThat(editedUser.getBirth()).isEqualTo("2002-12-12");
        Assertions.assertThat(editedUser.getUserInfo().getPhone()).isEqualTo("010-1111-1111");
        Assertions.assertThat(editedUser.getUserInfo().getAddress()).isEqualTo("서울시 중구");
    }


    @Test
    void findPassword() {
        FindPasswordForm findPasswordForm = new FindPasswordForm();
        findPasswordForm.setName("testName");
        findPasswordForm.setEmail("testEmail");

        //임의 변경된 패스워드
        String password = userService.findPassword(findPasswordForm);

        Optional<User> testEmail = userService.findByEmail("testEmail");
        User changePassword = testEmail.get();

        Assertions.assertThat(password).isEqualTo(changePassword.getUserAccount().getPassword());
    }

    @Test
    void deleteUser() {
    }
}