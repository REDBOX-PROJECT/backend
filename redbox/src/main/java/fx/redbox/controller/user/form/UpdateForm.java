package fx.redbox.controller.user.form;

import lombok.Getter;

import java.sql.Date;

@Getter
public class UpdateForm {
    private Date birth;
    private String phone;
    private String address;
}
