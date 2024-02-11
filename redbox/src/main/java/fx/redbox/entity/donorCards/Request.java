package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.RejectPermission;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    private Long requestId;
    private RejectPermission requestPermission;
    private String rejectionReason;
    private Long userId;
}
