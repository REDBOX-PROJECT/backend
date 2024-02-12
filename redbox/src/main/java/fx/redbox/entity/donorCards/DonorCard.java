package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.DonorBloodKind;
import fx.redbox.entity.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class DonorCard {
    private String certificateNumber;
    private String donorName;
    private Date donorBirth;
    private DonorBloodKind donorBloodKind;
    private Gender donorGender;
    private String bloodCenter;
    private Long userId;

}
