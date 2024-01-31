package fx.redbox.controller.user.form;

import fx.redbox.entity.enums.Permission;
import fx.redbox.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponseForm {
    private Long userId;

    private String email;

    private String name;

    private Permission permission;

    private String token;


    public SignResponseForm(User user) {
        this.userId = user.getUserId();
        this.email = user.getUserAccount().getEmail();
        this.name = user.getName();
        this.permission = user.getUserInfo().getPermission();
    }
}
