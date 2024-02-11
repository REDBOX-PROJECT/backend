package fx.redbox.service.donorcard;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.RejectPermission;
import fx.redbox.repository.donorCardRequest.DonorCardRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonorCardRequestServiceImpl implements DonorCardRequestService {

    private final DonorCardRequestRepository donorCardRequestRepository;

    @Override
    public DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm) {
        return donorCardRequestRepository.createDonorCardRequest(donorCardRequest, donorCardRequestForm);
    }

    @Override
    public Optional<DonorCardRequest> getDonorCardRequestById(Long donorCardRequestId) {
        return donorCardRequestRepository.getDonorCardRequestById(Long.valueOf(donorCardRequestId));
    }

    @Override
    public List<DonorCardRequest> getAllDonorCardRequests() {
        return donorCardRequestRepository.getAllDonorCardRequests();
    }

    @Override
    public void updateDonorCardRequest(Long donorCardRequestId, RejectPermission donorCardRequestPermission, DonorCardRequestRejectReason donorCardRequestRejectReason) {
        donorCardRequestRepository.updateDonorCardRequest(donorCardRequestId, donorCardRequestPermission, donorCardRequestRejectReason);
    }

    @Override
    public void updateDonorCardRequestForm(Long donorCardRequestId, String evidenceDocument) {
        donorCardRequestRepository.updateDonorCardRequestForm(donorCardRequestId, evidenceDocument);
    }

    @Override
    public void deleteDonorCardRequest(String donorCardRequestId) {
        donorCardRequestRepository.deleteDonorCardRequest(Long.valueOf(donorCardRequestId));
    }

    @Override
    public void acceptDonorCardRequest(String donorCardRequestId) {
        // 헌혈증 요청을 조회
        Optional<DonorCardRequest> donorCardRequestOpt = getDonorCardRequestById(Long.valueOf(donorCardRequestId));

        if (donorCardRequestOpt.isPresent()) {
            DonorCardRequest donorCardRequest = donorCardRequestOpt.get();

            // 증명서류의 적절성 확인(일단 200자 이상)
            if (donorCardRequest.getDonorCardRequestForm().getEvidenceDocument().length() >= 200) {
                // 증명서류가 200자 이상이므로 요청 수락
                donorCardRequestRepository.updateDonorCardRequest(Long.parseLong(donorCardRequestId), RejectPermission.승인, null);
            } else {
                // 증명서류가 200자 미만이므로 요청 거절
                donorCardRequestRepository.updateDonorCardRequest(Long.parseLong(donorCardRequestId), RejectPermission.거절, DonorCardRequestRejectReason.자료부족);
            }
        } else {
            throw new RuntimeException("Cannot find DonorCardRequest with id: " + donorCardRequestId);
        }
    }



}