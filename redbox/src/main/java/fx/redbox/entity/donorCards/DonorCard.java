package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.DonorBloodKind;
import fx.redbox.entity.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class DonorCard {
    private String certificateNumber;
    private String donorName;
    private LocalDate donorBirth;
    private DonorBloodKind donorBloodKind;
    private LocalDate donationDate;
    private Gender donorGender;
    private String bloodCenter;
    private Long userId;
    private BloodType bloodType;
}
