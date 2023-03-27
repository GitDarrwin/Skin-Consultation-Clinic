import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
public class Consultation {

    private static final String SECRET_KEY = "abcdefghijklmnop";                                    //secret key for the encryption
    private static final String INIT_VECTOR = "abcdefghijklmnop";
    private String encryptedNotes;


    private String id;
    private LocalDateTime dateTime;
    private double cost;

    byte[] encryptedNotesBytes;
    private String imagePath = "None";
    private ImageIcon icon = new ImageIcon();

    byte[] encryptedImagePathBytes;

    private String encryptedImagePath;
    private Doctor doctor;
    private Patient patient;

    public Consultation(LocalDateTime dateTime, Doctor doctor, Patient patient){
        this.dateTime = dateTime;
        this.doctor = doctor;
        this.patient = patient;

    }


    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getImagePath() throws Exception{
        IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));                //encryption
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        return new String(cipher.doFinal(Base64.getDecoder().decode(this.encryptedImagePath)));
    }

    public void setImagePath(String filePath) throws Exception{                                     //decryption
        IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        this.encryptedImagePath = Base64.getEncoder().encodeToString(cipher.doFinal(filePath.getBytes()));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNotes() throws Exception {                         //encryption
        IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        return new String(cipher.doFinal(Base64.getDecoder().decode(this.encryptedNotes)));
    }


    public void setNotes(String notes) throws Exception{                //decryption
        IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        this.encryptedNotes = Base64.getEncoder().encodeToString(cipher.doFinal(notes.getBytes()));
    }



    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
