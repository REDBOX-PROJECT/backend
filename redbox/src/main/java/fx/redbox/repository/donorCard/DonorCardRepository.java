package fx.redbox.repository.donorCard;

import fx.redbox.entity.donorCards.DonorCard;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DonorCardRepository {

    Optional<DonorCard> saveDonorCard(DonorCard donorCard);

    boolean existsDonorCardByCertificateNumber(String certificateNumber);

    Optional<DonorCard> findDonorCardByCertificateNumber(String certificateNumber);

    List<DonorCard> findAllDonorCards(Long userId);

    List<DonorCard> findAllDonorCards();

    void deleteDonorCard(String certificateNumber);

    int countDonorCardByUserId(Long userId);
  
    void assignOwnerToDonorCard(String certificateNumber, Long userId);
}
