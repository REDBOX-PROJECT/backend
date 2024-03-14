package fx.redbox.controller.donorCard.form;

import fx.redbox.entity.enums.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class DonorCardRequestDto {

    private String patientName;
    private LocalDate birth;
    private Gender gender;
    private String hospitalName;
    private String evidenceDocument;

}
