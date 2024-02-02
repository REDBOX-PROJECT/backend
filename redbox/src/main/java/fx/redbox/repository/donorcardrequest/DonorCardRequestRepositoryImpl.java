package fx.redbox.repository.donorcardrequest;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.repository.mappper.DonorCardRequestMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;
import java.util.NoSuchElementException;

public class DonorCardRequestRepositoryImpl implements DonorCardRequestRepository {

    private final JdbcTemplate jdbcTemplate;

    public DonorCardRequestRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm) {

        // request 테이블
        SimpleJdbcInsert donorCardRequestInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("donorcard_request")
                .usingGeneratedKeyColumns("donorcard_request_id");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("donorcard_request_permission", donorCardRequest.getDonorCardRequestPermission());
        parameters.addValue("donorcard_request_reject_reason", donorCardRequest.getDonorCardRequestRejectReason());
        parameters.addValue("user_id", donorCardRequest.getUserId());

        Long donorCardRequestId = donorCardRequestInsert.executeAndReturnKey(parameters).longValue();
        donorCardRequest.setDonorCardRequestId(donorCardRequestId);

        // request_form 테이블
        SimpleJdbcInsert donorCardRequestFormInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("donorcard_request_form")
                .usingGeneratedKeyColumns("donorcard_request_id");

        MapSqlParameterSource formParameters = new MapSqlParameterSource();
        formParameters.addValue("donorcard_request_id", donorCardRequestId);
        formParameters.addValue("patient_name", donorCardRequestForm.getPatientName());
        formParameters.addValue("evidence_document", donorCardRequestForm.getEvidenceDocument());
        formParameters.addValue("patient_gender", donorCardRequestForm.getPatientGender());
        formParameters.addValue("blood_type", donorCardRequestForm.getBloodType());
        formParameters.addValue("donorcard_request_date", donorCardRequestForm.getDonorCardRequestDate());

        donorCardRequestFormInsert.execute(formParameters);

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
    public DonorCardRequest updateDonorCardRequest(DonorCardRequestForm donorCardRequestForm) {

        String sql = "UPDATE donorCardRequestForm SET patient_name = ?," +
                " evidence_document = ?," +
                " patient_gender = ?," +
                " blood_type = ?," +
                " donorcard_request_date = ?" +
                " WHERE donorcard_request_id = ?";
        jdbcTemplate.update(sql,
                donorCardRequestForm.getPatientName(),
                donorCardRequestForm.getEvidenceDocument(),
                donorCardRequestForm.getPatientGender(),
                donorCardRequestForm.getBloodType(),
                donorCardRequestForm.getDonorCardRequestDate(),
                donorCardRequestForm.getDonorCardRequestId());

        return getDonorCardRequestById(String.valueOf(donorCardRequestForm.getDonorCardRequestId()));
    }


    @Override
    public void deleteDonorCardRequest(String donorCardRequestId) {
        String sql = "DELETE FROM donor_card_request WHERE donor_card_request_id = ?";
        jdbcTemplate.update(sql, donorCardRequestId);
    }

}
