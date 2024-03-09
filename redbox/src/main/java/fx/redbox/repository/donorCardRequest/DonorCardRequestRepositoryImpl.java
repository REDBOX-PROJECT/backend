package fx.redbox.repository.donorCardRequest;

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
    public void saveDonorCardRequestForm(DonorCardRequestForm donorCardRequestForm) {

        // donorCardRequestForm 테이블
        SimpleJdbcInsert donorCardRequestFormJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("donorcard_request_forms")
                .usingGeneratedKeyColumns("donorcard_request_id");
        Map<String, Object> donorCardRequestFormParam = new ConcurrentHashMap<>();
        donorCardRequestFormParam.put("patient_name", donorCardRequestForm.getPatientName());
        donorCardRequestFormParam.put("evidence_document", donorCardRequestForm.getEvidenceDocument());
        donorCardRequestFormParam.put("patient_gender", donorCardRequestForm.getPatientGender().name());
        donorCardRequestFormParam.put("birth", donorCardRequestForm.getBirth());
        donorCardRequestFormParam.put("hospital_name", donorCardRequestForm.getHospitalName());
        donorCardRequestFormParam.put("blood_type", donorCardRequestForm.getBloodType().name());
        donorCardRequestFormParam.put("donorcard_request_date", donorCardRequestForm.getDonorCardRequestDate());
        donorCardRequestFormParam.put("user_id", donorCardRequestForm.getUserId());

        Long donorcard_request_id = donorCardRequestFormJdbcInsert.executeAndReturnKey(donorCardRequestFormParam).longValue();

        //donorcard_request_approval
        SimpleJdbcInsert donorCardRequestApprovalInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("donorcard_request_approval");
        Map<String, Object> donorCardRequestApprovalParam = new ConcurrentHashMap<>();
        donorCardRequestApprovalParam.put("donorcard_request_id", donorcard_request_id);
        donorCardRequestApprovalParam.put("donorcard_request_permission", RejectPermission.심사중.name()); //default 심사중
        donorCardRequestApprovalParam.put("rejection_reason", DonorCardRequestRejectReason.심사중.name()); //default 심사중
        donorCardRequestApprovalInsert.execute(donorCardRequestApprovalParam);

    }

    @Override
    public List<DonorCardRequestForm> getAllDonorCardRequests() {
        String sql = "SELECT * FROM donorcard_request_forms dcrf" +
                " LEFT JOIN donorcard_request_approval dcra" +
                " ON dcrf.donorcard_request_id = dcra.donorcard_request_id";

        return jdbcTemplate.query(sql, donorCardRequestMapper);
    }

     @Override
    public Optional<DonorCardRequestForm> getDonorCardRequestByDonorCardRequestId(Long donorCardRequestId) {
        String sql = "SELECT * FROM donorcard_request_forms" +
                " INNER JOIN donorcard_request_approval" +
                " ON donorcard_request_forms.donorcard_request_id = donorcard_request_approval.donorcard_request_id" +
                " WHERE donorcard_request_forms.donorcard_request_id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new Object[]{donorCardRequestId}, donorCardRequestMapper));
    }

    @Override
    public void updateDonorCardRequestReview(Long donorCardRequestId,
                                             String rejectPermission,
                                             String rejectReason) {
        String sql = "UPDATE donorcard_request_approval" +
                " SET donorcard_request_permission = ?, rejection_reason = ?" +
                " WHERE donorcard_request_id = ?";
        jdbcTemplate.update(sql, rejectPermission, rejectReason, donorCardRequestId);
    }


}