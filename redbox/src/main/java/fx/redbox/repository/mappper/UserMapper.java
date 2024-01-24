package fx.redbox.repository.mappper;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.Grade;
import fx.redbox.entity.enums.Permission;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = User.builder()
                .userId(rs.getLong("user_id"))
                .name(rs.getString("name"))
                .birth(rs.getDate("birth"))
                .gender(Gender.valueOf(rs.getString("gender"))) //valueof 를 사용해 enum 타입으로 변환
                .bloodType(BloodType.valueOf(rs.getString("blood_type")))
                .grade(Grade.valueOf(rs.getString("grade")))
                .accountId(rs.getLong("account_id"))
                .userInfoId(rs.getLong("user_info_id"))
                .build();

        UserAccount userAccount = UserAccount.builder()
                .accountId(rs.getLong("account_id"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .build();
        user.setUserAccount(userAccount);


        UserInfo userInfo = UserInfo.builder()
                .userInfoId(rs.getLong("user_info_id"))
                .phone(rs.getString("phone"))
                .address(rs.getString("address"))
                .donationCount(rs.getInt("donation_count"))
                .permission(Permission.valueOf(rs.getString("permission")))
                .build();
        user.setUserInfo(userInfo);

        return user;
    }
}
