package fx.redbox.service.donorCard;

import fx.redbox.entity.donorCards.DonorCard;

import java.util.List;
import java.util.Map;

public interface DonorCardService {
    void join(DonorCard donorCard);
    List<Map<String, Object>> findDonorCard(String certificateNumber);
    void deleteDonorCard(String certificateNumber);
    void updateDonorCard(String certificateNumber, DonorCard updateDonorCard);
}
