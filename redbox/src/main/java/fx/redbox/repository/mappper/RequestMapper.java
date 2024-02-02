package fx.redbox.repository.mappper;

import fx.redbox.entity.donorCards.Request;
import fx.redbox.entity.donorCards.RequestForm;
import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.RejectPermission;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RequestMapper implements RowMapper<Request> {
    @Override
    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {

        RequestForm requestForm = RequestForm.builder()
                .requestId(rs.getLong("request_id"))
                .patientName(rs.getString("patient_name"))
                .evidenceDocument(rs.getString("evidence_document"))
                .patientGender(Gender.valueOf(rs.getString("patient_gender")))
                .bloodType(BloodType.valueOf(rs.getString("blood_type")))
                .requestDate(rs.getTimestamp("request_date"))
                .build();

        Request request = Request.builder()
                .requestId(rs.getLong("request_id"))
                .requestPermission(RejectPermission.valueOf(rs.getString("request_permission")))
                .rejectionReason(rs.getString("rejection_reason"))
                .userId(rs.getLong("user_id"))
                .requestForm(requestForm)  // RequestForm 객체 설정
                .build();

        return request;
    }
}
