package fx.redbox.controller.donorCard.form;

import fx.redbox.entity.enums.RejectPermission;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DonorCardRequestReviewForm {

    private String requestName;
    private String patientName;
    private String hospitalName;
    private String evidenceDocument;
    private RejectPermission rejectPermission;
}
