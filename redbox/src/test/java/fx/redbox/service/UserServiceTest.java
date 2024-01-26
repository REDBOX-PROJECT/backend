package fx.redbox.service;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import fx.redbox.service.user.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceTest {

    private final UserService userService;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void signUp() throws NoSuchAlgorithmException {

        UserAccount userAccount = UserAccount.builder()
                .email("user1@fx.com")
                .password("1234")
                .build();

        UserInfo userInfo = UserInfo.builder()
                .phone("010-1111-1111")
                .address("SEOUL")
                .build();

        User user = User.builder()
                .name("KIM")
                .birth(Date.valueOf("2001-01-01"))
                .gender(Gender.남)
                .bloodType(BloodType.B)
                .build();


        userService.signUp(userAccount, userInfo, user);

    }

    @Test
    void findByUserId() {
        Optional<User> findUser = userService.findByUserId(1l);
        System.out.println("findUser = " + findUser);
        Assertions.assertThat(findUser.get().getUserId()).isEqualTo(1L);
    }

    @Test
    void findAll() {
        List<User> allStudent = userService.findAll();
        System.out.println("allStudent = " + allStudent);
        Assertions.assertThat(allStudent.size()).isEqualTo(4);
    }

    @Test
    void update() {
        Long updateUserId = 1L;
        User user = User.builder()
                .userId(updateUserId)
                .userAccount(UserAccount.builder().password("newPassword").build())
                .userInfo(UserInfo.builder().phone("010-2222-2222").address("INCHEON").build())
                .build();

        Long updatedId = userService.update(updateUserId, user);

        Assertions.assertThat(updatedId).isEqualTo(1L);
    }

    @Test
    void deleteByUserId() {
        Long deleteUserId = 2L;
        int beforeSize = userService.findAll().size();

        userService.deleteByUserId(deleteUserId);
        int afterSize = userService.findAll().size();

        Assertions.assertThat(afterSize).isEqualTo(beforeSize-1);
    }

}