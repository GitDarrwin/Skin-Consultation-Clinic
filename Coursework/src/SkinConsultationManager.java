import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

public interface SkinConsultationManager {
    void addDoctor(Doctor doctor);

    void setDoctors(List<Doctor> doctors);

    void addCosts(Patient patient, double cost);

    boolean checkPatientFirstTime(Patient patient);
    void setIcon(Patient patient, String filePath);
    void removeDoctor(String licenseNumber);

    void printDoctors();

    void saveToFile();

    void readFromFile();

    void addConsultation(LocalDateTime dateTime, Doctor doctor, Patient patient);

    boolean checkAvailability(Doctor doctor, LocalDateTime dateTime);

    void addPatientInfo(Patient patient);

    void enterAndSaveCost(Consultation consultation);

    void addNotes(Consultation consultation) throws Exception;

    void addNotes(Patient patient, String notes) throws Exception;

    void addImage(Patient patient, String filePath) throws Exception;


    void sortAlphabetically();
}
