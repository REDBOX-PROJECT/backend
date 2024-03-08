package fx.redbox.service.donorCard;

import fx.redbox.controller.donorCard.form.DonorCardRequestDto;
import fx.redbox.controller.donorCard.form.DonorCardRequestListForm;
import fx.redbox.entity.donorCards.DonorCardRequestForm;

import java.util.List;
import java.util.Optional;

public interface DonorCardRequestService {

    void saveDonorCardRequest(String email, DonorCardRequestDto donorCardRequestDto);

    List<DonorCardRequestListForm> showAllDonorCardRequests();

    Optional<DonorCardRequestForm> getDonorCardRequest(String email);
/*

    void updateDonorCardRequest(Long donorCardRequestId, RejectPermission donorCardRequestPermission, DonorCardRequestRejectReason donorCardRequestRejectReason);

    void updateDonorCardRequestForm(Long donorCardRequestId, String evidenceDocument);

    void deleteDonorCardRequest(Long donorCardRequestId);

    void acceptDonorCardRequest(Long donorCardRequestId);
 */

}
