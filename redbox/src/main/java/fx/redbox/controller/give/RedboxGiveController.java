package fx.redbox.controller.give;

import fx.redbox.config.argumentresolver.Login;
import fx.redbox.controller.api.RedboxResponseMessage;
import fx.redbox.controller.api.ResponseApi;
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
public class RedboxGiveController {

    private final DonorCardService donorCardService;

    @PostMapping("/{certificateNumber}")
    @Operation(summary = "레드박스 기부", description = "증서번호를 사용해 레드박스에 기부합니다.")
    @ApiResponse(responseCode = "200", description = "레드박스 기부 성공")
    @ApiResponse(responseCode = "404", description = "헌혈증 정보를 찾을 수 없습니다.")
    public ResponseApi redboxGive(@PathVariable String certificateNumber, @Login User loginUser) {
        donorCardService.redboxGive(certificateNumber, loginUser);
        return ResponseApi.success(RedboxResponseMessage.SUCCESS_REDBOX_GIVE.getMessage());
    }

}
