import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WestminsterSkinConsultationManager extends JFrame implements SkinConsultationManager{


    private static final int doctorsLimit = 10;                             // max doctors limit

    private List<Doctor> doctors;
    private List<Consultation> consultations;
    private List<Patient> patients;
    private Scanner scanner;

    public WestminsterSkinConsultationManager(){
        doctors = new ArrayList<>();                //created three arraylists which contain the doctors, consultations and the patients.
        consultations = new ArrayList<>();
        patients = new ArrayList<>();
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }           //sets the doctors list

    public List<Consultation> getConsultations() {              //getter for consultations list
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }               //setter for consultations list

    public Consultation getConsultation(String consultationId) {            //getter for consultation
        for (Consultation consultation : consultations) {
            if (consultation.getId().equals(consultationId)) {
                return consultation;
            }
        }
        return null;
    }

    public List<Patient> getPatients() {
        return patients;
    }               //getter for patients

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }           //setter for patients

    public boolean checkFull(){
        return this.doctors.size()==doctorsLimit;
    }           //check if the doctors array is full

    public Doctor getDoctor(String name, String surname) {              //gets the doctor using the arguments name and surname,
        for (Doctor doctor : doctors) {                                  //prints false if the doctor doesn't exist
            if (doctor.getName().equals(name) && doctor.getSurname().equals(surname)) {
                return doctor;
            }
        }
        return null;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }
    @Override
    public void addDoctor(Doctor doctor){                       //add doctor method
        if (this.doctors.size() < doctorsLimit) {
            this.doctors.add(doctor);
            System.out.println("\nAdded Dr." + doctor.getName().charAt(0) + " " + doctor.getSurname() + " Successfully");
        } else {
            System.out.println("Doctors Limit reached!");
        }
    }
    @Override
    public void removeDoctor(String licenseNumber) {                //remove doctor method
        Doctor deleteDoc = null;
        for (Doctor doctor : this.doctors){
            if(doctor.getLicenseNumber().equals(licenseNumber)) {
                deleteDoc = doctor;
                break;
            }
        }

        if (deleteDoc != null){                                                 //removal of doctor with license number
            this.doctors.remove(deleteDoc);
            System.out.println("Removed doctor " + deleteDoc.getName() + " with medical license number '" + licenseNumber + "'!");
            System.out.println("Total number of doctors in list - " + this.doctors.size());

        } else {
            System.out.println("Error, " + licenseNumber + " not found!");
        }
    }
    @Override
    public void printDoctors(){                 //print doctors method
        this.doctors.sort((d1, d2) -> d1.getSurname().compareToIgnoreCase(d2.getSurname()));
        for(Doctor doctor : this.doctors){
            System.out.println("------------------------");
            System.out.println("Doctor " + (this.doctors.indexOf(doctor)+1));
            System.out.println("Name: " + doctor.getName());
            System.out.println("Surname: " + doctor.getSurname());
            System.out.println("Date of birth: " + doctor.getDateOfBirth());
            System.out.println("Mobile number: " + doctor.getMobileNumber());
            System.out.println("Medical license number: " + doctor.getLicenseNumber());
            System.out.println("Specialisation: " + doctor.getSpecialization());

        }
    }

    @Override
    public void saveToFile() {              //save file method
        try (FileWriter fw = new FileWriter("doctors.txt")) {
            for (Doctor doctor : doctors) {
                fw.write(doctor.getName() + "," + doctor.getSurname() + "," + doctor.getDateOfBirth() + "," + doctor.getMobileNumber() + "," + doctor.getLicenseNumber() + "," + doctor.getSpecialization() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile() {                //read file method, using a bufferedReader
        doctors = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("doctors.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String surname = parts[1];
                LocalDate dateOfBirth = LocalDate.parse(parts[2]);
                String mobileNumber = parts[3];
                String medicalLicenseNumber = parts[4];
                String specialisation = parts[5];
                Doctor doctor = new Doctor(name, surname, dateOfBirth, mobileNumber, medicalLicenseNumber, specialisation);
                doctors.add(doctor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GUI(){
        GUI gui = new GUI(doctors, this, consultations);
    }               //gui opener method

    public void addConsultationId(Patient patient){                 //set an ID for the consultation when booked
        for(Consultation consultation : consultations){
            if (consultation.getPatient().equals(patient)){
                int i = consultations.indexOf(consultation);
                String index = Integer.toString(i);
                consultation.setId(index+1);
            }
        }
    }



    public void bookConsultationGUI(){
        BookConsultationGUI bookGUI = new BookConsultationGUI(doctors, this);
    }  //book consultation GUI opener

    @Override                                           //addConsultation method
    public void addConsultation(LocalDateTime dateTime, Doctor doctor, Patient patient) {
        Consultation consultation = new Consultation(dateTime, doctor, patient);
        consultations.add(consultation);
    }

    @Override                                   //checks the availability of the doctor during the specified time
    public boolean checkAvailability(Doctor doctor, LocalDateTime dateTime) {
        for (Consultation consultation : consultations) {
            if (consultation.getDoctor().equals(doctor) && consultation.getDateTime().equals(dateTime)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void addPatientInfo(Patient patient) {
        patients.add(patient);
    }
    @Override
    public void enterAndSaveCost(Consultation consultation) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cost: ");
        double cost = scanner.nextDouble();
        consultation.setCost(cost);
    }

    @Override
    public void addNotes(Consultation consultation) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();
        consultation.setNotes(notes);
    }

    public void addNotes(Patient patient, String notes) throws Exception { //add notes
        for(Consultation consultation : consultations){
            if (consultation.getPatient().equals(patient)){
                consultation.setNotes(notes);
                break;
            }
        }
    }

    public void addImage(Patient patient, String filePath) throws Exception {
        for(Consultation consultation : consultations){
            if(consultation.getPatient().equals(patient)){
                consultation.setImagePath(filePath);
                break;
            }
        }
    }

    public boolean checkPatientFirstTime(Patient patient){
        for(Consultation consultation : consultations){
            if(Objects.equals(consultation.getPatient().getName().toUpperCase(), patient.getName().toUpperCase()) && Objects.equals(consultation.getPatient().getSurname().toUpperCase(), patient.getSurname().toUpperCase()) && Objects.equals(consultation.getPatient().getDateOfBirth(), patient.getDateOfBirth())){
                return false;
            }
        }
        return true;
    }

    public void addCosts(Patient patient, double cost){
        for(Consultation consultation : consultations){
            if(consultation.getPatient().equals(patient)){
                consultation.setCost(cost);
                break;
            }
        }
    }

    public void setIcon(Patient patient, String filePath){
        for(Consultation consultation : consultations){
            if(consultation.getPatient().equals(patient)){
                ImageIcon image = new ImageIcon(filePath);
                consultation.setIcon(image);
                break;
            }
        }

    }

    @Override
    public void sortAlphabetically() {
        doctors.sort(new Comparator<Doctor>() {
            public int compare(Doctor o1, Doctor o2) {
                return o1.getSurname().compareTo(o2.getSurname());
            }
        });
    }


}


