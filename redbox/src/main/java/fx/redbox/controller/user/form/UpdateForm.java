package fx.redbox.controller.user.form;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UpdateForm {

//    @Past
    private Date birth;

//    @Pattern(regexp = "\\d{3}-\\d{4}-\\d{4}")
    private String phone;

//    @Size(min=2, max=30)
    private String address;
}
