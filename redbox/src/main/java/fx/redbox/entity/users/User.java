package fx.redbox.entity.users;


import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.Grade;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;


@Getter
@Setter
@Builder
public class User {

    private Long userId;
    private String name;
    private LocalDate birth; //YYYY-MM-DD
    private Gender gender;
    private BloodType bloodType;
    private Grade grade;
    private Long accountId;
    private Long userInfoId;

    private UserAccount userAccount;
    private UserInfo userInfo;

}
