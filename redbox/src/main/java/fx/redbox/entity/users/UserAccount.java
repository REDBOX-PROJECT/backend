package fx.redbox.entity.users;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserAccount {

    private Long accountId;
    private String email;
    private String password;
}
