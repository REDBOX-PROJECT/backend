package fx.redbox.controller.donorCard;

import fx.redbox.controller.api.ApiResponse;
import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.service.donorcard.DonorCardRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/redbox/donorCardRequest")
public class DonorCardRequestController {

    private final DonorCardRequestService donorCardRequestService;

    @PostMapping
    public ApiResponse createDonorCardRequest(@RequestBody DonorCardRequestDto donorCardRequestDto) {
        DonorCardRequest createdDonorCardRequest = donorCardRequestService.createDonorCardRequest(donorCardRequestDto.getDonorCardRequest(), donorCardRequestDto.getDonorCardRequestForm());
        return ApiResponse.success("헌혈증 요청 생성 성공", createdDonorCardRequest);
    }

    @GetMapping("/{donorCardRequestId}")
    public ApiResponse getDonorCardRequestById(@PathVariable String donorCardRequestId) {
        Optional<DonorCardRequest> getDonorCardRequest = donorCardRequestService.getDonorCardRequestById(Long.valueOf(donorCardRequestId));
        return ApiResponse.success("헌혈증 요청 조회 성공", getDonorCardRequest);
    }

    @GetMapping
    public ApiResponse getAllDonorCardRequests() {
        List<DonorCardRequest> getDonorCardRequests = donorCardRequestService.getAllDonorCardRequests();
        return ApiResponse.success("헌혈증 요청 전체 조회 성공", getDonorCardRequests);
    }

    @PutMapping("/{donorCardRequestId}")
    public ApiResponse updateDonorCardRequest(@PathVariable Long donorCardRequestId, @RequestBody DonorCardRequest donorCardRequest) {
        donorCardRequestService.updateDonorCardRequest(donorCardRequestId, donorCardRequest.getDonorCardRequestPermission(), donorCardRequest.getDonorCardRequestRejectReason());
        return ApiResponse.success("헌혈증 요청 상태 수정 성공", null);
    }

    @PostMapping("/{donorCardRequestId}")
    public ApiResponse updateDonorCardRequestForm(@PathVariable Long donorCardRequestId, @RequestBody String evidenceDocument){
        donorCardRequestService.updateDonorCardRequestForm(donorCardRequestId, evidenceDocument);
        return ApiResponse.success("헌혈증 요청서 수정 성공", null);
    }

    @DeleteMapping("/{donorCardRequestId}")
    public ApiResponse<Void> deleteDonorCardRequest(@PathVariable Long donorCardRequestId) {
        donorCardRequestService.deleteDonorCardRequest(Long.valueOf(donorCardRequestId));
        return ApiResponse.success("헌혈증 요청 삭제 성공", null);
    }

    @PutMapping("/{donorCardRequestId}")
    public void acceptDonorCardRequest(@PathVariable Long donorCardRequestId) {
        donorCardRequestService.acceptDonorCardRequest(donorCardRequestId);
    }
}
