package fx.redbox.entity.donorCards;

import fx.redbox.entity.enums.DonorBloodKind;
import fx.redbox.entity.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class DonorCard {
    private String certificateNumber;
    private String donorName;
    private Date donorBirth;
    private DonorBloodKind donorBloodKind;
    private Gender donorGender;
    private String bloodCenter;
    private Long userId;

    @Builder
    public DonorCard(String certificateNumber, String donorName, Date donorBirth, DonorBloodKind donorBloodKind, Gender donorGender, String bloodCenter, Long userId){
        this.certificateNumber = certificateNumber;
        this.donorName = donorName;
        this.donorBirth = donorBirth;
        this.donorBloodKind = donorBloodKind;
        this.donorGender = donorGender;
        this.bloodCenter = bloodCenter;
        this.userId = userId;
    }
}
