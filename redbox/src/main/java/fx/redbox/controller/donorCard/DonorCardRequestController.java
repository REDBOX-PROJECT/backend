package fx.redbox.controller.donorCard;

import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.donorCard.form.DonorCardRequestDto;
import fx.redbox.controller.donorCard.form.DonorCardRequestListForm;
import fx.redbox.service.donorCard.DonorCardRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donorCardRequest")
public class DonorCardRequestController {

    private final DonorCardRequestService donorCardRequestService;

    @PostMapping("/{email}")
    public ResponseApi saveDonorCardRequest(@PathVariable String email, @RequestBody DonorCardRequestDto donorCardRequestDto) {
        donorCardRequestService.saveDonorCardRequest(email, donorCardRequestDto);
        return ResponseApi.success("헌혈증 요청 생성 성공");
    }

    @GetMapping
    public ResponseApi showAllDonorCardRequests() {
        List<DonorCardRequestListForm> donorCardRequestListForms = donorCardRequestService.showAllDonorCardRequests();
        return ResponseApi.success("헌혈증 요청 전체 조회 성공", donorCardRequestListForms);
    }


/*
    @GetMapping("/{donorCardRequestId}")
    public ResponseApi getDonorCardRequestById(@PathVariable String donorCardRequestId) {
        Optional<DonorCardRequest> getDonorCardRequest = donorCardRequestService.getDonorCardRequestById(Long.valueOf(donorCardRequestId));
        return ResponseApi.success("헌혈증 요청 조회 성공", getDonorCardRequest);
    }

    /*@PatchMapping("/{donorCardRequestId}")
    public ApiResponse updateDonorCardRequest(@PathVariable Long donorCardRequestId, @RequestBody DonorCardRequest donorCardRequest) {
        donorCardRequestService.updateDonorCardRequest(donorCardRequestId, donorCardRequest.getDonorCardRequestPermission(), donorCardRequest.getDonorCardRequestRejectReason());
        return ApiResponse.success("헌혈증 요청 상태 수정 성공", null);
    }

    @PatchMapping("/{donorCardRequestId}")
    public ResponseApi updateDonorCardRequestForm(@PathVariable Long donorCardRequestId, @RequestBody String evidenceDocument){
        donorCardRequestService.updateDonorCardRequestForm(donorCardRequestId, evidenceDocument);
        return ResponseApi.success("헌혈증 요청서 수정 성공", null);
    }

    @DeleteMapping("/{donorCardRequestId}")
    public ResponseApi deleteDonorCardRequest(@PathVariable Long donorCardRequestId) {
        donorCardRequestService.deleteDonorCardRequest(Long.valueOf(donorCardRequestId));
        return ResponseApi.success("헌혈증 요청 삭제 성공", null);
    }

    @PutMapping("/{donorCardRequestId}")
    public void acceptDonorCardRequest(@PathVariable Long donorCardRequestId) {
        donorCardRequestService.acceptDonorCardRequest(donorCardRequestId);
    }
    */
}
