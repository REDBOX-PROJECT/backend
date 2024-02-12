package fx.redbox.repository.donorCardRequest;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.RejectPermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorCardRequestRepository {

    DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm);

    Optional<DonorCardRequest> getDonorCardRequestById(Long donorCardRequestId);

    List<DonorCardRequest> getAllDonorCardRequests();

    void updateDonorCardRequest(Long donorCardRequestId, RejectPermission donorCardRequestPermission, DonorCardRequestRejectReason donorCardRequestRejectReason);

    void updateDonorCardRequestForm(Long donorCardRequestId, String evidenceDocument);

    void deleteDonorCardRequest(Long donorCardRequestId);
}
