package fx.redbox.service;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import fx.redbox.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

@SpringBootTest
class UserServiceTest {

    private final UserService userService;

    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void signUp() {

        UserAccount userAccount = new UserAccount();
        userAccount.setEmail("user1@fx.com");
        userAccount.setPassword("1234");

        UserInfo userInfo = new UserInfo();
        userInfo.setPhone("010-1111-1111");
        userInfo.setAddress("SEOUL");

        User user = new User();
        user.setName("KIM");
        user.setBirth(Date.valueOf("2001-01-01"));
        user.setGender(Gender.남);
        user.setBloodType(BloodType.B);

        userService.signUp(userAccount, userInfo, user);

    }
}