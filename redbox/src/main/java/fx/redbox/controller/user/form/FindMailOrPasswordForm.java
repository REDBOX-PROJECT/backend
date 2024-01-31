package fx.redbox.controller.user.form;

import fx.redbox.entity.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindMailOrPasswordForm {

    private String name;
    private String phone;
    private String email;

    public FindMailOrPasswordForm(User user) {
        this.name = user.getName();
        this.phone = user.getUserInfo().getPhone();
        this.email = user.getUserAccount().getEmail();
    }
}
