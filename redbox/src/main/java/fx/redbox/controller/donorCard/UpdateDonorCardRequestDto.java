package fx.redbox.controller.donorCard;

import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.RejectPermission;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateDonorCardRequestDto {
    private RejectPermission donorCardRequestPermission;
    private DonorCardRequestRejectReason donorCardRequestRejectReason;

}
