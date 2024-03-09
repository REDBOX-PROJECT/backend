package fx.redbox.controller.donorCard;

import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.donorCard.form.DonorCardRequestDto;
import fx.redbox.controller.donorCard.form.DonorCardRequestListForm;
import fx.redbox.controller.donorCard.form.DonorCardRequestReviewCheckForm;
import fx.redbox.controller.donorCard.form.DonorCardRequestReviewForm;
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

    @GetMapping("/{donorCardRequestId}")
    public ResponseApi showDonorCardRequestReview(@PathVariable Long donorCardRequestId) {
        DonorCardRequestReviewForm donorCardRequestReviewForm = donorCardRequestService.showDonorCardRequestReview(donorCardRequestId);
        return ResponseApi.success("헌혈증 요청 심사 조회 성공", donorCardRequestReviewForm);
    }

    @PatchMapping("/{donorCardRequestId}") //헌혈증 요청 심사 (승인 거절) 선택
    public ResponseApi updateDonorCardRequestReview(@PathVariable Long donorCardRequestId,
                                                    @RequestBody DonorCardRequestReviewCheckForm donorCardRequestReviewCheckForm) {
        donorCardRequestService.updateDonorCardRequest(donorCardRequestId, donorCardRequestReviewCheckForm);
        return ResponseApi.success("헌혈증 요청 상태 수정 성공", null);
    }

}
