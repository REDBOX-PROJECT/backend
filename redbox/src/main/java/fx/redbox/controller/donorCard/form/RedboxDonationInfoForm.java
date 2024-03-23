package fx.redbox.controller.donorCard.form;

import lombok.Data;

@Data
public class RedboxDonationInfoForm {

    private int countTotalBloodType;
    private int countBloodTypeA;
    private int countBloodTypeB;
    private int countBloodTypeAB;
    private int countBloodTypeO;

    // 기본 생성자
    public RedboxDonationInfoForm() {
    }

    public RedboxDonationInfoForm(int countTotalBloodType, int countBloodTypeA, int countBloodTypeB, int countBloodTypeAB, int countBloodTypeO) {
        this.countTotalBloodType = countTotalBloodType;
        this.countBloodTypeA = countBloodTypeA;
        this.countBloodTypeB = countBloodTypeB;
        this.countBloodTypeAB = countBloodTypeAB;
        this.countBloodTypeO = countBloodTypeO;
    }
}