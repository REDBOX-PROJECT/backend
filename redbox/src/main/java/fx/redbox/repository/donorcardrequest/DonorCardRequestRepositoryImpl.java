package fx.redbox.repository.donorCardRequest;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.RejectPermission;
import fx.redbox.repository.mappper.DonorCardRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class DonorCardRequestRepositoryImpl implements DonorCardRequestRepository {

    private final JdbcTemplate jdbcTemplate;
    private final DonorCardRequestMapper donorCardRequestMapper;

    @Override
    public DonorCardRequest createDonorCardRequest(DonorCardRequest donorCardRequest, DonorCardRequestForm donorCardRequestForm) {

        // donorCardRequestForm 테이블
        SimpleJdbcInsert donorCardRequestFormJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("donorcard_request_forms")
                .usingGeneratedKeyColumns("donorcard_request_id");
        Map<String, Object> donorCardRequestFormParam = new ConcurrentHashMap<>();
        donorCardRequestFormParam.put("patient_name", donorCardRequestForm.getPatientName());
        donorCardRequestFormParam.put("evidence_document", donorCardRequestForm.getEvidenceDocument());
        donorCardRequestFormParam.put("patient_gender", donorCardRequestForm.getPatientGender());
        donorCardRequestFormParam.put("blood_type", donorCardRequestForm.getBloodType());
        donorCardRequestFormParam.put("donorcard_request_date", donorCardRequestForm.getDonorCardRequestDate());

        // donorCardRequest 테이블
        SimpleJdbcInsert donorCardRequestJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("donorcard_requests")
                .usingGeneratedKeyColumns("donorcard_request_id");
        Map<String, Object> donorCardRequestParam = new ConcurrentHashMap<>();
        donorCardRequestParam.put("donorcard_request_permission", donorCardRequest.getDonorCardRequestPermission());
        donorCardRequestParam.put("donorcard_request_reject_reason", donorCardRequest.getDonorCardRequestRejectReason());
        donorCardRequestParam.put("user_id", donorCardRequest.getUserId());
        donorCardRequestParam.put("donorcard_request_id", donorCardRequestFormJdbcInsert.executeAndReturnKey(donorCardRequestFormParam).longValue());
        donorCardRequestJdbcInsert.executeAndReturnKey(donorCardRequestParam).longValue();

        return donorCardRequest;
    }

    @Override
    public Optional<DonorCardRequest> getDonorCardRequestById(Long donorCardRequestId) {
        String sql = "SELECT * FROM donorcard_requests " +
                "JOIN donorcard_request_forms ON donorcard_requests.donorcard_request_id " +
                "WHERE donorcard_requests.donorcard_request_id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{donorCardRequestId}, donorCardRequestMapper));
    }

    @Override
    public List<DonorCardRequest> getAllDonorCardRequests() {
        String sql = "SELECT * FROM donorcard_requests " +
                "JOIN donorcard_request_forms ON donorcard_requests.donorcard_request_id";

        return jdbcTemplate.query(sql, donorCardRequestMapper);
    }

    @Override
    public void updateDonorCardRequest(Long donorCardRequestId, RejectPermission donorCardRequestPermission, DonorCardRequestRejectReason donorCardRequestRejectReason) {
        String sql = "UPDATE donorcard_requests SET donorcard_request_permission = ?, donorcard_request_reject_reason = ? WHERE donorcard_request_id = ?";
        jdbcTemplate.update(sql, donorCardRequestPermission, donorCardRequestRejectReason, donorCardRequestId);

    }

    @Override
    public void updateDonorCardRequestForm(Long donorCardRequestId, String evidenceDocument) {
        String sql = "UPDATE donorcard_request_forms SET evidence_document = ? WHERE donorcard_request_id = ?";
        jdbcTemplate.update(sql, evidenceDocument, donorCardRequestId);
    }

    @Override
    public void deleteDonorCardRequest(Long donorCardRequestId) {
        String deleteFormsql = "DELETE FROM donorcard_request_forms WHERE donorcard_request_id = ?";
        String deleterequestsql = "DELETE FROM donorcard_requests WHERE donorcard_request_id = ?";

        jdbcTemplate.update(deleteFormsql, donorCardRequestId);
        jdbcTemplate.update(deleterequestsql, donorCardRequestId);
    }

}