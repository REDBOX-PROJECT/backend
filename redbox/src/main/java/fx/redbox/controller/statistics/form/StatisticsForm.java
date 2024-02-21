package fx.redbox.controller.statistics.form;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.DonorBloodKind;
import fx.redbox.entity.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatisticsForm {

    private double whole; //전혈
    private double plasma; //혈장성분
    private double platelet; //혈소판성분
    private double plateletPlasma; //혈소판혈장성분
    private double man;
    private double woman;
    private double age10;
    private double age20;
    private double age30;
    private double age40;
    private double age50;

}
