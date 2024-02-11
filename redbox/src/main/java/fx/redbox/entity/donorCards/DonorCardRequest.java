package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.RejectPermission;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import lombok.*;

@Getter
@Setter
@Builder
public class DonorCardRequest {
    private Long donorCardRequestId;
    private RejectPermission donorCardRequestPermission;
    private DonorCardRequestRejectReason donorCardRequestRejectReason;
    private Long userId;
    private Long donorCardRequestFormId;

    private DonorCardRequestForm donorCardRequestForm;
}
