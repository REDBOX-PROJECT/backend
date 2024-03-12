package fx.redbox.controller.donorCard;

import fx.redbox.controller.api.DonorCardResponseMessage;
import fx.redbox.controller.api.ResponseApi;
import fx.redbox.entity.donorCards.DonorCard;
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

    @PostMapping("{email}")
    @Operation(
            summary = "헌혈증 저장",
            description = "증서번호, 성명, 생년월일, 헌혈일, 혈액원명, 헌혈종류, 성별을 이용해 헌혈증을 작성하고 저장합니다."
    )
    @ApiResponse(responseCode = "200", description = "헌혈증 저장 성공.")
    @ApiResponse(responseCode = "404", description = "헌혈증 저장 실패.")
    public ResponseApi saveDonorCard(@PathVariable String email, @RequestBody DonorCard donorCardData) throws SQLException {
        donorCardService.saveDonorCard(email, donorCardData);

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
        Optional<DonorCard> findDonorCard = donorCardService.findDonorCard(certificateNumber);

        return ResponseApi.success(DonorCardResponseMessage.READ_DONORCARD.getMessage());
    }
    @GetMapping("/all/{email}")
    @Operation(
            summary = "헌혈증 전체 정보 조회",
            description = "증서번호, 헌혈종류, 헌혈일자, 혈액원명을 이용해 전체 헌혈증을 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "전체 헌혈증 조회 성공.")
    @ApiResponse(responseCode = "404", description = "전체 헌혈증 조회 실패.")
    public ResponseApi showAllDonorCards(@PathVariable String email) {
        List<DonorCard> donorCards = donorCardService.findAllDonorCards(email);

        return ResponseApi.success(DonorCardResponseMessage.READ_ALL_DONORCARD.getMessage(), donorCards);
    }

//    @PatchMapping("/{certificateNumber}")
//    public ResponseApi updateDonorCardByCertificateNumber(@PathVariable String certificateNumber, @RequestBody DonorCard updateDonorCard) throws SQLException{
//        donorCardService.updateDonorCard(certificateNumber, updateDonorCard);
//
//        return ResponseApi.success("헌혈증 정보 수정 성공", null);
//    }
    
//    @DeleteMapping("/{certificateNumber}")
//    public ResponseApi deleteDonorCardByCertificateNumber(@PathVariable String certificateNumber) throws SQLException{
//        donorCardService.deleteDonorCard(certificateNumber);
//
//        return ResponseApi.success("헌혈증 정보 삭제 성공", null);
//    }

}
