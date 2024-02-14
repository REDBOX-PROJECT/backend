package fx.redbox.controller.user.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInForm {

    //UserAccount
    @Email
    private String email;

    @Size(min=5, max=20)
    private String password;
}
