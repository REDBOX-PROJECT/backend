package fx.redbox.service.donorcard;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;

import java.util.List;

public interface DonorCardRequestService {

    DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm);

    DonorCardRequest getDonorCardRequestById(String donorCardRequestId);

    List<DonorCardRequest> getAllDonorCardRequests();

    DonorCardRequest updateDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm);

    void deleteDonorCardRequest(String donorCardRequestId);

    void acceptDonorCardRequest(String donorCardRequestId);

}
