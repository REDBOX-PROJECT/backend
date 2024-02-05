package fx.redbox.controller.user.form;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class SignUpForm {

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

// 메모리
// java 변수 선언할때 Memory 일어나는 일
// 공간 복잡도 위험