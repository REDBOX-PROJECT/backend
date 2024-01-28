package fx.redbox.controller.user.form;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.Grade;
import fx.redbox.entity.enums.Permission;
import fx.redbox.entity.users.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@Builder
public class UserInfoForm {
    //userId, 이메일, 이름, 생일, 성별, 혈액형, 등급, 휴대폰, 기부횟수, 권한
    private Long userId;
    private String email;
    private String name;
    private Date birth;
    private Gender gender;
    private BloodType bloodType;
    private Grade grade;
    private String phone;
    private int donationCount;
    private Permission permission;

}
