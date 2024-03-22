package fx.redbox.repository.donorCard;

import fx.redbox.common.Exception.DonorCardNotFoundException;
import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.entity.enums.DonorBloodKind;
import fx.redbox.entity.enums.Gender;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@AllArgsConstructor
public class DonorCardRepositoryImpl implements DonorCardRepository{
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<DonorCard> saveDonorCard(DonorCard donorCard) {
        SimpleJdbcInsert donorCardJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("donor_cards");
        Map<String, Object> donorCardParam = new ConcurrentHashMap<>();
        donorCardParam.put("certificate_number",donorCard.getCertificateNumber());
        donorCardParam.put("donor_name",donorCard.getDonorName());
        donorCardParam.put("donor_birth",donorCard.getDonorBirth());
        donorCardParam.put("donor_blood_kind",donorCard.getDonorBloodKind());
        donorCardParam.put("donation_date", donorCard.getDonationDate());
        donorCardParam.put("donor_gender", donorCard.getDonorGender().name()); //enum 을 string 형으로
        donorCardParam.put("blood_center",donorCard.getBloodCenter());
        donorCardParam.put("user_id",donorCard.getUserId());

        donorCardJdbcInsert.execute(donorCardParam);

        return Optional.of(donorCard);
    }

    @Override
    public boolean existsDonorCardByCertificateNumber(String certificateNumber) {
        String sql = "select count(*) from donor_cards where certificate_number = ?";
        int cnt = jdbcTemplate.queryForObject(sql, Integer.class, certificateNumber);
        return cnt > 0;
    }

    @Override
    public Optional<DonorCard> findDonorCardByCertificateNumber(String certificateNumber){
        String FIND = "select * from donor_cards where certificate_number=?";
        try {
            DonorCard donorCard = jdbcTemplate.queryForObject(FIND, new Object[]{certificateNumber}, donorCardRowMapper());
            return Optional.ofNullable(donorCard);
        } catch (EmptyResultDataAccessException e) {
            throw new DonorCardNotFoundException();
        }
    }


    @Override
    public List<DonorCard> findAllDonorCards(Long userId) {
        String FIND = "select * from donor_cards where user_id=?";
        List<DonorCard> donorCards = jdbcTemplate.query(FIND, donorCardRowMapper(), userId);
        return donorCards;
    }

    @Override //통계에 쓰여질 findAll
    public List<DonorCard> findAllDonorCards() {
        String FIND = "select * from donor_cards";
        List<DonorCard> donorCards = jdbcTemplate.query(FIND, donorCardRowMapper());
        return donorCards;
    }

    @Override
    public void deleteDonorCard(String certificateNumber) {
        String DELETE = "select * from donor_cards where cefiticate_number=?";
        jdbcTemplate.queryForList(DELETE, certificateNumber);
    }

    @Override
    public int countDonorCardByUserId(Long userId) {
        String sql = "select count(*) from donor_cards where user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    public RowMapper<DonorCard> donorCardRowMapper(){
        return((rs, rowNum) -> {
            DonorCard donorCard = DonorCard.builder()
                    .certificateNumber(rs.getString("certificate_number"))
                    .donorName(rs.getString("donor_name"))
                    .donorBloodKind(DonorBloodKind.valueOf(rs.getString("donor_blood_kind")))
                    .donorBirth(LocalDate.parse(rs.getString("donor_birth")))
                    .donationDate(LocalDate.parse(rs.getString("donation_date")))
                    .donorGender(Gender.valueOf(rs.getString("donor_gender")))
                    .bloodCenter(rs.getString("blood_center"))
                    .userId(rs.getLong("user_id")).build();
            return donorCard;
        });
    }

}