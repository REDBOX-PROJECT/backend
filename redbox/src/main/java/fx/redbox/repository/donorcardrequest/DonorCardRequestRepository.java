package fx.redbox.repository.donorcardrequest;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;

import java.util.List;

public interface DonorCardRequestRepository {

    DonorCardRequest createDonorCardRequest(DonorCardRequest request, DonorCardRequestForm requestForm);

    DonorCardRequest getDonorCardRequestById(String requestId);

    List<DonorCardRequest> getAllDonorCardRequests();

}
