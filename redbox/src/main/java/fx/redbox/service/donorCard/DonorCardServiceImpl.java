package fx.redbox.service.donorCard;

import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.controller.donorCard.form.ReadAllDonorCardForm;
import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.entity.users.User;
import fx.redbox.repository.donorCard.DonorCardRepository;
import fx.redbox.repository.user.UserRepository;
import fx.redbox.service.user.UserService;
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
    private final UserService userService;

    @Override
    public Optional<DonorCard> saveDonorCard(String email, DonorCard donorCard) throws SQLException {
        //사용자 검증
        Optional<User> userOptional = userService.findByEmail(email);
        if(userOptional.isEmpty())
            throw new UserNotFoundException();
        User user = userOptional.get();

        donorCard.setUserId(user.getUserId());

        Optional<DonorCard> findDonorCard = donorCardRepository.findDonorCardByCertificateNumber(donorCard.getCertificateNumber());
        if (!findDonorCard.isEmpty()){
            return findDonorCard; // 등록하려는 헌혈증이 이미 있다면 그 헌혈증의 정보 반환
        }

        Optional<DonorCard> savedDonorCard = donorCardRepository.saveDonorCard(donorCard);

        return savedDonorCard;
    }

    @Override
    public Optional<DonorCard> findDonorCard(String certificatieNumber) throws SQLException{

        Optional<DonorCard> findDonorCard = donorCardRepository.findDonorCardByCertificateNumber(certificatieNumber);

        if(!findDonorCard.isEmpty()){
            // return 해당 증서번호의 헌혈증이 없어요 라는 에러 발생
        }
        return findDonorCard;
    }

    @Override
    public List<ReadAllDonorCardForm> findAllDonorCards(String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        if(userOptional.isEmpty())
            throw new UserNotFoundException();
        User user = userOptional.get();
        List<DonorCard> donorCards = donorCardRepository.findAllDonorCards(user.getUserId());
        return convertToReadAllDonorCardFormList(donorCards);
    }

    @Override
    public void deleteDonorCard(String certificateNumber) throws SQLException{
//        Optional<DonorCard> findDonorCard = donorCardRepository.findDonorCardByCertificateNumber(certificateNumber);
//        if(!findDonorCard.isEmpty()){
//            // return 해당 증서번호의 헌혈증이 없어요 라는 에러 발생
//        }
        donorCardRepository.deleteDonorCard(certificateNumber);
    }

    @Override
    public void updateDonorCard(String certificateNumber, DonorCard updateDonorCard) throws SQLException{
//        Optional<DonorCard> findDonorCard = donorCardRepository.findDonorCardByCertificateNumber(certificateNumber);
//        if(!findDonorCard.isEmpty()){
//            // return 해당 증서번호의 헌혈증이 없어요 라는 에러 발생
//        }
        donorCardRepository.updateDonorCard(certificateNumber, updateDonorCard);
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
