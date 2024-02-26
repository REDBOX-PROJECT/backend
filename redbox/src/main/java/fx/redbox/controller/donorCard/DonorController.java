package fx.redbox.controller.donorCard;

import fx.redbox.controller.api.ResponseApi;
import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.service.donorCard.DonorCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/donorcards")
@RestController
@RequiredArgsConstructor
public class DonorController {
    private final DonorCardService donorCardService;


    @PostMapping
    public ResponseApi saveDonorCard(@RequestBody DonorCard donorCardData) throws SQLException {
        donorCardService.saveDonorCard(donorCardData);

        return ResponseApi.success("헌혈증 정보 저장 성공", null);
    }

    @GetMapping("/{certificateNumber}")
    public ResponseApi showDonorCardByCertificateNumber(@PathVariable String certificateNumber) throws SQLException{
        Optional<DonorCard> findDonorCard = donorCardService.findDonorCard(certificateNumber);

        return ResponseApi.success("헌혈증 단일 정보 조회 성공", findDonorCard);
    }

    @GetMapping
    public ResponseApi showAllDonorCards() throws SQLException{
        List<DonorCard> donorCards = donorCardService.findAllDonorCards();

        return ResponseApi.success("헌혈증 전체 정보 조회 성공", donorCards);
    }

    @PatchMapping("/{certificateNumber}")
    public ResponseApi updateDonorCardByCertificateNumber(@PathVariable String certificateNumber, @RequestBody DonorCard updateDonorCard) throws SQLException{
        donorCardService.updateDonorCard(certificateNumber, updateDonorCard);
       
        return ResponseApi.success("헌혈증 정보 수정 성공", null);
    }
    
    @DeleteMapping("/{certificateNumber}")
    public ResponseApi deleteDonorCardByCertificateNumber(@PathVariable String certificateNumber) throws SQLException{
        donorCardService.deleteDonorCard(certificateNumber);
        
        return ResponseApi.success("헌혈증 정보 삭제 성공", null);
    }

}
