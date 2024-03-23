package fx.redbox.service.donorCard;

import fx.redbox.controller.donorCard.form.ReadAllDonorCardForm;
import fx.redbox.controller.donorCard.form.ReadDonorCardForm;
import fx.redbox.controller.donorCard.form.RedBoxDashboardInfo;
import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.entity.users.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DonorCardService {

    Optional<DonorCard> saveDonorCard(DonorCard donorCard) throws SQLException;

    Optional<ReadDonorCardForm> findDonorCard(String certificateNumber) throws SQLException;

    List<ReadAllDonorCardForm> findAllDonorCards(User user);

    RedBoxDashboardInfo readRedBoxDashboard(User user);
  
    void redboxGive(String certificateNumber, User user);
}
