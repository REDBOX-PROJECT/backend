package fx.redbox.repository;

import fx.redbox.entity.donorCards.DonorCard;

import java.util.List;
import java.util.Map;

public interface DonorCardRepository {
    void save(DonorCard donorCard);
    List<Map<String, Object>> findById(String certificateNumber);
    List<Map<String, Object>> findAll();
    void update(String certificateNumber, DonorCard updateDonorCard);
    void delete(String certificateNumber);
}
