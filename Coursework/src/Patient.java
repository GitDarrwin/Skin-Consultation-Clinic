import java.io.Serializable;
import java.time.LocalDate;

public class Patient extends Person implements Serializable {
    private int patientId;
    public Patient(String name, String surname, LocalDate dateOfBirth, String mobileNumber, int patientId){
        super(name, surname, dateOfBirth, mobileNumber);
        this.patientId = patientId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
}
