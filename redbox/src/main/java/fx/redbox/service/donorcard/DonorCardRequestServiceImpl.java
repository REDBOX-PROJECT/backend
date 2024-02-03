package fx.redbox.service.donorcard;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.RejectPermission;
import fx.redbox.repository.donorcardrequest.DonorCardRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorCardRequestServiceImpl implements DonorCardRequestService {

    private final DonorCardRequestRepository donorCardRequestRepository;

    @Autowired
    public DonorCardRequestServiceImpl(DonorCardRequestRepository donorCardRequestRepository) {
        this.donorCardRequestRepository = donorCardRequestRepository;
    }

    @Override
    public DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm) {
        return donorCardRequestRepository.createDonorCardRequest(donorCardRequest, donorCardRequestForm);
    }

    @Override
    public DonorCardRequest getDonorCardRequestById(String donorCardRequestId) {
        return donorCardRequestRepository.getDonorCardRequestById(donorCardRequestId);
    }

    @Override
    public List<DonorCardRequest> getAllDonorCardRequests() {
        return donorCardRequestRepository.getAllDonorCardRequests();
    }

    @Override
    public DonorCardRequest updateDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm) {
        return donorCardRequestRepository.updateDonorCardRequest(donorCardRequest, donorCardRequestForm);
    }

    @Override
    public void deleteDonorCardRequest(String donorCardRequestId) {
        donorCardRequestRepository.deleteDonorCardRequest(donorCardRequestId);
    }

    @Override
    public void acceptDonorCardRequest(String donorCardRequestId) {
        // 해당 ID로 헌혈증 요청을 찾는다.
        DonorCardRequest donorCardRequest = donorCardRequestRepository.getDonorCardRequestById(donorCardRequestId);

        // DonorCardRequestForm 객체를 가져온다
        DonorCardRequestForm donorCardRequestForm = donorCardRequest.getDonorCardRequestForm();

        // 증빙 서류가 첨부되어있는지 확인한다.
        if (donorCardRequestForm.getEvidenceDocument() != null) {
            // 증빙서류가 첨부되어 있으면 요청 상태를 '수락됨'으로 변경한다.
            donorCardRequest.setDonorCardRequestPermission(RejectPermission.승인);
        } else {
            // 증빙서류가 첨부되어 있지 않으면 요청 상태를 '거절'로 변경한다.
            donorCardRequest.setDonorCardRequestPermission(RejectPermission.거절);

            // 증빙서류의 길이가 200자 이하인지 확인하고, 거절 사유를 설정한다.
            if (donorCardRequestForm.getEvidenceDocument() != null && donorCardRequestForm.getEvidenceDocument().length() <= 200) {
                donorCardRequest.setDonorCardRequestRejectReason(DonorCardRequestRejectReason.자료부족);
            } else {
                donorCardRequest.setDonorCardRequestRejectReason(DonorCardRequestRejectReason.적합한사유가아님);
            }
        }

        // 변경된 상태를 데이터베이스에 저장한다.
        donorCardRequestRepository.updateDonorCardRequest(donorCardRequest, donorCardRequestForm);
    }


}