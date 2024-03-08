package fx.redbox.controller.user.form;

import fx.redbox.entity.users.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindMailForm {

//    @NotBlank
//    @Size(min=2, max=8, message="이름은 2~8자 입니다.")
    private String name;

//    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
    private String phone;

    public FindMailForm(User user) {
        this.name = user.getName();
        this.phone = user.getUserInfo().getPhone();
    }
}


// Mail
// Phone , 이름

// password
// mail , 이름6