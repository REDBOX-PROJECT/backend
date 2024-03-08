package fx.redbox.controller.user.form;

import fx.redbox.entity.enums.Permission;
import fx.redbox.entity.users.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoForm {

    // 의도가 분명할 것
    // 그릇된 정보를 제공하지 말것
    // 한가지 개념의 단어를 사용할 것
    // 일관성을 유지할것

    private Long userId;

//    @Email
    private String email;

//    @NotBlank
//    @Size(min=2, max=8, message="이름은 2~8자 입니다.")
    private String name;

    private Permission permission;


    public UserInfoForm(User user) {
        this.userId = user.getUserId();
        this.email = user.getUserAccount().getEmail();
        this.name = user.getName();
        this.permission = user.getUserInfo().getPermission();
    }
}
