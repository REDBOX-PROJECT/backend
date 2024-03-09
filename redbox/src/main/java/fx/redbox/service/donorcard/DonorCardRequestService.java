package fx.redbox.service.donorCard;

import fx.redbox.controller.donorCard.form.DonorCardRequestDto;
import fx.redbox.controller.donorCard.form.DonorCardRequestListForm;
import fx.redbox.controller.donorCard.form.DonorCardRequestReviewCheckForm;
import fx.redbox.controller.donorCard.form.DonorCardRequestReviewForm;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.RejectPermission;

import java.util.List;
import java.util.Optional;

public interface DonorCardRequestService {

    void saveDonorCardRequest(String email, DonorCardRequestDto donorCardRequestDto);

    List<DonorCardRequestListForm> showAllDonorCardRequests();

    DonorCardRequestReviewForm showDonorCardRequestReview(Long donorCardRequestId);

    void updateDonorCardRequest(Long donorCardRequestId, DonorCardRequestReviewCheckForm donorCardRequestReviewCheckForm);

/*


    void updateDonorCardRequestForm(Long donorCardRequestId, String evidenceDocument);

    void deleteDonorCardRequest(Long donorCardRequestId);

    void acceptDonorCardRequest(Long donorCardRequestId);
 */

}
