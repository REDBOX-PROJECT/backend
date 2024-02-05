package fx.redbox.repository.donorCard;

import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.entity.enums.DonorBloodKind;
import fx.redbox.entity.enums.Gender;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DonorCardRepositoryImpl implements DonorCardRepository{
    private SimpleJdbcInsert simpleJdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<DonorCard> saveDonorCard(DonorCard donorCard) {
        Map<String,Object> donorCardParam = new HashMap<>();

        donorCardParam.put("certificate_number",donorCard.getCertificateNumber());
        donorCardParam.put("donor_name",donorCard.getDonorName());
        donorCardParam.put("donor_birth",donorCard.getDonorBirth());
        donorCardParam.put("donor_blood_kind",donorCard.getDonorBloodKind());
        donorCardParam.put("donor_gender",donorCard.getDonorGender());
        donorCardParam.put("blood_center",donorCard.getBloodCenter());
        donorCardParam.put("user_id",donorCard.getUserId());

        simpleJdbcInsert.execute(donorCardParam);

        return Optional.of(donorCard);
    }

    @Override
    public Optional<DonorCard> findDonorCardByCertificateNumber(String certificateNumber){
        String FIND = "select * from donor_cards where cefiticate_number=?";
        try{
            DonorCard donorCard = jdbcTemplate.queryForObject(FIND, donorCardRowMapper(),certificateNumber);
            return Optional.of(donorCard);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

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
