package fx.redbox.repository.mappper;

import fx.redbox.entity.donorCards.DonorCardRequest;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.RejectPermission;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DonorCardRequestMapper implements RowMapper<DonorCardRequest> {
    @Override
    public DonorCardRequest mapRow(ResultSet rs, int rowNum) throws SQLException {

        DonorCardRequestForm donorCardRequestForm = DonorCardRequestForm.builder()
                .donorCardRequestId(rs.getLong("donorcard_request_id"))
                .patientName(rs.getString("patient_name"))
                .evidenceDocument(rs.getString("evidence_document"))
                .patientGender(Gender.valueOf(rs.getString("patient_gender")))
                .bloodType(BloodType.valueOf(rs.getString("blood_type")))
                .donorCardRequestDate(rs.getTimestamp("donorcard_request_date"))
                .build();

        DonorCardRequest donorCardRequest = DonorCardRequest.builder()
                .donorCardRequestId(rs.getLong("donorcard_request_id"))
                .donorCardRequestPermission(RejectPermission.valueOf(rs.getString("donorcard_request_permission")))
                .donorCardRequestRejectReason(rs.getString("donorcard_request_reject_reason"))
                .userId(rs.getLong("user_id"))
                .donorCardRequestForm(donorCardRequestForm)  // RequestForm 객체 설정
                .build();

        return donorCardRequest;
    }
}