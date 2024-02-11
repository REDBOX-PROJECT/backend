package fx.redbox.controller.donorCard;

import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.RejectPermission;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class UpdateDonorCardRequestDto {
    private RejectPermission donorCardRequestPermission;
    private DonorCardRequestRejectReason donorCardRequestRejectReason;

}
