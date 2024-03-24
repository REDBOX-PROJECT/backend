package fx.redbox.controller.donorCard.form;

import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.RejectPermission;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonorCardRequestReviewCheckForm {

    private RejectPermission rejectPermission; //승인 거절 심사중
    private String donorCardRequestRejectReason;

}
