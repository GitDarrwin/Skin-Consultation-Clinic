import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class BookConsultationGUI extends JFrame {

    private JTextField doctorNameField;
    private JTextField doctorSurnameField;
    private JTextField patientNameField;
    private JTextField patientSurnameField;
    private JTextField patientDOBField;
    private JTextField patientMobileField;
    private JTextField patientIDField;
    private JTextField dateTimeField;

    private WestminsterSkinConsultationManager manager;
    private Doctor doctor;

    public BookConsultationGUI(List<Doctor> doctors, WestminsterSkinConsultationManager manager) {
        Font newfont = new Font("Inter", Font.PLAIN,13);

        JFrame frame = new JFrame();                    //frame created
        this.manager = manager;
        JTextField[] textFields = new JTextField[5];            //array created to validate if all the text-field are filled.
        frame.setTitle("Book Consultation");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {           //code executed when the frame is closed
            public void windowClosing(WindowEvent e) {
                manager.GUI();
                frame.dispose();
            }
        });

        JLabel title = new JLabel("  Book Consultation");                     //JLabels title created
        title.setFont(new Font("Inter", Font.PLAIN, 20));

        JPanel panel = new JPanel();                            //panel which holds the patient info fields
        panel.setLayout(new GridLayout(11, 2));


        JButton bookButton = new JButton("Book Consultation");      //book button
        bookButton.setFont(newfont);
        bookButton.setEnabled(false);


        JLabel labelDName = new JLabel("Doctor Name:");     //label names for each of the fields
        labelDName.setFont(newfont);
        panel.add(labelDName);
        JComboBox<Doctor> doctorComboBox = new JComboBox<>(new DefaultComboBoxModel<>(doctors.toArray(new Doctor[0])));
        doctorComboBox.setRenderer(new ListCellRenderer<Doctor>() {             //comboBox created to store all the doctors and use a drop down menu to access them
            @Override
            public Component getListCellRendererComponent(JList<? extends Doctor> list, Doctor doctor, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = new JLabel(doctor.getName() + " " + doctor.getSurname());
                if (isSelected) {
                    label.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                } else {
                    label.setBackground(list.getBackground());
                    label.setForeground(list.getForeground());
                }
                return label;
            }
        });
        doctorComboBox.setFont(newfont);
        panel.add(doctorComboBox);

        JLabel labelDateAndTime = new JLabel("Date and Time (YYYY-MM-DD HH:MM):");          //label for fields
        labelDateAndTime.setFont(newfont);
        panel.add(labelDateAndTime);
        dateTimeField = new JTextField();
        dateTimeField.setFont(newfont);

        panel.add(dateTimeField);

        panel.add(new JLabel(""));
        JButton availabilityButton = new JButton("Check Availability");
        availabilityButton.setFont(newfont);
        panel.add(availabilityButton);

        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        JLabel labelPatientName = new JLabel("Patient Name:");          //label for fields
        labelPatientName.setFont(newfont);
        panel.add(labelPatientName);
        patientNameField = new JTextField();
        patientNameField.setFont(newfont);
        patientNameField.addFocusListener(new FocusListener() {             //focus listener added to check if all the fields are filled when focus lost and enable the book button
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                boolean allFieldsFilled = true;
                for (JTextField textField : textFields) {
                    if (textField.getText().isEmpty()) {
                        allFieldsFilled = false;
                        break;
                    }
                }
                bookButton.setEnabled(allFieldsFilled);
            }
        });
        textFields[0] = patientNameField;
        panel.add(patientNameField);

        JLabel labelPatientSurname = new JLabel("Patient Surname:");
        labelPatientSurname.setFont(newfont);
        panel.add(labelPatientSurname);
        patientSurnameField = new JTextField();
        patientSurnameField.setFont(newfont);
        patientSurnameField.addFocusListener(new FocusListener() {             //focus listener added to check if all the fields are filled when focus lost and enable the book button
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                boolean allFieldsFilled = true;
                for (JTextField textField : textFields) {
                    if (textField.getText().isEmpty()) {
                        allFieldsFilled = false;
                        break;
                    }
                }
                bookButton.setEnabled(allFieldsFilled);
            }
        });
        textFields[1] = patientSurnameField;
        panel.add(patientSurnameField);
        JLabel labelPatientDOB = new JLabel("Patient DOB (YYYY-MM-DD):");
        labelPatientDOB.setFont(newfont);
        panel.add(labelPatientDOB);
        patientDOBField = new JTextField();
        patientDOBField.setFont(newfont);
        textFields[2] = patientDOBField;

        patientDOBField.addFocusListener(new FocusListener() {             //focus listener added to check if all the fields are filled when focus lost and enable the book button
            @Override
            public void focusGained(FocusEvent e) {                        //also used to validate the right formatting for the date-time

            }

            @Override
            public void focusLost(FocusEvent e) {
                boolean allFieldsFilled = true;
                for (JTextField textField : textFields) {
                    if (textField.getText().isEmpty()) {
                        allFieldsFilled = false;
                        break;
                    }
                }
                bookButton.setEnabled(allFieldsFilled);
                if(Objects.equals(patientDOBField.getText(), "")){
                    return;
                }
                try{
                    //remember to use all these variables to send to the list later
                    String dateString = patientDOBField.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate dateOfBirth = LocalDate.parse(dateString, formatter);
                }
                catch(DateTimeParseException ex){
                    JOptionPane.showMessageDialog(null, "Incorrect Format!");
                }
            }
        });
        Image icon = new ImageIcon("hospital.png").getImage();
        frame.setIconImage(icon);
        panel.add(patientDOBField);
        JLabel labelPatientMobile = new JLabel("Patient Mobile:");
        labelPatientMobile.setFont(newfont);
        panel.add(labelPatientMobile);
        patientMobileField = new JTextField();
        patientMobileField.setFont(newfont);
        textFields[3] = patientMobileField;

        patientMobileField.addFocusListener(new FocusListener() {             //focus listener added to check if all the fields are filled when focus lost and enable the book button
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                boolean allFieldsFilled = true;
                for (JTextField textField : textFields) {
                    if (textField.getText().isEmpty()) {
                        allFieldsFilled = false;
                        break;
                    }
                }
                bookButton.setEnabled(allFieldsFilled);

                if(Objects.equals(patientMobileField.getText(), "")){
                    return;
                }
                String mobileNumber = patientMobileField.getText();
                if(mobileNumber.matches("[0-9]+")){
                    boolean allowed = true;
                    return;
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error! Enter only numbers");                        //also used to validate the right formatting for the mobile number
                }
            }
        });
        panel.add(patientMobileField);
        JLabel labelPatientID = new JLabel("Patient ID:");
        labelPatientID.setFont(newfont);
        panel.add(labelPatientID);

        patientIDField = new JTextField();
        patientIDField.setFont(newfont);
        patientIDField.addFocusListener(new FocusListener() {             //focus listener added to check if all the fields are filled when focus lost and enable the book button
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                boolean allFieldsFilled = true;
                for (JTextField textField : textFields) {
                    if (textField.getText().isEmpty()) {
                        allFieldsFilled = false;
                        break;
                    }
                }
                bookButton.setEnabled(allFieldsFilled);
            }
        });

        textFields[4] = patientIDField;
        panel.add(patientIDField);
        final String[] fileArray = {"sain"};
        final boolean[] imageUploaded = {false};
        JButton uploadButton = new JButton("Upload");
        uploadButton.setFont(newfont);
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            //action performed when upload button is clicked, uses the JFileChooser to open a filechooser window
                String filePath;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png", "gif");
                fileChooser.addChoosableFileFilter(filter);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePath = selectedFile.getAbsolutePath();
                    fileArray[0] = filePath;
                    imageUploaded[0] = true;
                    // You can use the filePath to load the image and display it in the GUI
                }
            }
        });

        JLabel labelUploadNotes = new JLabel("Upload notes (Optional): ");              //label for uploading notes
        labelUploadNotes.setFont(newfont);
        panel.add(labelUploadNotes);
        JTextField patientNotes = new JTextField();
        patientNotes.setFont(newfont);
        panel.add(patientNotes);

        JLabel labelAddImage = new JLabel("Add image (Optional): ");            //label for uploading images
        labelAddImage.setFont(newfont);
        panel.add(labelAddImage);
        panel.add(uploadButton);

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    for (JTextField s : textFields) {
                        if (s.getText().isEmpty() || dateTimeField.getText().isEmpty()) {               //method to check if the fields are filled
                            JOptionPane.showMessageDialog(frame, "Fill in all the required fields!");
                            return;
                        }
                    }
                    String dateString = patientDOBField.getText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate patientDate = LocalDate.parse(dateString, formatter);

                    Doctor selectedDoctor = (Doctor) doctorComboBox.getSelectedItem();

                    assert selectedDoctor != null;
                    String doctorFirstName = selectedDoctor.getName();
                    String doctorLastName = selectedDoctor.getSurname();

                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String dateTimeString = dateTimeField.getText();
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter2);
                    Doctor doctor = manager.getDoctor(doctorFirstName, doctorLastName);


                    if (!(manager.checkAvailability(doctor, dateTime))) {               //checks if the doctor is available
                        JOptionPane.showMessageDialog(null, "Booking already exists at this time!");
                        int result = JOptionPane.showOptionDialog(null, "Do you want to close the window?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                        if (result == JOptionPane.YES_OPTION) {
                            // user clicked "Yes", so close the window
                            frame.dispose();
                            manager.GUI();
                        }

                        return;
                    } else {           //checks if the patient has registered already, adds $15 if its registered first, or $25 otherwise

                        Patient patient = new Patient(patientNameField.getText(), patientSurnameField.getText(), patientDate, patientMobileField.getText(), Integer.parseInt(patientIDField.getText()));
                        if (manager.checkPatientFirstTime(patient)) {
                            int result = JOptionPane.showOptionDialog(frame, "First time, $15, ready to proceed?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                            if (result == JOptionPane.YES_OPTION) {
                                manager.addConsultation(dateTime, doctor, patient);
                                manager.addConsultationId(patient);
                                if (imageUploaded[0]) {             //checks if the images field is filled
                                    manager.addImage(patient, fileArray[0]);
                                    manager.setIcon(patient, fileArray[0]);
                                }
                                if (!(patientNotes.getText().isEmpty())) {          //checks if the notes field is filled

                                    manager.addNotes(patient, patientNotes.getText());
                                } else if (patientNotes.getText().isEmpty()) {
                                    manager.addNotes(patient, "None");
                                }
                                manager.addCosts(patient, 15);
                                JOptionPane.showMessageDialog(null, "Consultation added successfully!");
                                frame.dispose();
                                manager.GUI();
                            }
                        } else {
                            int result = JOptionPane.showOptionDialog(frame, "$25, ready to proceed?", "Close Window?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                            if (result == JOptionPane.YES_OPTION) {
                                manager.addConsultation(dateTime, doctor, patient);
                                manager.addConsultationId(patient);
                                if (imageUploaded[0]) {
                                    manager.addImage(patient, fileArray[0]);
                                    manager.setIcon(patient, fileArray[0]);
                                }
                                if (!(patientNotes.getText().isEmpty())) {
                                    manager.addNotes(patient, patientNotes.getText());
                                }
                                manager.addCosts(patient, 25);
                                JOptionPane.showMessageDialog(null, "Consultation added successfully!");
                                frame.dispose();
                                manager.GUI();
                            }


                        }



                    }
                }
                catch(Exception ee){
                    JOptionPane.showMessageDialog(frame, "Error!, please re-check fields.");            //error handling
                }
        }

        });

        availabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Doctor selectedDoctor = (Doctor) doctorComboBox.getSelectedItem();
                assert selectedDoctor != null;
                String doctorFirstName = selectedDoctor.getName();
                String doctorLastName = selectedDoctor.getSurname();
                try {
                    if (dateTimeField.getText().isEmpty()) {                //checks if datetimefield is empty
                        JOptionPane.showMessageDialog(null, "Enter a date and time!");
                        return;
                    }

                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String dateTimeString = dateTimeField.getText();
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter2);
                    Doctor doctor = manager.getDoctor(doctorFirstName, doctorLastName);

                    if (!(manager.checkAvailability(doctor, dateTime))) {           //checks if the doctor is available at selected date and time
                        JOptionPane.showMessageDialog(frame, "Doctor not available at this date and time!");
                        List<Doctor> availableDoctors = new ArrayList<>();
                        for (Doctor d : manager.getDoctors()) {
                            if (manager.checkAvailability(d, dateTime)) {
                                availableDoctors.add(d);
                            }
                        }
                        if (availableDoctors.isEmpty()) {
                            JOptionPane.showMessageDialog(frame,"Sorry! No other available doctors at this time.");
                            return;
                        }
                        Random random = new Random();               //random doctor allocated from the available doctors
                        int index = random.nextInt(availableDoctors.size());
                        doctor = availableDoctors.get(index);
                        JOptionPane.showMessageDialog(frame,"Doctor allocated: " + doctor.getName() + " " + doctor.getSurname());

                        for(Doctor d : doctors){
                            if(d == doctor){
                                index = doctors.indexOf(d);
                                doctorComboBox.setSelectedIndex(index);
                                break;
                            }
                        }

                    } else {
                        JOptionPane.showMessageDialog(frame, "Doctor available!");
                    }
                } catch (Exception ee) {
                    JOptionPane.showMessageDialog(frame, "Incorrect format!, try again");
                }
            }
});
        panel.setFont(newfont);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));               //add borders

        frame.add(title, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);


        frame.add(bookButton, BorderLayout.SOUTH);



        frame.setVisible(true);
    }
}