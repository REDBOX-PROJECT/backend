package fx.redbox.service.donorCard;

import fx.redbox.common.Exception.DonorCardRequestNotFoundException;
import fx.redbox.common.Exception.DuplicateDonorCardRequestException;
import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.controller.donorCard.form.DonorCardRequestDto;
import fx.redbox.controller.donorCard.form.DonorCardRequestListForm;
import fx.redbox.controller.donorCard.form.DonorCardRequestReviewCheckForm;
import fx.redbox.controller.donorCard.form.DonorCardRequestReviewForm;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.users.User;
import fx.redbox.repository.donorCardRequest.DonorCardRequestRepository;
import fx.redbox.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DonorCardRequestServiceImpl implements DonorCardRequestService {

    private final DonorCardRequestRepository donorCardRequestRepository;
    private final UserRepository userRepository;

    @Override
    public void saveDonorCardRequest(Long userId, DonorCardRequestDto donorCardRequestDto) {

        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);

        Boolean existDonorCardRequest = donorCardRequestRepository.existsByUserId(user.getUserId());
        if (existDonorCardRequest) {
            throw new DuplicateDonorCardRequestException();
        }


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
                    .donorCardRequestId(donorCardRequest.getDonorCardRequestId())
                    .patientName(donorCardRequest.getPatientName())
                    .bloodType(donorCardRequest.getBloodType())
                    .permission(donorCardRequest.getDonorCardRequestApproval().getDonorCardRequestPermission())
                    .build();
            result.add(build);
        }
        return result;
    }


    @Override
    public DonorCardRequestReviewForm showDonorCardRequestReview(Long donorCardRequestId) {

        DonorCardRequestForm donorCardRequestForm = donorCardRequestRepository.getDonorCardRequestByDonorCardRequestId(donorCardRequestId)
                .orElseThrow(DonorCardRequestNotFoundException::new);

        //요청자 ID 확인
        User requestUser = userRepository.findByUserId(donorCardRequestForm.getUserId())
                .orElseThrow(UserNotFoundException::new);

        DonorCardRequestReviewForm donorCardRequestReviewForm = DonorCardRequestReviewForm.builder()
                .requestName(requestUser.getName()) //요청자이름
                .patientName(donorCardRequestForm.getPatientName()) //환자 이름
                .hospitalName(donorCardRequestForm.getHospitalName())
                .evidenceDocument(donorCardRequestForm.getEvidenceDocument())
                .rejectPermission(donorCardRequestForm.getDonorCardRequestApproval().getDonorCardRequestPermission())
                .build();
        return donorCardRequestReviewForm;
    }

    @Override
    public void updateDonorCardRequest(Long donorCardRequestId, DonorCardRequestReviewCheckForm donorCardRequestReviewCheckForm) {
        donorCardRequestRepository.getDonorCardRequestByDonorCardRequestId(donorCardRequestId)
                        .orElseThrow(DonorCardRequestNotFoundException::new);

        donorCardRequestRepository.updateDonorCardRequestReview(
                donorCardRequestId,
                donorCardRequestReviewCheckForm.getRejectPermission().name(),
                donorCardRequestReviewCheckForm.getDonorCardRequestRejectReason());
    }
}