package fx.redbox.controller.redbox;

import fx.redbox.config.argumentresolver.Login;
import fx.redbox.controller.api.DonorCardResponseMessage;
import fx.redbox.controller.api.RedboxResponseMessage;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.donorCard.form.RedBoxDashboardInfo;
import fx.redbox.controller.donorCard.form.RedboxDonationInfoForm;
import fx.redbox.entity.users.User;
import fx.redbox.service.donorCard.DonorCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "REDBOX GIVE API", description = "레드박스 기부 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/redbox")
public class RedboxController {

    private final DonorCardService donorCardService;

    @PostMapping("/{certificateNumber}")
    @Operation(summary = "레드박스 기부", description = "증서번호를 사용해 레드박스에 기부합니다.")
    @ApiResponse(responseCode = "200", description = "레드박스 기부 성공")
    @ApiResponse(responseCode = "404", description = "헌혈증 정보를 찾을 수 없습니다.")
    public ResponseApi redboxGive(@PathVariable String certificateNumber, @Login User loginUser) {
        donorCardService.redboxGive(certificateNumber, loginUser);
        return ResponseApi.success(RedboxResponseMessage.SUCCESS_REDBOX_GIVE.getMessage());
    }

    @GetMapping("/dashboard")
    @Operation(
            summary = "REDBOX 대시보드 조회",
            description = "REDBOX 총 기부 개수, 회원별 기부 개수, 기여도를 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "REDBOX 대시보드 조회 성공.")
    @ApiResponse(responseCode = "404", description = "REDBOX 대시보드 조회 실패.")
    public ResponseApi readRedBoxDashboard(@Login User loginuser) {
        RedBoxDashboardInfo redBoxDashboardInfo = donorCardService.readRedBoxDashboard(loginuser);

        return ResponseApi.success(DonorCardResponseMessage.READ_REDBOX_DASHBOARD.getMessage(), redBoxDashboardInfo);
    }

    @GetMapping("/donation-information")
    @Operation(
            summary = "REDBOX 헌혈증 보유량",
            description = "REDBOX가 소유하고 있는 A, B, AB, O, 전체 헌혈증 보유량을 나타냅니다."
    )
    @ApiResponse(responseCode = "200", description = "REDBOX 혈액형 보유량 조회 성공")
    public ResponseApi showRedboxDonationInfo() {
        RedboxDonationInfoForm redboxDonationInfoForm = donorCardService.showRedboxDonationInfo();
        return ResponseApi.success(DonorCardResponseMessage.SUCESS_REDBOX_DONATION_INFORMATION.getMessage(), redboxDonationInfoForm);
    }
}
