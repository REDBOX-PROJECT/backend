package fx.redbox.service.donorCard;

import fx.redbox.common.Exception.DonorCardNotFoundException;
import fx.redbox.common.Exception.DuplicateCertificateNumberException;
import fx.redbox.common.Exception.UserNotFoundException;
import fx.redbox.controller.donorCard.form.ReadAllDonorCardForm;
import fx.redbox.controller.donorCard.form.ReadDonorCardForm;
import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.entity.users.User;
import fx.redbox.repository.donorCard.DonorCardRepository;
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
    public Optional<DonorCard> saveDonorCard(DonorCard donorCard) throws SQLException {

        // 헌혈증 중복 검증
        boolean exits = donorCardRepository.existsDonorCardByCertificateNumber(donorCard.getCertificateNumber());
        if(exits)
            throw new DuplicateCertificateNumberException();

        // 정보에 빈문자열일 때 예외처리 -> ?
        Optional<DonorCard> savedDonorCard = donorCardRepository.saveDonorCard(donorCard);

        return savedDonorCard;
    }

    @Override
    public Optional<ReadDonorCardForm> findDonorCard(String certificatieNumber) throws SQLException{

        Optional<DonorCard> findDonorCard = donorCardRepository.findDonorCardByCertificateNumber(certificatieNumber);

        //요청자 ID 확인
        Optional<User> byUserId = userService.findByUserId(findDonorCard.get().getUserId());
        if(byUserId.isEmpty()) {
            throw new UserNotFoundException();
        }
        User user = byUserId.get();

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
