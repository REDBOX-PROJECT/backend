package fx.redbox.service.donorCard;

import fx.redbox.common.Exception.DonorCardNotFoundException;
import fx.redbox.common.Exception.DuplicateCertificateNumberException;
import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.controller.donorCard.form.ReadAllDonorCardForm;
import fx.redbox.controller.donorCard.form.ReadDonorCardForm;
import fx.redbox.controller.donorCard.form.RedBoxDashboardInfo;
import fx.redbox.controller.donorCard.form.RedboxDonationInfoForm;
import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.users.User;
import fx.redbox.repository.donorCard.DonorCardRepository;
import fx.redbox.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonorCardServiceImpl implements DonorCardService{

    private final DonorCardRepository donorCardRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<DonorCard> saveDonorCard(DonorCard donorCard, User user) {

        // 헌혈증 중복 검증
        boolean exits = donorCardRepository.existsDonorCardByCertificateNumber(donorCard.getCertificateNumber());
        if(exits)
            throw new DuplicateCertificateNumberException();

        //사용자의 혈액형 파악
        User userInfo = userRepository.findByUserId(user.getUserId()).orElseThrow(UserNotFoundException::new);
        BloodType bloodType = userInfo.getBloodType();
        donorCard.setBloodType(bloodType);

        // 정보에 빈문자열일 때 예외처리 -> ?
        Optional<DonorCard> savedDonorCard = donorCardRepository.saveDonorCard(donorCard);

        return savedDonorCard;
    }

    @Override
    public Optional<ReadDonorCardForm> findDonorCard(String certificatieNumber) throws SQLException{

        Optional<DonorCard> findDonorCard = donorCardRepository.findDonorCardByCertificateNumber(certificatieNumber);

        //요청자 ID 확인
        User user = userRepository.findByUserId(findDonorCard.get().getUserId()).orElseThrow(UserNotFoundException::new);

        ReadDonorCardForm readDonorCardForm = ReadDonorCardForm.builder()
                .certificateNumber(user.getName())
                .donorName(findDonorCard.get().getDonorName())
                .donorBirth(findDonorCard.get().getDonorBirth())
                .donorBloodKind(findDonorCard.get().getDonorBloodKind())
                .donationDate(findDonorCard.get().getDonationDate())
                .donorGender(findDonorCard.get().getDonorGender())
                .bloodCenter(findDonorCard.get().getBloodCenter())
                .build();
        return Optional.ofNullable(readDonorCardForm);
    }

    @Override
    public List<ReadAllDonorCardForm> findAllDonorCards(User user) {
        List<DonorCard> donorCards = donorCardRepository.findAllDonorCards(user.getUserId());
        if (donorCards.isEmpty()) {
            throw new DonorCardNotFoundException();
        }
        return convertToReadAllDonorCardFormList(donorCards);
    }

  
    @Override
    public RedBoxDashboardInfo readRedBoxDashboard(User user) {
        // 사용자 존재 여부 확인
        userRepository.findByUserId(user.getUserId()).orElseThrow(UserNotFoundException::new);

        // 사용자 ID에 해당하는 헌혈증 수 카운트
        int totalDonorCards = donorCardRepository.countDonorCardByUserId(1L); //1L은 레드박스 소유임! 나중에 상수 처리해야함
        int userDonorCards = userRepository.findDonationCountByUserId(user.getUserId());

        // 기여도 계산
        double contributionRate;
        if(totalDonorCards > 0) {
            contributionRate = (double) userDonorCards / totalDonorCards * 100;
        } else {
            contributionRate = 0;
        }

        return new RedBoxDashboardInfo(totalDonorCards, userDonorCards, contributionRate);
    }

    @Override //레드박스 기부 시 userId가 1이 지정됨. & 사용자의 기부개수 1 증가
    public void redboxGive(String certificateNumber, User user) {

        //해당 사용자의 헌혈증이 맞는지 검증한다.
        boolean certificateNumerCheck = false;
        List<DonorCard> allDonorCards = donorCardRepository.findAllDonorCards(user.getUserId());
        for(DonorCard donorCard : allDonorCards) {
            if(donorCard.getCertificateNumber().equals(certificateNumber)) {
                certificateNumerCheck = true;
                break;
            }
        }

        //파라미터에서 넘어온 헌혈증 번호와 사용자가 가지고 있는 헌혈증이 일치하지 않으면 예외 발생
        if(!certificateNumerCheck) {
            throw new DonorCardNotFoundException();
        }

//        donorCardRepository.findDonorCardByCertificateNumber(certificateNumber)
//                .orElseThrow(DonorCardNotFoundException::new);

        //사용자 기부개수 1늘림
        userRepository.incrementDonationCount(user.getUserId());

        //1L = 레드박스 나중에 상수처리하자
        donorCardRepository.assignOwnerToDonorCard(certificateNumber, 1L);
    }

    @Override
    public RedboxDonationInfoForm showRedboxDonationInfo() {

        //레드박스 헌혈증 조회
        List<DonorCard> donorCards = donorCardRepository.findAllDonorCards(1L);

        // 혈액 형태별 카운트 초기화
        int countBloodTypeA = 0;
        int countBloodTypeB = 0;
        int countBloodTypeAB = 0;
        int countBloodTypeO = 0;

        // 각 헌혈증의 혈액 형태를 확인하고 카운트 증가
        for (DonorCard donorCard : donorCards) {
            BloodType bloodType = donorCard.getBloodType();
            if (bloodType != null) {
                if (bloodType.equals(BloodType.A)) {
                    countBloodTypeA++;
                } else if (bloodType.equals(BloodType.B)) {
                    countBloodTypeB++;
                } else if (bloodType.equals(BloodType.AB)) {
                    countBloodTypeAB++;
                } else if (bloodType.equals(BloodType.O)) {
                    countBloodTypeO++;
                }
            }
        }
        int countTotalBloodType = countBloodTypeA + countBloodTypeB + countBloodTypeAB + countBloodTypeO;
        return new RedboxDonationInfoForm(countTotalBloodType, countBloodTypeA, countBloodTypeB, countBloodTypeAB, countBloodTypeO);
    }

    public List<ReadAllDonorCardForm> convertToReadAllDonorCardFormList(List<DonorCard> donorCards) {
        return donorCards.stream().map(donorCard -> {
            ReadAllDonorCardForm form = new ReadAllDonorCardForm();
            form.setCertificateNumber(donorCard.getCertificateNumber());
            form.setDonorBloodKind(donorCard.getDonorBloodKind());
            form.setDonationDate(donorCard.getDonationDate());
            form.setBloodCenter(donorCard.getBloodCenter());
            return form;
        }).collect(Collectors.toList());
    }
}
