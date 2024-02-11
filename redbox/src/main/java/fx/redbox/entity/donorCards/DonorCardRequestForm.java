package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class DonorCardRequestForm {
    private Long donorCardRequestId;
    private String patientName;
    private String evidenceDocument;
    private Gender patientGender;
    private BloodType bloodType;
    private Timestamp donorCardRequestDate;
}
