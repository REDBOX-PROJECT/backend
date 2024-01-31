package fx.redbox.entity.users;

import fx.redbox.entity.enums.Permission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@ToString
@Builder
public class UserInfo implements GrantedAuthority {

    private Long userInfoId;
    private String phone;
    private String address;
    private int donationCount;
    private Permission permission;

    @Override
    public String getAuthority() {
        return permission.name();
    }
}
