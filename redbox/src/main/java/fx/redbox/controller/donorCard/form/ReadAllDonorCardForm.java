package fx.redbox.controller.donorCard.form;

import fx.redbox.entity.enums.DonorBloodKind;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReadAllDonorCardForm {

    private String certificateNumber;
    private DonorBloodKind donorBloodKind;
    private LocalDate donationDate;
    private String bloodCenter;
}
