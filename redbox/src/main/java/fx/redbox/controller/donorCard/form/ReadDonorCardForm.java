package fx.redbox.controller.donorCard.form;

import fx.redbox.entity.enums.DonorBloodKind;
import fx.redbox.entity.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReadDonorCardForm {

    private String certificateNumber;
    private String donorName;
    private LocalDate donorBirth;
    private DonorBloodKind donorBloodKind;
    private LocalDate donationDate;
    private Gender donorGender;
    private String bloodCenter;

}
