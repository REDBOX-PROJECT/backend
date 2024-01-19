package fx.redbox.entity.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccount {

    private Long accountId;
    private String email;
    private String password;
}
