package fx.redbox.controller.user.form;

import fx.redbox.entity.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindMailForm {

    private String name;
    private String phone;

    public FindMailForm(User user) {
        this.name = user.getName();
        this.phone = user.getUserInfo().getPhone();
    }
}


// Mail
// Phone , 이름

// password
// mail , 이름