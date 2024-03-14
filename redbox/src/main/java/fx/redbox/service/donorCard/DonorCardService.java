package fx.redbox.service.donorCard;

import fx.redbox.controller.donorCard.form.ReadAllDonorCardForm;
import fx.redbox.entity.donorCards.DonorCard;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DonorCardService {

    Optional<DonorCard> saveDonorCard(String email, DonorCard donorCard) throws SQLException;

    Optional<DonorCard> findDonorCard(String email) throws SQLException;

    List<ReadAllDonorCardForm> findAllDonorCards(String email);

}
