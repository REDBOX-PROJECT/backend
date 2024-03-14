package fx.redbox.repository.mappper;

import fx.redbox.entity.donorCards.DonorCardRequestApproval;
import fx.redbox.entity.donorCards.DonorCardRequestForm;
import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.DonorCardRequestRejectReason;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.enums.RejectPermission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DonorCardRequestMapper implements RowMapper<DonorCardRequestForm> {

    @Override
    public DonorCardRequestForm mapRow(ResultSet rs, int rowNum) throws SQLException {



        DonorCardRequestForm donorCardRequestForm = DonorCardRequestForm.builder()
                .donorCardRequestId(rs.getLong("donorcard_request_id"))
                .patientName(rs.getString("patient_name"))
                .birth(rs.getTimestamp("birth").toLocalDateTime().toLocalDate())
                .evidenceDocument(rs.getString("evidence_document"))
                .patientGender(Gender.valueOf(rs.getString("patient_gender")))
                .hospitalName(rs.getString("hospital_name"))
                .bloodType(BloodType.valueOf(rs.getString("blood_type")))
                .donorCardRequestDate(rs.getTimestamp("donorcard_request_date").toLocalDateTime())
                .userId(rs.getLong("user_id"))
                .build();

        DonorCardRequestApproval approval = DonorCardRequestApproval.builder()
                .donorCardRequestId(rs.getLong("donorcard_request_id"))
                .donorCardRequestPermission(RejectPermission.valueOf(rs.getString("donorcard_request_permission")))
                .donorCardRequestRejectReason(DonorCardRequestRejectReason.valueOf(rs.getString("rejection_reason")))
                .build();

        donorCardRequestForm.setDonorCardRequestApproval(approval);

        return donorCardRequestForm;
    }
}
