package fx.redbox.repository.donorcardrequest;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonorCardRequestRepository {

    DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm);

    DonorCardRequest getDonorCardRequestById(String donorCardRequestId);

    List<DonorCardRequest> getAllDonorCardRequests();

    DonorCardRequest updateDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm);

    void deleteDonorCardRequest(String donorCardRequestId);
}
