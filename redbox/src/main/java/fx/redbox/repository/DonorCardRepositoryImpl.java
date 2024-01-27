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

    @Override
    public List<Map<String, Object>> findById(String certificateNumber) {
        String FIND = "select * from donor_cards where cefiticate_number=?";
        return jdbcTemplate.queryForList(FIND, certificateNumber);
    }

    @Override
    public List<Map<String, Object>> findAll() {
        String FIND_ALL = "select * from donor_cards";
        List<Map<String, Object>> donorCards =  jdbcTemplate.queryForList(FIND_ALL);
        return donorCards;
    }

    @Override
    public void update(String certificateNumber, DonorCard updateDonorCard) {
        String UPDATE = "update donor_cards set donor_name=?," +
                "donor_birth=?, donor_blood_kind=?, donor_gender=?" +
                "donor_blood_center=? where cefiticate_number=?";
        jdbcTemplate.update(UPDATE,
                updateDonorCard.getDonorName(),
                updateDonorCard.getDonorBirth(),
                updateDonorCard.getDonorBloodKind(),
                updateDonorCard.getDonorGender(),
                updateDonorCard.getBloodCenter(),
                certificateNumber);
    }

    @Override
    public void delete(String certificateNumber) {
        String DELETE = "select * from donor_cards where cefiticate_number=?";
        jdbcTemplate.queryForList(DELETE, certificateNumber);
    }

}
