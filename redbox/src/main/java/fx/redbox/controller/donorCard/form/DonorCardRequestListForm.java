package fx.redbox.controller.donorCard.form;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.RejectPermission;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonorCardRequestListForm {

    private Long donorCardRequestId;
    private String patientName;
    private BloodType bloodType;
    private RejectPermission permission;

}
