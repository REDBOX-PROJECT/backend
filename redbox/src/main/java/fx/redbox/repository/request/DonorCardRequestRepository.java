package fx.redbox.repository.request;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;

import java.util.List;

public interface RequestRepository {

    DonorCardRequest createDonorCardRequest(DonorCardRequest request, DonorCardRequestForm requestForm);

    DonorCardRequest getDonorCardRequestById(String requestId);

    List<DonorCardRequest> getAllDonorCardRequests();

}
