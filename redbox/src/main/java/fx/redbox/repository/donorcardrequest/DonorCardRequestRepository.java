package fx.redbox.repository.donorcardrequest;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;

import java.util.List;

public interface DonorCardRequestRepository {

    DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm);

    DonorCardRequest getDonorCardRequestById(String donorCardRequestId);

    List<DonorCardRequest> getAllDonorCardRequests();

    DonorCardRequest updateDonorCardRequest(DonorCardRequest donorCardRequest);

}
