package fx.redbox.controller.donorCard;

import fx.redbox.config.argumentresolver.Login;
import fx.redbox.controller.api.DonorCardResponseMessage;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.controller.donorCard.form.ReadAllDonorCardForm;
import fx.redbox.controller.donorCard.form.ReadDonorCardForm;
import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.entity.users.User;
import fx.redbox.service.donorCard.DonorCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Tag(name = "DONORCARD API", description = "헌혈증 관리 API")
@RequestMapping("/donorcards")
@RestController
@RequiredArgsConstructor
@Slf4j
public class DonorController {
    private final DonorCardService donorCardService;

    @PostMapping()
    @Operation(
            summary = "헌혈증 저장",
            description = "증서번호, 성명, 생년월일, 헌혈일, 혈액원명, 헌혈종류, 성별을 이용해 헌혈증을 작성하고 저장합니다."
    )
    @ApiResponse(responseCode = "200", description = "헌혈증 저장 성공.")
    @ApiResponse(responseCode = "404", description = "헌혈증 저장 실패.")
    public ResponseApi saveDonorCard(@RequestBody DonorCard donorCardData, @Login User loginUser) throws SQLException {

        donorCardData.setUserId(loginUser.getUserId());

        donorCardService.saveDonorCard(donorCardData);

        return ResponseApi.success(DonorCardResponseMessage.CREATED_DONORCARD.getMessage());
    }

    @GetMapping("/{certificateNumber}")
    @Operation(
            summary = "헌혈증 단일 정보 조회",
            description = "증서번호, 성명, 생년월일, 헌혈일, 혈액원명, 헌혈종류, 성별을 이용해 단일 헌혈증을 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "단일 헌혈증 조회 성공.")
    @ApiResponse(responseCode = "404", description = "단일 헌혈증 조회 실패.")
    public ResponseApi showDonorCardByCertificateNumber(@PathVariable String certificateNumber) throws SQLException{
        Optional<ReadDonorCardForm> readDonorCardForm = donorCardService.findDonorCard(certificateNumber);

        return ResponseApi.success(DonorCardResponseMessage.READ_DONORCARD.getMessage(), readDonorCardForm);
    }

    @GetMapping("/readAll")
    @Operation(
            summary = "헌혈증 전체 정보 조회",
            description = "증서번호, 헌혈종류, 헌혈일자, 혈액원명을 이용해 전체 헌혈증을 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "전체 헌혈증 조회 성공.")
    @ApiResponse(responseCode = "404", description = "전체 헌혈증 조회 실패.")
    public ResponseApi showAllDonorCards(@Login User loginuser) {
        List<ReadAllDonorCardForm> donorCards = donorCardService.findAllDonorCards(loginuser);

        return ResponseApi.success(DonorCardResponseMessage.READ_ALL_DONORCARD.getMessage(), donorCards);
    }

}
