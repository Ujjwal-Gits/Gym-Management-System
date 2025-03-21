public class PremiumMember extends GymMember {
    private final double premiumCharge = 50000;
    private String personalTrainer;
    private boolean isFullPayment;
    private double paidAmount;
    private double discountAmount;

    public PremiumMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String personalTrainer) {
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.personalTrainer = personalTrainer;
        this.paidAmount = 0;
        this.isFullPayment = false;
        this.discountAmount = 0;
    }

    @Override
    public void markAttendance() {
        if (activeStatus) {
            attendance++;
            loyaltyPoints += 5;
        }
    }

    public String payDueAmount(double amount) {
        if (isFullPayment) return "Payment already completed.";
        paidAmount += amount;
        if (paidAmount >= premiumCharge) {
            isFullPayment = true;
            paidAmount = premiumCharge;
        }
        return String.format("Paid: Rs %.0f | Remaining: Rs %.0f", paidAmount, premiumCharge - paidAmount);
    }

    public void calculateDiscount() {
        discountAmount = isFullPayment ? premiumCharge * 0.10 : 0;
    }

    public void revertPremiumMember() {
        resetMember();
        personalTrainer = "";
        paidAmount = 0;
        isFullPayment = false;
        discountAmount = 0;
    }


    @Override
    public String getDetails() {
        return super.getDetails() + String.format("\nTrainer: %s\nPaid: Rs %.0f\nRemaining: Rs %.0f\nDiscount: Rs %.0f",
                personalTrainer, paidAmount, premiumCharge - paidAmount, discountAmount);
    }
}