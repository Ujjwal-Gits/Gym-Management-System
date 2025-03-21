public abstract class GymMember {
    protected int id;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected String DOB;
    protected String membershipStartDate;
    protected int attendance;
    protected double loyaltyPoints;
    protected boolean activeStatus;

    public GymMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.membershipStartDate = membershipStartDate;
        this.attendance = 0;
        this.loyaltyPoints = 0;
        this.activeStatus = false;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public String getDOB() { return DOB; }
    public String getMembershipStartDate() { return membershipStartDate; }
    public int getAttendance() { return attendance; }
    public double getLoyaltyPoints() { return loyaltyPoints; }
    public boolean isActiveStatus() { return activeStatus; }

    public abstract void markAttendance();

    public void activateMembership() { activeStatus = true; }
    public void deactivateMembership() { if (activeStatus) activeStatus = false; }
    public void resetMember() { activeStatus = false; attendance = 0; loyaltyPoints = 0; }

    public String getDetails() {
        return String.format("ID: %d\nName: %s\nLocation: %s\nPhone: %s\nEmail: %s\nGender: %s\nDOB: %s\nMembership Start: %s\nAttendance: %d\nLoyalty Points: %.1f\nActive: %b",
                id, name, location, phone, email, gender, DOB, membershipStartDate, attendance, loyaltyPoints, activeStatus);
    }
}