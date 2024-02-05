package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.RejectPermission;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonorCardRequest {
    private Long donorCardRequestId;
    private RejectPermission donorCardRequestPermission;
    private DonorCardRequestRejectReason donorCardRequestRejectReason;
    private Long userId;
    private DonorCardRequestForm donorCardRequestForm; // RequestForm 필드 추가
}
