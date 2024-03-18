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

    @Value("${donorcard.column.donorcard_request_id}") private String donorCardRequestId;
    @Value("${donorcard.column.patient_name}") private String patientName;
    @Value("${donorcard.column.birth}") private String birth;
    @Value("${donorcard.column.evidence_document}") private String evidenceDocument;
    @Value("${donorcard.column.patient_gender}") private String patientGender;
    @Value("${donorcard.column.hospital_name}") private String hospitalName;
    @Value("${donorcard.column.blood_type}") private String bloodType;
    @Value("${donorcard.column.donorcard_request_date}") private String donorCardRequestDate;
    @Value("${donorcard.column.user_id}") private String userId;
    @Value("${donorcard.column.donorcard_request_permission}") private String donorCardRequestPermission;
    @Value("${donorcard.column.rejection_reason}") private String donorCardRejectionReason;

    @Override
    public DonorCardRequestForm mapRow(ResultSet rs, int rowNum) throws SQLException {

        DonorCardRequestForm donorCardRequestForm = DonorCardRequestForm.builder()
                .donorCardRequestId(rs.getLong(donorCardRequestId))
                .patientName(rs.getString(patientName))
                .birth(rs.getTimestamp(birth).toLocalDateTime().toLocalDate())
                .evidenceDocument(rs.getString(evidenceDocument))
                .patientGender(Gender.valueOf(rs.getString(patientGender)))
                .hospitalName(rs.getString(hospitalName))
                .bloodType(BloodType.valueOf(rs.getString(bloodType)))
                .donorCardRequestDate(rs.getTimestamp(donorCardRequestDate).toLocalDateTime())
                .userId(rs.getLong(userId))
                .build();

        DonorCardRequestApproval approval = DonorCardRequestApproval.builder()
                .donorCardRequestId(rs.getLong(donorCardRequestId))
                .donorCardRequestPermission(RejectPermission.valueOf(rs.getString(donorCardRequestPermission)))
                .donorCardRequestRejectReason(DonorCardRequestRejectReason.valueOf(rs.getString(donorCardRejectionReason)))
                .build();

        donorCardRequestForm.setDonorCardRequestApproval(approval);

        return donorCardRequestForm;
    }
}
