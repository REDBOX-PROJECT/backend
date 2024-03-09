package fx.redbox.repository.donorCardRequest;

import fx.redbox.entity.donorCards.DonorCardRequestForm;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorCardRequestRepository {

    void saveDonorCardRequestForm(DonorCardRequestForm donorCardRequestForm);

    List<DonorCardRequestForm> getAllDonorCardRequests();

    Optional<DonorCardRequestForm> getDonorCardRequestByDonorCardRequestId(Long donorCardRequestId);

    void updateDonorCardRequestReview(Long donorCardRequestId, String donorCardRequestPermission, String donorCardRequestRejectReason);

    Boolean existsByUserId(Long userId);
}
