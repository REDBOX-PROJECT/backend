package fx.redbox.controller.donorCard;

import fx.redbox.controller.api.DonorCardRequestResponseMessage;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.service.donorCard.DonorCardRequestService;
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
    public ResponseApi createDonorCardRequest(@RequestBody DonorCardRequestDto donorCardRequestDto) {
        DonorCardRequest createdDonorCardRequest = donorCardRequestService.createDonorCardRequest(donorCardRequestDto.getDonorCardRequest(), donorCardRequestDto.getDonorCardRequestForm());
        return ResponseApi.success(DonorCardRequestResponseMessage.CREATED_DONORCARD_REQUEST.getMessage(), createdDonorCardRequest);
    }

    @GetMapping("/{donorCardRequestId}")
    public ResponseApi getDonorCardRequestById(@PathVariable String donorCardRequestId) {
        Optional<DonorCardRequest> getDonorCardRequest = donorCardRequestService.getDonorCardRequestById(Long.valueOf(donorCardRequestId));
        return ResponseApi.success(DonorCardRequestResponseMessage.READ_DONORCARD_REQUEST.getMessage(), getDonorCardRequest);
    }

    @GetMapping
    public ResponseApi getAllDonorCardRequests() {
        List<DonorCardRequest> getDonorCardRequests = donorCardRequestService.getAllDonorCardRequests();
        return ResponseApi.success("헌혈증 요청 전체 조회 성공", getDonorCardRequests);
    }

    @PatchMapping("/{donorCardRequestId}")
    public ResponseApi updateDonorCardRequest(@PathVariable Long donorCardRequestId, @RequestBody DonorCardRequest donorCardRequest) {
        donorCardRequestService.updateDonorCardRequest(donorCardRequestId, donorCardRequest.getDonorCardRequestPermission(), donorCardRequest.getDonorCardRequestRejectReason());
        return ResponseApi.success(DonorCardRequestResponseMessage.UPDATE_DONORCARD_REQUEST.getMessage(), null);
    }

    @PatchMapping("/{donorCardRequestId}")
    public ResponseApi updateDonorCardRequestForm(@PathVariable Long donorCardRequestId, @RequestBody String evidenceDocument){
        donorCardRequestService.updateDonorCardRequestForm(donorCardRequestId, evidenceDocument);
        return ResponseApi.success(DonorCardRequestResponseMessage.UPDATE_DONORCARD_REQUEST_FORM.getMessage(), null);
    }

    @DeleteMapping("/{donorCardRequestId}")
    public ResponseApi deleteDonorCardRequest(@PathVariable Long donorCardRequestId) {
        donorCardRequestService.deleteDonorCardRequest(Long.valueOf(donorCardRequestId));
        return ResponseApi.success(DonorCardRequestResponseMessage.DELETE_DONORCARD_REQUEST.getMessage(), null);
    }

    @PutMapping("/{donorCardRequestId}")
    public void acceptDonorCardRequest(@PathVariable Long donorCardRequestId) {
        donorCardRequestService.acceptDonorCardRequest(donorCardRequestId);
    }
}
