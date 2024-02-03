package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.RejectPermission;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DonorCardRequest {
    private Long donorCardRequestId;
    private RejectPermission donorCardRequestPermission;
    private DonorCardRequestRejectReason donorCardRequestRejectReason;
    private Long userId;
    private DonorCardRequestForm donorCardRequestForm; // RequestForm 필드 추가
}
