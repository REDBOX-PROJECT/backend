package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.RejectPermission;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import lombok.*;

@Getter
@Builder
public class DonorCardRequestApproval {
    private Long donorCardRequestId;
    private RejectPermission donorCardRequestPermission;
    private String donorCardRequestRejectReason;

}
