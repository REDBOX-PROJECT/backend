package fx.redbox.service.donorCard;

import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.repository.donorCard.DonorCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonorCardServiceImpl implements DonorCardService{

    private final DonorCardRepository donorCardRepository;

    @Override
    public Optional<DonorCard> saveDonorCard(DonorCard donorCard) throws SQLException {

        Optional<DonorCard> findDonorCard = donorCardRepository.findDonorCardByCertificateNumber(donorCard.getCertificateNumber());
        if (!findDonorCard.isEmpty()){
            return findDonorCard; // 등록하려는 헌혈증이 이미 있다면 그 헌혈증의 정보 반환
        }
        
        Optional<DonorCard> savedDonorCard = donorCardRepository.saveDonorCard(donorCard);
        
        return savedDonorCard;
    }

    @Override
    public Optional<DonorCard> findDonorCard(String certificateNumber) throws SQLException{
        Optional<DonorCard> findDonorCard = donorCardRepository.findDonorCardByCertificateNumber(certificateNumber);
        if(!findDonorCard.isEmpty()){
            // return 해당 증서번호의 헌혈증이 없어요 라는 에러 발생
        }
        return findDonorCard;
    }

    @Override
    public List<DonorCard> findAllDonorCards() throws SQLException{
        return donorCardRepository.findAllDonorCards();
    }

    @Override
    public void deleteDonorCard(String certificateNumber) {
        donorCardRepository.delete(certificateNumber);
    }

    @Override
    public void updateDonorCard(String certificateNumber, DonorCard updateDonorCard) {
        donorCardRepository.update(certificateNumber, updateDonorCard);
    }
}
