package fx.redbox.controller.user.form;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserForm {

    //User
    private String name;
    private Date birth; //YYYY-MM-DD
    private Gender gender;
    private BloodType bloodType = BloodType.NULL;

    //UserAccount
    private String email;
    private String password;

    //UserInfo
    private String phone;
    private String address;
}
