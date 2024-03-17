package fx.redbox.controller.statistics.form;

import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class StatisticsForm {
    private Map<String, Double> bloodTypeStatistics;
    private Map<String, Double> genderStatistics;
    private Map<String, Double> ageGroupStatistics;

    public StatisticsForm(Map<String, Double> bloodTypeStatistics, Map<String, Double> genderStatistics, Map<String, Double> ageGroupStatistics) {
        this.bloodTypeStatistics = bloodTypeStatistics;
        this.genderStatistics = genderStatistics;
        this.ageGroupStatistics = ageGroupStatistics;
    }


}
