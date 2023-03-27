import java.io.Serializable;
import java.time.LocalDate;

public class Doctor extends Person implements Serializable {

    private String licenseNumber;

    private String specialization;

    public Doctor(String name, String surname, LocalDate dateOfBirth, String mobileNumber, String licenseNumber, String specialization) {
        super(name, surname, dateOfBirth, mobileNumber);
        this.licenseNumber = licenseNumber;
        this.specialization = specialization;
    }

    private String name;
    private String surname;
    private LocalDate dob;

    public Doctor(){
        name = "";
        surname = "";
        dob = LocalDate.now();
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }


}
