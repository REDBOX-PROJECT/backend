package fx.redbox.repository.donorcardrequest;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.repository.mappper.DonorCardRequestMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class DonorCardRequestRepositoryImpl implements DonorCardRequestRepository {

    private final JdbcTemplate jdbcTemplate;

    public DonorCardRequestRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm) {

        // DonorCardRequest 테이블에 데이터 삽입
        String sqlRequest = "INSERT INTO donorcard_request (donorcard_request_permission," +
                " donorcard_request_reject_reason," +
                " user_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sqlRequest, new String[] {"donorcard_request_id"});
            ps.setString(1, donorCardRequest.getDonorCardRequestPermission().toString());
            ps.setString(2, String.valueOf(donorCardRequest.getDonorCardRequestRejectReason()));
            ps.setLong(3, donorCardRequest.getUserId());
            return ps;
        }, keyHolder);

        Long donorCardRequestId = keyHolder.getKey().longValue();
        donorCardRequest.setDonorCardRequestId(donorCardRequestId);

        // DonorCardRequestForm 테이블에 데이터 삽입
        String sqlRequestForm = "INSERT INTO donorcard_request_form (donorcard_request_id," +
                " patient_name," +
                " evidence_document," +
                " patient_gender," +
                " blood_type," +
                " donorcard_request_date) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlRequestForm,
                donorCardRequest.getDonorCardRequestId(),
                donorCardRequestForm.getPatientName(),
                donorCardRequestForm.getEvidenceDocument(),
                donorCardRequestForm.getPatientGender().toString(),
                donorCardRequestForm.getBloodType().toString(),
                donorCardRequestForm.getDonorCardRequestDate());

        // DonorCardRequest 객체가 DonorCardRequestForm 객체를 참조하도록 설정
        donorCardRequest.setDonorCardRequestForm(donorCardRequestForm);

        return donorCardRequest;
    }

    @Override
    public DonorCardRequest getDonorCardRequestById(String donorCardRequestId) {

        String sql = "SELECT * FROM donorCardRequest WHERE donorcard_request_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{donorCardRequestId}, new DonorCardRequestMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("DonorCardRequest with id " + donorCardRequestId + " not found");
        }
    }


    @Override
    public List<DonorCardRequest> getAllDonorCardRequests() {
        String sql = "SELECT * FROM donorCardRequest";
        return jdbcTemplate.query(sql, new DonorCardRequestMapper());
    }

    @Override
    @Transactional
    public DonorCardRequest updateDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm) {
        // DonorCardRequest 업데이트 쿼리
        String sqlRequest = "UPDATE donorcard_request SET donorcard_request_permission = ?," +
                " donorcard_request_reject_reason = ?," +
                " user_id = ?" +
                " WHERE donorcard_request_id = ?";
        jdbcTemplate.update(sqlRequest, donorCardRequest.getDonorCardRequestPermission(), donorCardRequest.getDonorCardRequestRejectReason(), donorCardRequest.getUserId(), donorCardRequest.getDonorCardRequestId());

        // DonorCardRequestForm 업데이트 쿼리
        String sqlRequestForm = "UPDATE donorcard_request_form SET patient_name = ?," +
                " evidence_document = ?," +
                " patient_gender = ?," +
                " blood_type = ?," +
                " donorcard_request_date = ?" +
                " WHERE donorcard_request_id = ?";
        jdbcTemplate.update(sqlRequestForm,
                donorCardRequestForm.getPatientName(),
                donorCardRequestForm.getEvidenceDocument(),
                donorCardRequestForm.getPatientGender(),
                donorCardRequestForm.getBloodType(),
                donorCardRequestForm.getDonorCardRequestDate(),
                donorCardRequestForm.getDonorCardRequestId());

        // 업데이트된 DonorCardRequest 반환
        return getDonorCardRequestById(String.valueOf(donorCardRequest.getDonorCardRequestId()));
    }



    @Override
    public void deleteDonorCardRequest(String donorCardRequestId) {
        String sql = "DELETE FROM donor_card_request WHERE donor_card_request_id = ?";
        jdbcTemplate.update(sql, donorCardRequestId);
    }

}
