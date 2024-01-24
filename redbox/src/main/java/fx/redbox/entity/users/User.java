package fx.redbox.entity.users;


import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.Grade;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@Builder
public class User {

    private Long userId;
    private String name;
    private Date birth; //YYYY-MM-DD
    private Gender gender;
    private BloodType bloodType;
    private Grade grade;
    private Long accountId;
    private Long userInfoId;

    private UserAccount userAccount;
    private UserInfo userInfo;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", gender=" + gender +
                ", bloodType=" + bloodType +
                ", grade=" + grade +
                ", accountId=" + accountId +
                ", email='" + userAccount.getEmail() + '\'' +
                ", password='" + userAccount.getPassword() + '\'' +
                ", userInfoId=" + userInfoId +
                ", phone='" + userInfo.getPhone() + '\'' +
                ", address='" + userInfo.getAddress() + '\'' +
                ", donationCount=" + userInfo.getDonationCount() +
                ", permission=" + userInfo.getPermission() +
                '}';
    }
}
