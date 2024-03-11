package fx.redbox.service.statistics;

import fx.redbox.controller.statistics.form.StatisticsForm;
import fx.redbox.entity.donorCards.DonorCard;
import fx.redbox.entity.enums.DonorBloodKind;
import fx.redbox.entity.enums.Gender;
import fx.redbox.entity.users.User;
import fx.redbox.repository.donorCard.DonorCardRepository;
import fx.redbox.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StatisticsService {

    private DonorCardRepository donorCardRepository;
    private UserService userService;

    public StatisticsForm showStatistics() throws SQLException {

        List<DonorCard> allDonorCards = donorCardRepository.findAllDonorCards();

        int whole = 0; //전혈
        int plasma = 0 ; //혈장성분
        int platelet = 0; //혈소판성분
        int plateletPlasma = 0; //혈소판혈장성분

        int man = 0, woman = 0;

        int age10 = 0, age20 = 0, age30 = 0, age40 = 0, age50 = 0;

        int totalDonors = allDonorCards.size(); // 전체 헌혈자 수

        //헌혈종류, 성별, 나이대 수집
        GatheringStatisticsData gatheringStatisticsData = getGatheringStatisticsData(allDonorCards, whole, plasma, platelet, plateletPlasma, man, woman, age10, age20, age30, age40, age50);

        // 백분율 계산
        CalculatePercentage calculatePercentage = getCalculatePercentage(gatheringStatisticsData, (double) totalDonors);


        return StatisticsForm.builder()
                .whole(calculatePercentage.wholePercentage())
                .plasma(calculatePercentage.plasmaPercentage())
                .platelet(calculatePercentage.plateletPercentage())
                .plateletPlasma(calculatePercentage.plateletPlasmaPercentage())
                .man(calculatePercentage.manPercentage())
                .woman(calculatePercentage.womanPercentage())
                .age10(calculatePercentage.age10Percentage())
                .age20(calculatePercentage.age20Percentage())
                .age30(calculatePercentage.age30Percentage())
                .age40(calculatePercentage.age40Percentage())
                .age50(calculatePercentage.age50Percentage())
                .build();
    }




    private static CalculatePercentage getCalculatePercentage(GatheringStatisticsData gatheringStatisticsData, double totalDonors) {
        double wholePercentage = (gatheringStatisticsData.whole() / totalDonors) * 100;
        double plasmaPercentage = (gatheringStatisticsData.plasma() / totalDonors) * 100;
        double plateletPercentage = (gatheringStatisticsData.platelet() / totalDonors) * 100;
        double plateletPlasmaPercentage = (gatheringStatisticsData.plateletPlasma() / totalDonors) * 100;

        double manPercentage = (gatheringStatisticsData.man() / totalDonors) * 100;
        double womanPercentage = (gatheringStatisticsData.woman() / totalDonors) * 100;

        double age10Percentage = (gatheringStatisticsData.age10() / totalDonors) * 100;
        double age20Percentage = (gatheringStatisticsData.age20() / totalDonors) * 100;
        double age30Percentage = (gatheringStatisticsData.age30() / totalDonors) * 100;
        double age40Percentage = (gatheringStatisticsData.age40() / totalDonors) * 100;
        double age50Percentage = (gatheringStatisticsData.age50() / totalDonors) * 100;
        CalculatePercentage calculatePercentage = new CalculatePercentage(wholePercentage, plasmaPercentage, plateletPercentage, plateletPlasmaPercentage, manPercentage, womanPercentage, age10Percentage, age20Percentage, age30Percentage, age40Percentage, age50Percentage);
        return calculatePercentage;
    }

    private record CalculatePercentage(double wholePercentage, double plasmaPercentage, double plateletPercentage, double plateletPlasmaPercentage, double manPercentage, double womanPercentage, double age10Percentage, double age20Percentage, double age30Percentage, double age40Percentage, double age50Percentage) {
    }

    private GatheringStatisticsData getGatheringStatisticsData(List<DonorCard> allDonorCards, int whole, int plasma, int platelet, int plateletPlasma, int man, int woman, int age10, int age20, int age30, int age40, int age50) {
        for (DonorCard donorCard : allDonorCards) {

            //헌혈 종류 수집
            DonorBloodKind donorBloodKind = donorCard.getDonorBloodKind();
            if(donorBloodKind.equals(DonorBloodKind.전혈)) {
                whole++;
            } else if(donorBloodKind.equals(DonorBloodKind.혈장성분)) {
                plasma++;
            } else if(donorBloodKind.equals(DonorBloodKind.혈소판성분)) {
                platelet++;
            } else {
                plateletPlasma = plateletPlasma + 1;
            }

            
            //성별 수집
            Gender donorGender = donorCard.getDonorGender();
            if(donorGender.equals(Gender.남)) {
                man++;
            } else {
                woman++;
            }

            
            //나이대 수집
            Long userId = donorCard.getUserId();
            Optional<User> user = userService.findByUserId(userId);

            LocalDate birthDate = user.get().getBirth();

            int birthYear = birthDate.getYear();
            int currentYear = LocalDate.now().getYear();

            // 한국식 나이 계산
            int age = currentYear - birthYear + 1;

            if (age < 20) {
                age10++;
            } else if (age < 30) {
                age20++;
            } else if (age < 40) {
                age30++;
            } else if (age < 50) {
                age40++;
            } else {
                age50++;
            }
        }
        GatheringStatisticsData gatheringStatisticsData = new GatheringStatisticsData(whole, plasma, platelet, plateletPlasma, man, woman, age10, age20, age30, age40, age50);
        return gatheringStatisticsData;
    }

    private record GatheringStatisticsData(int whole, int plasma, int platelet, int plateletPlasma, int man, int woman, int age10, int age20, int age30, int age40, int age50) {
    }


}
