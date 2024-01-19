package fx.redbox.entity.users;

import fx.redbox.entity.enums.Permission;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {

    private Long userInfoId;
    private String phone;
    private String address;
    private int donationCount;
    private Permission permission;
}
