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
    public List<DonorCard> findAllDonorCards() throws SQLException{
        String FIND_ALL = "select * from donor_cards";
        List<DonorCard> donorCards =  jdbcTemplate.query(FIND_ALL,donorCardRowMapper());

        return donorCards;
    }

    @Override
    public void updateDonorCard(String certificateNumber, DonorCard updateDonorCard) throws SQLException{
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
    public void deleteDonorCard(String certificateNumber) throws SQLException{
        String DELETE = "select * from donor_cards where cefiticate_number=?";
        jdbcTemplate.queryForList(DELETE, certificateNumber);
    }

    private RowMapper<DonorCard> donorCardRowMapper(){
        return((rs, rowNum) -> {
            DonorCard donorCard = DonorCard.builder()
                    .certificateNumber(rs.getString("certificate_number"))
                    .donorName(rs.getString("donor_name"))
                    .donorBloodKind(DonorBloodKind.valueOf(rs.getString("donor_blood_kind")))
                    .donorGender(Gender.valueOf(rs.getString("donor_gender")))
                    .bloodCenter(rs.getString("blood_center"))
                    .userId(rs.getLong("user_id")).build();
            return donorCard;
        });
    }

}