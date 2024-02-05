package fx.redbox.controller.donorCard;

import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.RejectPermission;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class DonorCardRequestDto {

    // DonorCardRequest
    private RejectPermission donorCardRequestPermission;
    private DonorCardRequestRejectReason donorCardRequestRejectReason;
    private Long userId;
    private DonorCardRequestForm donorCardRequestForm;

    // DonorCardRequestForm
    private String patientName;
    private String evidenceDocument;
    private Gender patientGender;
    private BloodType bloodType;
    private Timestamp donorCardRequestDate;
}
