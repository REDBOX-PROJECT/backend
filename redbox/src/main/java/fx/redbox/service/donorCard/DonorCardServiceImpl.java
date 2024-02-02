package fx.redbox.service.donorCard;

import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.repository.donorCard.DonorCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DonorCardServiceImpl implements DonorCardService{
    private final DonorCardRepository donorCardRepository;
    @Override
    public void join(DonorCard donorCard) {
        donorCardRepository.save(donorCard);
    }

    @Override
    public List<Map<String, Object>> findDonorCard(String certificateNumber) {
        return donorCardRepository.findById(certificateNumber);
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
