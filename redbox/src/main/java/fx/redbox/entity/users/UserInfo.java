package fx.redbox.entity.users;

import fx.redbox.entity.enums.Permission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserInfo {

    private Long userInfoId;
    private String phone;
    private String address;
    private int donationCount;
    private Permission permission;

}
