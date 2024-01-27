package fx.redbox.service;

import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.repository.DonorCardRepository;
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

}
