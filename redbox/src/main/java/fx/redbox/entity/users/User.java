package fx.redbox.entity.users;


import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.Grade;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class User {

    private Long userId;
    private String name;
    private Date birth; //YYYY-MM-DD
    private Gender gender;
    private BloodType bloodType;
    private Grade grade;
    private Long accountId;
    private Long userInfoId;

}
