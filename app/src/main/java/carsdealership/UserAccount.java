package carsdealership;

import org.mindrot.jbcrypt.BCrypt;

class UserAccount {
    private String id;
    private String userName;
    private String passwordHash;
    private String firstName;
    private String lastName;

    UserAccount(String id, String userName, String password, String firstName, String lastName) {
        this.id = id;
        this.setUserName(userName);
        this.setPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getId() {
        return this.id;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public boolean validatePassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }
}

class AdminAccount extends UserAccount {
    private String branch;
    private double salary;
    private int officeNumber;

    AdminAccount(String id, String userName, String password, String firstName, String lastName, String branch, double salary,
            int officeNumber) {
        super(id, userName, password, firstName, lastName);

        setBranch(branch);
        setSalary(salary);
        setOfficeNumber(officeNumber);

    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setOfficeNumber(int officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getBranch() {
        return this.branch;
    }

    public double getSalary() {
        return this.salary;
    }

    public int getOfficeNumber() {
        return this.officeNumber;
    }
}

class SalesManAccount extends UserAccount {
    private String branch;
    private double salary;

    SalesManAccount(String id, String userName, String password, String firstName, String lastName, String branch, double salary) {
        super(id, userName, password, firstName, lastName);

        this.branch = branch;
        this.salary = salary;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getBranch() {
        return this.branch;
    }

    public double getSalary() {
        return this.salary;
    }
}

class CostomerAccount extends UserAccount {
    private String phoneNumber;
    private String emailAddress;
    private int loyalityPoints;

    CostomerAccount(String id, String userName, String password, String firstName, String lastName, String phoneNumber,
            String emailAddress) {
        super(id, userName, password, firstName, lastName);

        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.loyalityPoints = 0;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public int getLoyalityPoints() {
        return loyalityPoints;
    }

    public void addLoyalityPoints(int points) {
        this.loyalityPoints += points;
    }
}
