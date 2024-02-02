package fx.redbox.repository.donorcardrequest;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.repository.mappper.DonorCardRequestMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

public class DonorCardRequestRepositoryImpl implements DonorCardRequestRepository {

    private final JdbcTemplate jdbcTemplate;

    public DonorCardRequestRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm) {

        // request 테이블
        SimpleJdbcInsert requestInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("donorcard_request")
                .usingGeneratedKeyColumns("donorcard_request_id");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("donorcard_request_permission", donorCardRequest.getDonorCardRequestPermission());
        parameters.addValue("donorcard_request_reject_reason", donorCardRequest.getDonorCardRequestRejectReason());
        parameters.addValue("user_id", donorCardRequest.getUserId());

        Long donorCardRequestId = requestInsert.executeAndReturnKey(parameters).longValue();
        donorCardRequest.setDonorCardRequestId(donorCardRequestId);

        // request_form 테이블
        SimpleJdbcInsert requestFormInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("donorcard_request_form")
                .usingGeneratedKeyColumns("donorcard_request_id");

        MapSqlParameterSource formParameters = new MapSqlParameterSource();
        formParameters.addValue("donorcard_request_id", donorCardRequestId);
        formParameters.addValue("patient_name", donorCardRequestForm.getPatientName());
        formParameters.addValue("evidence_document", donorCardRequestForm.getEvidenceDocument());
        formParameters.addValue("patient_gender", donorCardRequestForm.getPatientGender());
        formParameters.addValue("blood_type", donorCardRequestForm.getBloodType());
        formParameters.addValue("donorcard_request_date", donorCardRequestForm.getDonorCardRequestDate());

        requestFormInsert.execute(formParameters);

        return donorCardRequest;
    }

    @Override
    public DonorCardRequest getDonorCardRequestById(String requestId) {
        String sql = "SELECT * FROM request WHERE request_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{requestId}, new DonorCardRequestMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<DonorCardRequest> getAllDonorCardRequests() {
        String sql = "SELECT * FROM request";
        return jdbcTemplate.query(sql, new DonorCardRequestMapper());
    }

}
