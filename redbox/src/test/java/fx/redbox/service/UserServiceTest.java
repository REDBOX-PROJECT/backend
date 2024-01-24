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

import java.sql.Date;
import java.util.Optional;

@SpringBootTest
class UserServiceTest {

    private final UserService userService;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void signUp() {

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
}