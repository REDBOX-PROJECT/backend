package fx.redbox.service.donorCard;

import fx.redbox.controller.donorCard.form.DonorCardRequestDto;
import fx.redbox.controller.donorCard.form.DonorCardRequestListForm;
import fx.redbox.controller.donorCard.form.DonorCardRequestReviewCheckForm;
import fx.redbox.controller.donorCard.form.DonorCardRequestReviewForm;

import java.util.List;

public interface DonorCardRequestService {

    void saveDonorCardRequest(Long userId, DonorCardRequestDto donorCardRequestDto);

    List<DonorCardRequestListForm> showAllDonorCardRequests();

    DonorCardRequestReviewForm showDonorCardRequestReview(Long donorCardRequestId);

    void updateDonorCardRequest(Long donorCardRequestId, DonorCardRequestReviewCheckForm donorCardRequestReviewCheckForm);

}
