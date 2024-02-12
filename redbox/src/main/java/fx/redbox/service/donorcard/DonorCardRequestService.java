package fx.redbox.service.donorcard;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.RejectPermission;

import java.util.List;
import java.util.Optional;

public interface DonorCardRequestService {

    DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm);

    Optional<DonorCardRequest> getDonorCardRequestById(Long donorCardRequestId);

    List<DonorCardRequest> getAllDonorCardRequests();

    void updateDonorCardRequest(Long donorCardRequestId, RejectPermission donorCardRequestPermission, DonorCardRequestRejectReason donorCardRequestRejectReason);

    void updateDonorCardRequestForm(Long donorCardRequestId, String evidenceDocument);

    void deleteDonorCardRequest(Long donorCardRequestId);

    void acceptDonorCardRequest(Long donorCardRequestId);

}
