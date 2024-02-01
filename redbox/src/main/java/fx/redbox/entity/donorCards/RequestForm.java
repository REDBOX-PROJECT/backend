package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class RequestForm {
    private Long requestId;
    private String patientName;
    private String evidenceDocument;
    private Gender patientGender;
    private BloodType bloodType;
    private Timestamp requestDate;
}
