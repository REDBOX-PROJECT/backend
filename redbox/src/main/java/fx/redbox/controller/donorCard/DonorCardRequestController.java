package fx.redbox.controller.donorCard;

import fx.redbox.controller.api.ApiResponse;
import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.service.donorcard.DonorCardRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/redbox/donorCardRequest")
public class DonorCardRequestController {

    private final DonorCardRequestService donorCardRequestService;

    @Autowired
    public DonorCardRequestController(DonorCardRequestService donorCardRequestService) {
        this.donorCardRequestService = donorCardRequestService;
    }

    @PostMapping
    public ApiResponse<DonorCardRequest> createDonorCardRequest(@RequestBody DonorCardRequestDto donorCardRequestDto) {

        // DonorCardRequestDto를 DonorCardRequest로 변환
        DonorCardRequest donorCardRequest = new DonorCardRequest();
        donorCardRequest.setDonorCardRequestPermission(donorCardRequestDto.getDonorCardRequestPermission());
        donorCardRequest.setDonorCardRequestRejectReason(donorCardRequestDto.getDonorCardRequestRejectReason());
        donorCardRequest.setUserId(donorCardRequestDto.getUserId());

        // DonorCardRequestDto를 DonorCardRequestForm으로 변환
        DonorCardRequestForm donorCardRequestForm = new DonorCardRequestForm();
        donorCardRequestForm.setPatientName(donorCardRequestDto.getPatientName());
        donorCardRequestForm.setEvidenceDocument(donorCardRequestDto.getEvidenceDocument());
        donorCardRequestForm.setPatientGender(donorCardRequestDto.getPatientGender());
        donorCardRequestForm.setBloodType(donorCardRequestDto.getBloodType());
        donorCardRequestForm.setDonorCardRequestDate(donorCardRequestDto.getDonorCardRequestDate());

        // 서비스 메소드 호출
        DonorCardRequest newDonorCardRequest = donorCardRequestService.createDonorCardRequest(donorCardRequest, donorCardRequestForm);
        return ApiResponse.res(HttpStatus.CREATED.value(), "헌혈증 요청 생성 성공", newDonorCardRequest);
    }

    @GetMapping("/{donorCardRequestId}")
    public ApiResponse<DonorCardRequest> getDonorCardRequestById(@PathVariable String donorCardRequestId) {
        DonorCardRequest donorCardRequest = donorCardRequestService.getDonorCardRequestById(donorCardRequestId);
        return ApiResponse.res(HttpStatus.OK.value(), "헌혈증 요청 조회 성공", donorCardRequest);
    }

    @GetMapping
    public ApiResponse<List<DonorCardRequest>> getAllDonorCardRequests() {
        List<DonorCardRequest> donorCardRequests = donorCardRequestService.getAllDonorCardRequests();
        return ApiResponse.res(HttpStatus.OK.value(), "전체 헌혈증 조회 성공", donorCardRequests);
    }

    @PutMapping("/{donorCardRequestId}")
    public ApiResponse<DonorCardRequest> updateDonorCardRequest(@PathVariable Long donorCardRequestId, @RequestBody DonorCardRequestDto donorCardRequestDto) {

        // DonorCardRequestDto를 DonorCardRequest로 변환
        DonorCardRequest donorCardRequest = new DonorCardRequest();
        donorCardRequest.setDonorCardRequestPermission(donorCardRequestDto.getDonorCardRequestPermission());
        donorCardRequest.setDonorCardRequestRejectReason(donorCardRequestDto.getDonorCardRequestRejectReason());
        donorCardRequest.setUserId(donorCardRequestDto.getUserId());

        // DonorCardRequestDto를 DonorCardRequestForm으로 변환
        DonorCardRequestForm donorCardRequestForm = new DonorCardRequestForm();
        donorCardRequestForm.setPatientName(donorCardRequestDto.getPatientName());
        donorCardRequestForm.setEvidenceDocument(donorCardRequestDto.getEvidenceDocument());
        donorCardRequestForm.setPatientGender(donorCardRequestDto.getPatientGender());
        donorCardRequestForm.setBloodType(donorCardRequestDto.getBloodType());
        donorCardRequestForm.setDonorCardRequestDate(donorCardRequestDto.getDonorCardRequestDate());

        // 서비스 메소드 호출
        DonorCardRequest updatedDonorCardRequest = donorCardRequestService.updateDonorCardRequest(donorCardRequest, donorCardRequestForm);
        return ApiResponse.res(HttpStatus.OK.value(), "헌혈증 요청 수정 성공", updatedDonorCardRequest);
    }


    @DeleteMapping("/{donorCardRequestId}")
    public ApiResponse<Void> deleteDonorCardRequest(@PathVariable String donorCardRequestId) {
        donorCardRequestService.deleteDonorCardRequest(donorCardRequestId);
        return ApiResponse.res(HttpStatus.NO_CONTENT.value(), "헌혈증 요청 삭제 성공");
    }
}
