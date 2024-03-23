package fx.redbox.controller.donorCard.form;

public class RedBoxDashboardInfo {
    private int totalDonorCards;
    private int userDonorCards;
    private double contributionRate;

    public RedBoxDashboardInfo(int totalDonorCards, int userDonorCards, double contributionRate) {
        this.totalDonorCards = totalDonorCards;
        this.userDonorCards = userDonorCards;
        this.contributionRate = contributionRate;
    }

    // Getter 메소드들
    public int getTotalDonorCards() {
        return totalDonorCards;
    }

    public int getUserDonorCards() {
        return userDonorCards;
    }

    public double getContributionRate() {
        return contributionRate;
    }
}
