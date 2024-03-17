package fx.redbox.service.statistics;

import fx.redbox.controller.statistics.form.StatisticsForm;
import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.entity.enums.BloodType;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.users.User;
import fx.redbox.repository.donorCard.DonorCardRepository;
import fx.redbox.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@AllArgsConstructor
@Service
public class StatisticsService {

    private DonorCardRepository donorCardRepository;
    private UserService userService;

    public StatisticsForm showStatistics() {

        List<DonorCard> donorCards = donorCardRepository.findAllDonorCards();
        int totalDonorCardCount = donorCards.size();

        //혈액형
        Map<BloodType, Integer> bloodTypeCounts = new HashMap<>();
        //성별
        Map<Gender, Integer> genderCounts = new HashMap<>();
        //연령대
        Map<String, Integer> ageGroupCounts = new HashMap<>();

        // 혈액형, 성별, 연령대별로 수를 계산
        for (DonorCard donorCard : donorCards) {
            Optional<User> userOptional = userService.findByUserId(donorCard.getUserId());
            userOptional.ifPresent(user -> {
                bloodTypeCounts.merge(user.getBloodType(), 1, Integer::sum);
                genderCounts.merge(donorCard.getDonorGender(), 1, Integer::sum);

                // 연령대 계산
                int age = Period.between(user.getBirth(), LocalDate.now()).getYears();
                String ageGroup = getAgeGroup(age); // 연령대 구하기
                ageGroupCounts.merge(ageGroup, 1, Integer::sum);
            });
        }

        // 혈액형 통계 계산
        Map<String, Double> bloodTypeStatistics = calculateStatistics(bloodTypeCounts, totalDonorCardCount);
        // 성별 통계 계산
        Map<String, Double> genderStatistics = calculateStatistics(genderCounts, totalDonorCardCount);
        // 연령대 통계 계산
        Map<String, Double> ageGroupStatistics = calculateStatistics(ageGroupCounts, totalDonorCardCount);

        return new StatisticsForm(bloodTypeStatistics, genderStatistics, ageGroupStatistics);
    }

    private String getAgeGroup(int age) {
        if (age < 20) {
            return "10대";
        } else if (age < 30) {
            return "20대";
        } else if (age < 40) {
            return "30대";
        } else if (age < 50) {
            return "40대";
        } else {
            return "50대 이상";
        }
    }

    private Map<String, Double> calculateStatistics(Map<?, Integer> counts, int totalCount) {
        Map<String, Double> statistics = new HashMap<>();
        counts.forEach((key, value) -> {
            double percentage = (double) value / totalCount * 100;
            statistics.put(key.toString(), percentage);
        });
        return statistics;
    }
}