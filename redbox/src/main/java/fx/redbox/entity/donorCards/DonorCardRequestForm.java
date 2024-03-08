package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class DonorCardRequestForm {
    private Long donorCardRequestId;
    private String patientName;
    private String evidenceDocument;
    private LocalDate birth;
    private Gender patientGender;
    private String hospitalName;
    private BloodType bloodType;
    private LocalDateTime donorCardRequestDate;
    private Long userId;

}
