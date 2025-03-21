public class RegularMember extends GymMember {
    private final int attendanceLimit = 30;
    private boolean isEligibleForUpgrade;
    private String removalReason;
    private String referralSource;
    private String plan;
    private double price;

    public RegularMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String referralSource) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.referralSource = referralSource;
        this.plan = "basic";
        this.price = 6500;
        this.isEligibleForUpgrade = false;
        this.removalReason = "";
    }

    @Override
    public void markAttendance() {
        if (activeStatus) {
            attendance++;
            loyaltyPoints += 5;
            if (attendance >= attendanceLimit) isEligibleForUpgrade = true;
        }
    }

    public double getPlanPrice(String plan) {
        switch (plan.toLowerCase()) {
            case "basic": return 6500;
            case "standard": return 12500;
            case "deluxe": return 18500;
            default: return -1;
        }
    }

    public String upgradePlan(String newPlan) {
        if (newPlan.equalsIgnoreCase(plan))
            return "Already on " + plan + " plan.";
        if (!isEligibleForUpgrade)
            return "Not eligible to upgrade.";
        double newPrice = getPlanPrice(newPlan);
        if (newPrice == -1) return "Invalid plan.";
        plan = newPlan;
        price = newPrice;
        isEligibleForUpgrade = false;
        return "Upgraded to " + newPlan + " (Rs " + newPrice + ")";
    }

    public void revertRegularMember(String reason) {
        resetMember();
        plan = "basic";
        price = 6500;
        removalReason = reason;
        isEligibleForUpgrade = false;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + String.format("\nPlan: %s\nPrice: Rs %.0f\nReferral: %s\nRemoval Reason: %s",
                plan, price, referralSource, removalReason.isEmpty() ? "N/A" : removalReason);
    }
}