package fx.redbox.controller.user.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordForm {

//    @NotBlank
//    @Size(min=2, max=8, message="이름은 2~8자 입니다.")
    private String name;

//    @Email
    private String email;
}
