package fx.redbox.controller.donorCard;

import fx.redbox.controller.api.ApiResponse;
import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.entity.enums.DonorBloodKind;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.users.User;
import fx.redbox.entity.users.UserAccount;
import fx.redbox.entity.users.UserInfo;
import fx.redbox.service.donorCard.DonorCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/redbox/donorcards")
@RestController
@RequiredArgsConstructor
public class DonorController {
    private final DonorCardService donorCardService;

    @PostMapping("/save")
    public ApiResponse save(@RequestBody DonorCard donorCardData){
        donorCardService.join(donorCardData);

        return ApiResponse.res(HttpStatus.CREATED.value(), "헌혈증 정보 저장 성공");
    }

    @GetMapping("/{certificateNumber}")
    public ApiResponse showDonorCardByCertificateNumber(@PathVariable String certificateNumber){
        List<Map<String, Object>> findDonorCard = donorCardService.findDonorCard(certificateNumber);

        return ApiResponse.res(HttpStatus.OK.value(), "헌혈증 단일 정보 조회 성공", findDonorCard);
    }

    @GetMapping("/donorCards")
    public ApiResponse showAllDonorCards(){
        List<Map<String, Object>> donorCards = donorCardService.findAll();

        return ApiResponse.res(HttpStatus.OK.value(), "헌혈증 전체 정보 조회 성공", donorCards);
    }

    @PatchMapping("/{certificateNumber}")
    public ApiResponse updateDonorCardByCertificateNumber(@PathVariable String certificateNumber, @RequestBody DonorCard updateDonorCard){
        donorCardService.updateDonorCard(certificateNumber, updateDonorCard);
       
        return ApiResponse.res(HttpStatus.OK.value(), "헌혈증 정보 수정 성공");
    }
    
    @DeleteMapping("/{certificateNumber}")
    public ApiResponse deleteDonorCardByCertificateNumber(@PathVariable String certificateNumber){
        donorCardService.deleteDonorCard(certificateNumber);
        
        return ApiResponse.res(HttpStatus.OK.value(), "헌혈증 정보 삭제 성공");
    }

}
