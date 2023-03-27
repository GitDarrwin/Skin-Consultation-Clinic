import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws Exception {


        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();      //create an instance of the skin consultation manager
        Scanner scanner = new Scanner(System.in);
        File file = new File("doctors.txt");            //check if the file "doctors.txt" exists, and read from if it exists
        if (file.exists()){
            manager.readFromFile();
            System.out.println("Read from file!");
        }
        while (true) {                  //main menu
            System.out.print("""
                    
                   ---------- Welcome to Westminster Skin Consultation Menu! ----------
                    
                                1. Add doctor
                                2. Remove doctor
                                3. Print list of Doctors
                                4. Save to file
                                5. Read from file                             \s
                                6. Display all consultations
                                7. Open GUI
                                8. Exit
                 
                                
                          Enter -\s""");

            int choice = scanner.nextInt();

            switch (choice) {               //choice 1
                case 1 -> {
                    if (manager.checkFull()){
                        System.out.println("\nDoctors list full!");
                        break;
                    }
                    System.out.println("\n---------------Add doctor---------------");
                    System.out.print("Enter doctor's first name: ");
                    String name = scanner.next();
                    System.out.print("Enter doctor's surname: ");
                    String surname = scanner.next();
                    String dateString;
                    LocalDate dateOfBirth = null;
                    boolean exceptionThrown = true;     //error handling
                    while(exceptionThrown){
                        try{
                            System.out.print("Enter doctor's date of birth (YYYY-MM-DD): ");
                            dateString = scanner.next();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");                //formats the string into datetime
                            dateOfBirth = LocalDate.parse(dateString, formatter);
                            exceptionThrown = false;
                        }
                        catch(DateTimeParseException e){
                            System.out.println("Incorrect format!, try again");
                        }
                    }
                    String mobileNumber = null;
                    boolean v = true;
                    while(v){
                        System.out.print("Enter doctor's mobile number: ");
                        mobileNumber = scanner.next();
                        if(mobileNumber.matches("[0-9]+")){         //check if mobile number is all numerical regex
                            v = false;
                            break;
                        }
                        else{
                            System.out.println("Error! Enter only numbers ");
                        }
                    }

                    System.out.print("Enter doctor's medical license number: ");
                    String licenseNumber = scanner.next();
                    System.out.print("Enter doctor's specialization: ");
                    String specialization = scanner.next();
                    Doctor doctor = new Doctor(name, surname, dateOfBirth, mobileNumber, licenseNumber, specialization);
                    manager.addDoctor(doctor);
                }
                case 2 -> {
                    manager.printDoctors();                 //print all the doctors list
                    System.out.print("\nEnter doctor's medical license number: ");
                    String licenseNumber = scanner.next();
                    manager.removeDoctor(licenseNumber);
                }
                case 3 -> manager.printDoctors();
                case 4 -> {
                    manager.saveToFile();           //save to file
                    System.out.println("Saved to file 'doctors.txt'!");
                }
                case 5 -> {                 //read from file
                    manager.readFromFile();
                    System.out.println("Read from file 'doctors.txt'!");
                }
                case 6 -> {
                    for(Consultation consultation : manager.getConsultations()){                //print all the details of the consultation
                        System.out.println("\nConsultation " + (manager.getConsultations().indexOf(consultation)+1) + " -----------------------------------");
                        System.out.println("Dr " + consultation.getDoctor().getName().charAt(0) + "." + consultation.getDoctor().getSurname());
                        System.out.print("to Patient " + consultation.getPatient().getName() + " " + consultation.getPatient().getSurname());
                        System.out.println(" on the " + consultation.getDateTime().getDayOfMonth() + "th of " + consultation.getDateTime().getMonth() + " " + consultation.getDateTime().getYear() +
                                " at " + consultation.getDateTime().getHour() + ":" + consultation.getDateTime().getMinute());
                        System.out.println("Cost - $" + consultation.getCost());
                        System.out.println("\n--Patient information--");
                        System.out.println("Patient name - " + consultation.getPatient().getName() + " " + consultation.getPatient().getSurname());
                        System.out.println("Patient ID - " + consultation.getPatient().getPatientId());
                        System.out.println("Patient DOB - " + consultation.getPatient().getDateOfBirth());
                        System.out.println("Patient Mobile Number - " + consultation.getPatient().getMobileNumber());
                    }

                }
                case 7 ->{
                    manager.GUI();
                }
                case 8 -> {
                    System.out.println("Exiting!");
                    System.exit(0);
                }
                case 11 -> {                                    //obsolete code for the manager to execute. Does the same functions in the GUI, like
                                                                //add consultation, add notes, cost, etc.
                    System.out.print("Enter Doctor's first name: ");
                    String name = scanner.next();
                    System.out.print("Enter Doctor's surname: ");
                    String surname = scanner.next();
                    scanner.nextLine();
                    Doctor doctor = manager.getDoctor(name, surname);
                    if (doctor == null) {
                        System.out.println("Doctor not found.");
                        break;
                    }
                    LocalDateTime dateTime = null;
                    boolean exceptionThrown = true;
                    while(exceptionThrown){
                        try{
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            System.out.print("Enter date and time (YYYY-MM-DD HH:MM): ");
                            String dateTimeString = scanner.nextLine();
                            dateTime = LocalDateTime.parse(dateTimeString, formatter);

                            exceptionThrown = false;
                        }
                        catch(DateTimeParseException e){
                            System.out.println("Incorrect format!, try again");
                        }
                    }
                    System.out.print("Enter patient name: ");
                    String patientName = scanner.nextLine();
                    System.out.print("Enter patient surname: ");
                    String patientSurname = scanner.nextLine();
                    LocalDate dateOfBirth = null;
                    String dateString = "";
                    exceptionThrown = true;
                    while(exceptionThrown){
                        try{
                            System.out.print("Enter date of birth (YYYY-MM-DD): ");
                            dateString = scanner.next();
                            scanner.nextLine();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            dateOfBirth = LocalDate.parse(dateString, formatter);
                            exceptionThrown = false;
                        }
                        catch(DateTimeParseException e){
                            System.out.println("Incorrect format!, try again");
                        }
                    }
                    System.out.print("Enter mobile number: ");
                    String mobileNumber = scanner.nextLine();
                    System.out.print("Enter patient id: ");
                    int id = scanner.nextInt();
                    Patient patient = new Patient(patientName, patientSurname, dateOfBirth, mobileNumber, id);

                    manager.addConsultation(dateTime, doctor, patient);
                    manager.addConsultationId(patient);
                    manager.addNotes(patient, "None");
                    System.out.println("Consultation added.");
                    break;
                }
                case 12 -> {
                    System.out.print("Enter doctor name: ");
                    String name = scanner.next();
                    System.out.print("Enter doctor surname: ");
                    String surname = scanner.next();
                    scanner.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    System.out.print("Enter date and time (YYYY-MM-DD HH:MM): ");
                    String dateTimeString = scanner.nextLine();
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
                    Doctor doctor = manager.getDoctor(name, surname);
                    if (doctor == null) {
                        System.out.println("Doctor not found.");
                        break;
                    }
                    if (manager.checkAvailability(doctor, dateTime)) {
                        System.out.println("Doctor is available.");
                    } else {
                        System.out.println("Doctor is not available. Allocating another doctor...");
                        List<Doctor> availableDoctors = new ArrayList<>();
                        for (Doctor d : manager.getDoctors()) {
                            if (manager.checkAvailability(d, dateTime)) {
                                availableDoctors.add(d);
                            }
                        }
                        if (availableDoctors.isEmpty()) {
                            System.out.println("No available doctors at this time.");
                            break;
                        }
                        Random random = new Random();
                        int index = random.nextInt(availableDoctors.size());
                        doctor = availableDoctors.get(index);
                        System.out.println("Doctor allocated: " + doctor.getName() + " " + doctor.getSurname());
                    }
                }
                case 13 -> {
                    System.out.print("Enter patient name: ");
                    String name = scanner.next();
                    System.out.print("Enter patient surname: ");
                    String surname = scanner.next();
                    scanner.nextLine();
                    System.out.print("Enter date of birth (YYYY-MM-DD): ");
                    String dateOfBirthString = scanner.nextLine();
                    LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString);
                    System.out.print("Enter mobile number: ");
                    String mobileNumber = scanner.nextLine();
                    System.out.print("Enter patient id: ");
                    int id = scanner.nextInt();
                    Patient patient = new Patient(name, surname, dateOfBirth, mobileNumber, id);
                    manager.addPatientInfo(patient);
                    System.out.println("Patient added.");
                }
                case 14 -> {
                    System.out.print("Enter consultation id: ");
                    int consultationId = scanner.nextInt();
                    scanner.nextLine();
                    Consultation consultation = manager.getConsultation(String.valueOf(consultationId));
                    if (consultation == null) {
                        System.out.println("Consultation not found.");
                        break;
                    }
                    System.out.print("Enter cost: ");
                    double cost = scanner.nextDouble();
                    consultation.setCost(cost);
                    System.out.println("Cost saved.");
                    break;

                }
                default -> System.out.println("Invalid! Try again.");
            }
        }

    }
}
