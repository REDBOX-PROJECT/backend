package fx.redbox.service.donorCard;

import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.controller.donorCard.form.DonorCardRequestDto;
import fx.redbox.controller.donorCard.form.DonorCardRequestListForm;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.users.User;
import fx.redbox.repository.donorCardRequest.DonorCardRequestRepository;
import fx.redbox.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DonorCardRequestServiceImpl implements DonorCardRequestService {

    private final DonorCardRequestRepository donorCardRequestRepository;
    private final UserService userService;

    @Override
    public void saveDonorCardRequest(String email, DonorCardRequestDto donorCardRequestDto) {
        Optional<User> userOptional = userService.findByEmail(email);
        if(userOptional.isEmpty())
            throw new UserNotFoundException();
       User user = userOptional.get();

        DonorCardRequestForm donorCardRequestForm = DonorCardRequestForm.builder()
                .patientName(donorCardRequestDto.getPatientName())
                .birth(donorCardRequestDto.getBirth())
                .patientGender(donorCardRequestDto.getGender())
                .hospitalName(donorCardRequestDto.getHospitalName())
                .evidenceDocument(donorCardRequestDto.getEvidenceDocument())

                .donorCardRequestDate(LocalDateTime.now())
                .bloodType(user.getBloodType())
                .userId(user.getUserId())
                .build();
        donorCardRequestRepository.saveDonorCardRequestForm(donorCardRequestForm);
    }

    @Override
    public List<DonorCardRequestListForm> showAllDonorCardRequests() {
        List<DonorCardRequestForm> allDonorCardRequests = donorCardRequestRepository.getAllDonorCardRequests();
        List<DonorCardRequestListForm> result = new ArrayList<>();

        for(DonorCardRequestForm donorCardRequest : allDonorCardRequests) {
            DonorCardRequestListForm build = DonorCardRequestListForm.builder()
                    .patientName(donorCardRequest.getPatientName())
                    .bloodType(donorCardRequest.getBloodType())
                    .permission(donorCardRequest.getDonorCardRequestApproval().getDonorCardRequestPermission())
                    .build();
            result.add(build);
        }
        return result;
    }


    @Override
    public Optional<DonorCardRequestForm> getDonorCardRequest(String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        if(userOptional.isEmpty())
            throw new UserNotFoundException();
        User user = userOptional.get();


        Optional<DonorCardRequestForm> donorCardRequest = donorCardRequestRepository.getDonorCardRequestByUserId(user.getUserId());
        if (donorCardRequest.isEmpty()) {
            throw new NoSuchElementException(user.getName() + "에 대한 요청 없음");
        }
        return donorCardRequest;
    }
    /*

        @Override
        public List<DonorCardRequest> getAllDonorCardRequests() {
            return donorCardRequestRepository.getAllDonorCardRequests();
        }
    @Override
    public void updateDonorCardRequest(Long donorCardRequestId, RejectPermission donorCardRequestPermission, DonorCardRequestRejectReason donorCardRequestRejectReason) {
        // 헌혈증 요청 조회
        Optional<DonorCardRequest> donorCardRequestOpt = donorCardRequestRepository.getDonorCardRequestById(donorCardRequestId);

        // 헌혈증 요청이 존재하지 않는 경우 예외 발생
        if (!donorCardRequestOpt.isPresent()) {
            throw new NoSuchElementException("해당 id에 대한 요청 없음. id: " + donorCardRequestId);
        }

        // 헌혈증 요청이 존재하는 경우 상태 업데이트
        donorCardRequestRepository.updateDonorCardRequest(donorCardRequestId, donorCardRequestPermission, donorCardRequestRejectReason);
    }

    @Override
    public void updateDonorCardRequestForm(Long donorCardRequestId, String evidenceDocument) {
        // 헌혈증 요청 조회
        Optional<DonorCardRequest> donorCardRequestOpt = donorCardRequestRepository.getDonorCardRequestById(donorCardRequestId);

        // 헌혈증 요청이 존재하지 않는 경우 예외 발생
        if (!donorCardRequestOpt.isPresent()) {
            throw new NoSuchElementException("해당 id에 대한 요청 없음. id: " + donorCardRequestId);
        }

        // 헌혈증 요청이 존재하는 경우 증거 문서 업데이트
        donorCardRequestRepository.updateDonorCardRequestForm(donorCardRequestId, evidenceDocument);
    }

    @Override
    public void deleteDonorCardRequest(Long donorCardRequestId) {
        // 헌혈증 요청 조회
        Optional<DonorCardRequest> donorCardRequestOpt = donorCardRequestRepository.getDonorCardRequestById(Long.valueOf(donorCardRequestId));

        // 헌혈증 요청이 존재하지 않는 경우 예외 발생
        if (!donorCardRequestOpt.isPresent()) {
            throw new NoSuchElementException("해당 id에 대한 요청 없음. id: " + donorCardRequestId);
        }

        // 헌혈증 요청이 존재하는 경우 삭제
        donorCardRequestRepository.deleteDonorCardRequest(Long.valueOf(donorCardRequestId));
    }

    @Override
    public void acceptDonorCardRequest(Long donorCardRequestId) {
        // 헌혈증 요청을 조회
        Optional<DonorCardRequest> donorCardRequestOpt = getDonorCardRequestById(Long.valueOf(donorCardRequestId));

        // 헌혈증 요청이 존재하지 않는 경우 예외 발생
        if (!donorCardRequestOpt.isPresent()) {
            throw new NoSuchElementException("해당 id에 대한 요청 없음. id: " + donorCardRequestId);
        }

        DonorCardRequest donorCardRequest = donorCardRequestOpt.get();

        // 증명서류의 적절성 확인(일단 200자 이상)
        if (donorCardRequest.getDonorCardRequestForm().getEvidenceDocument().length() >= 200) {
            // 증명서류가 200자 이상이므로 요청 수락
            donorCardRequestRepository.updateDonorCardRequest(Long.parseLong(String.valueOf(donorCardRequestId)), RejectPermission.승인, null);
        } else {
            // 증명서류가 200자 미만이므로 요청 거절
            donorCardRequestRepository.updateDonorCardRequest(Long.parseLong(String.valueOf(donorCardRequestId)), RejectPermission.거절, DonorCardRequestRejectReason.자료부족);
        }
    }

     */
}