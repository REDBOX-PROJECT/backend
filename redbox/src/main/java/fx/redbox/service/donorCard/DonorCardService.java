package fx.redbox.service.donorCard;

import fx.redbox.entity.donorCards.DonorCard;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DonorCardService {

    Optional<DonorCard> saveDonorCard(DonorCard donorCard) throws SQLException;

    Optional<DonorCard> findDonorCard(String certificateNumber) throws SQLException;

    List<DonorCard> findAllDonorCards() throws SQLException;

    void deleteDonorCard(String certificateNumber) throws SQLException;

    void updateDonorCard(String certificateNumber, DonorCard updateDonorCard) throws SQLException;

}
