package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.RejectPermission;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Request {
    private Long requestId;
    private RejectPermission requestPermission;
    private String rejectionReason;
    private Long userId;
    private RequestForm requestForm; // RequestForm 필드 추가
}
