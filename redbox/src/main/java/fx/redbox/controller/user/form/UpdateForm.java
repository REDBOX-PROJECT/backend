package fx.redbox.controller.user.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UpdateForm {
    private Date birth;
    private String phone;
    private String address;
}
