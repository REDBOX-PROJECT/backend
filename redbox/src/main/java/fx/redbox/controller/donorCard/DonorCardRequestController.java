package fx.redbox.controller.donorCard;

import fx.redbox.controller.api.DonorCardRequestResponseMessage;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.donorCard.form.DonorCardRequestDto;
import fx.redbox.controller.donorCard.form.DonorCardRequestListForm;
import fx.redbox.controller.donorCard.form.DonorCardRequestReviewCheckForm;
import fx.redbox.controller.donorCard.form.DonorCardRequestReviewForm;
import fx.redbox.service.donorCard.DonorCardRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DONORCARD REQUEST API", description = "헌혈증 요청 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/donorCardRequest")
public class DonorCardRequestController {

    private final DonorCardRequestService donorCardRequestService;

    @PostMapping("/{email}")
    @Operation(
            summary = "요청서 저장",
            description = "환자이름, 생일, 성별, 병원이름, 증거자료를 이용해 요청서를 작성하고 저장합니다."
    )
    @ApiResponse(responseCode = "200", description = "헌혈증 요청 저장 성공")
    public ResponseApi saveDonorCardRequest(@PathVariable String email, @RequestBody DonorCardRequestDto donorCardRequestDto) {
        donorCardRequestService.saveDonorCardRequest(email, donorCardRequestDto);
        return ResponseApi.success(DonorCardRequestResponseMessage.DONOR_CARD_REQUEST_SUCCESS.getMessage());
    }

    @GetMapping
    @Operation(
            summary = "요청서 리스트",
            description = "헌혈증 요청 리스트입니다."
    )
    @ApiResponse(responseCode = "200", description = "헌혈증 요청 전체 조회 성공")
    public ResponseApi showAllDonorCardRequests() {
        List<DonorCardRequestListForm> donorCardRequestListForms = donorCardRequestService.showAllDonorCardRequests();
        return ResponseApi.success(
                DonorCardRequestResponseMessage.DONOR_CARD_REQUEST_LIST_SUCCESS.getMessage(),
                donorCardRequestListForms);
    }

    @GetMapping("/{donorCardRequestId}")
    @Operation(
            summary = "요청서 단건 조회",
            description = "단건 헌혈증 요청에 대한 장보를 나타냅니다."
    )
    @ApiResponse(responseCode = "200", description = "헌혈증 요청서 단건 조회 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 요청서입니다.")
    public ResponseApi showDonorCardRequestReview(@PathVariable Long donorCardRequestId) {
        DonorCardRequestReviewForm donorCardRequestReviewForm = donorCardRequestService.showDonorCardRequestReview(donorCardRequestId);
        return ResponseApi.success(
                DonorCardRequestResponseMessage.DONOR_CARD_REQUEST_SELECT_SUCCESS.getMessage(),
                donorCardRequestReviewForm);
    }

    @PatchMapping("/{donorCardRequestId}") //헌혈증 요청 심사 (승인 거절) 선택
    @Operation(
            summary = "요청서 심사",
            description = "단건 헌혈증 요청에 대한 심사를 진행할 수 있습니다."
    )
    @ApiResponse(responseCode = "200", description = "헌혈증 요청 심사 업데이트 성공")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 요청서입니다.")
    public ResponseApi updateDonorCardRequestReview(@PathVariable Long donorCardRequestId,
                                                    @RequestBody DonorCardRequestReviewCheckForm donorCardRequestReviewCheckForm) {
        donorCardRequestService.updateDonorCardRequest(donorCardRequestId, donorCardRequestReviewCheckForm);
        return ResponseApi.success(DonorCardRequestResponseMessage.DONOR_CARD_REQUEST_UPDATE_SUCCESS.getMessage());
    }

}
