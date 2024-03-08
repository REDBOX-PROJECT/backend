package fx.redbox.service.donorCard;

import fx.redbox.controller.donorCard.form.DonorCardRequestDto;
import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.RejectPermission;

import java.util.List;
import java.util.Optional;

public interface DonorCardRequestService {

    void saveDonorCardRequest(String email, DonorCardRequestDto donorCardRequestDto);
/*
    Optional<DonorCardRequest> getDonorCardRequestById(Long donorCardRequestId);

    List<DonorCardRequest> getAllDonorCardRequests();

    void updateDonorCardRequest(Long donorCardRequestId, RejectPermission donorCardRequestPermission, DonorCardRequestRejectReason donorCardRequestRejectReason);

    void updateDonorCardRequestForm(Long donorCardRequestId, String evidenceDocument);

    void deleteDonorCardRequest(Long donorCardRequestId);

    void acceptDonorCardRequest(Long donorCardRequestId);

}
