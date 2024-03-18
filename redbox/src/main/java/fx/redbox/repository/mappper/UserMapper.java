package fx.redbox.repository.mappper;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.Grade;
import fx.redbox.entity.enums.Permission;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
public class UserMapper implements RowMapper<User> {

    @Value("${user.column.user_id}") private String userId;
    @Value("${user.column.name}") private String name;
    @Value("${user.column.birth}") private String birth;
    @Value("${user.column.gender}") private String gender;
    @Value("${user.column.blood_type}") private String bloodType;
    @Value("${user.column.grade}") private String grade;
    @Value("${user.column.account_id}") private String accountId;
    @Value("${user.column.user_info_id}") private String userInfoId;
    @Value("${user.column.email}") private String email;
    @Value("${user.column.password}") private String password;
    @Value("${user.column.phone}") private String phone;
    @Value("${user.column.address}") private String address;
    @Value("${user.column.donation_count}") private String donationCount;
    @Value("${user.column.permission}") private String permission;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = User.builder()
                .userId(rs.getLong(userId))
                .name(rs.getString(name))
                .birth(LocalDate.parse(rs.getString(birth)))
                .gender(Gender.valueOf(rs.getString(gender)))
                .bloodType(BloodType.valueOf(rs.getString(bloodType)))
                .grade(Grade.valueOf(rs.getString(grade)))
                .accountId(rs.getLong(accountId))
                .userInfoId(rs.getLong(userInfoId))
                .build();

        UserAccount userAccount = UserAccount.builder()
                .accountId(rs.getLong(accountId))
                .email(rs.getString(email))
                .password(rs.getString(password))
                .build();
        user.setUserAccount(userAccount);

        UserInfo userInfo = UserInfo.builder()
                .userInfoId(rs.getLong(userInfoId))
                .phone(rs.getString(phone))
                .address(rs.getString(address))
                .donationCount(rs.getInt(donationCount))
                .permission(Permission.valueOf(rs.getString(permission)))
                .build();
        user.setUserInfo(userInfo);

        return user;
    }
}