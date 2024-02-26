package fx.redbox.controller.user.form;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.ValidEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class SignUpForm {

    //User
    @NotBlank
    @Size(min=2, max=8, message="이름은 2~8자 입니다.")
    private String name;

    @Past
    private Date birth; //YYYY-MM-DD

    @ValidEnum(enumClass = Gender.class)
    private Gender gender;

    @ValidEnum(enumClass = BloodType.class)
    private BloodType bloodType = BloodType.NULL;

    //UserAccount
    @Email
    private String email;

    @Size(min=5, max=20)
    private String password;

    //UserInfo
    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
    private String phone;

    @Size(min=2, max=30)
    private String address;
}
