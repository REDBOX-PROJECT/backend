package fx.redbox.controller.user.form;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInForm {

    //UserAccount
    private String email;
    private String password;
}
