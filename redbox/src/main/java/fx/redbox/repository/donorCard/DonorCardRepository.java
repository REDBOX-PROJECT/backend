package fx.redbox.repository.donorCard;

import fx.redbox.entity.donorCards.DonorCard;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DonorCardRepository {

    Optional<DonorCard> saveDonorCard(DonorCard donorCard) throws SQLException;

    boolean existsDonorCardByCertificateNumber(String certificateNumber);

    Optional<DonorCard> findDonorCardByCertificateNumber(String certificateNumber) throws SQLException;

    List<DonorCard> findAllDonorCards(Long userId);

    List<DonorCard> findAllDonorCards();

    void updateDonorCardUserId(String certificateNumber);

}
