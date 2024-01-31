package fx.redbox.repository.request;

import fx.redbox.entity.donorCards.Request;
import fx.redbox.entity.donorCards.RequestForm;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.List;

public class RequestRepositoryImpl implements RequestRepository {

    private final JdbcTemplate jdbcTemplate;

    public RequestRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Request saveRequest(Request request, RequestForm requestForm) {

        // request 테이블
        SimpleJdbcInsert requestInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("request")
                .usingGeneratedKeyColumns("request_id");

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("request_permission", request.getRequestPermission());
        parameters.addValue("rejection_reason", request.getRejectionReason());
        parameters.addValue("user_id", request.getUserId());

        Long requestId = requestInsert.executeAndReturnKey(parameters).longValue();
        request.setRequestId(requestId);

        // request_form 테이블
        SimpleJdbcInsert requestFormInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("request_form")
                .usingGeneratedKeyColumns("request_id");

        MapSqlParameterSource formParameters = new MapSqlParameterSource();
        formParameters.addValue("request_id", requestId);
        formParameters.addValue("patient_name", requestForm.getPatientName());
        formParameters.addValue("evidence_document", requestForm.getEvidenceDocument());
        formParameters.addValue("patient_gender", requestForm.getPatientGender());
        formParameters.addValue("blood_type", requestForm.getBloodType());
        formParameters.addValue("request_date", requestForm.getRequestDate());

        requestFormInsert.execute(formParameters);

        return request;
    }

    @Override
    public Request getRequestById(String requestId) {
        return null;
    }

    @Override
    public List<Request> getAllRequests() {
        return null;
    }

    @Override
    public void acceptRequest(String requestId) {

    }

    @Override
    public void rejectRequest(String requestId, String rejectionReason) {

    }

    @Override
    public void cancelRequest(String requestId) {

    }

}
