package fx.redbox.repository;

import fx.redbox.entity.donorCards.DonorCard;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DonorCardRepositoryImpl implements DonorCardRepository{
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(DonorCard donorCard) {
        String INSERT = "insert into donor_cards values(?,?,?,?,?,?,?)";
        jdbcTemplate.update(INSERT,
                donorCard.getCertificateNumber(),
                donorCard.getDonorName(),
                donorCard.getDonorBirth(),
                donorCard.getDonorBloodKind(),
                donorCard.getDonorGender(),
                donorCard.getBloodCenter(),
                donorCard.getUserId());

    }

}
