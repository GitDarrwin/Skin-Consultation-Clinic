import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
public class GUI{

    private WestminsterSkinConsultationManager manager;

    List<Consultation> consultations;

    JFrame frame;
    public GUI(List<Doctor> doctors, WestminsterSkinConsultationManager manager, List<Consultation> consultations){
        this.consultations = consultations;             //consultations and manager called from the existing ones in the WestminsterSkinConsultationManager class
        this.manager = manager;
        Image icon = new ImageIcon("hospital.png").getImage();              //window thumbnail

        JButton sortButton = new JButton("Sort");       //sort button
        sortButton.setFont(new Font("Inter", Font.PLAIN, 12));
        frame = new JFrame("Skin Clinic");
        frame.setLayout(new BorderLayout());
        frame.setIconImage(icon);

        JLabel titleLabel = new JLabel("Doctors List");
        titleLabel.setFont(new Font("Inter", Font.PLAIN, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel northPanel = new JPanel();               //created five panels north, south, east, west and center inside the frame
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(northPanel, BorderLayout.NORTH);
        JLabel clinicNameLabel = new JLabel("Westminster Skin Clinic");
        clinicNameLabel.setFont(new Font("Inter", Font.ITALIC,13));
        JLabel nameUser = new JLabel("by Akash Darwin Bowse, w1898952");
        nameUser.setFont(new Font("Inter", Font.PLAIN, 13));
        JPanel centerPanel = new JPanel();
        frame.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridLayout(10, 1));
        JTable table = new JTable();
        table.setFont(new Font("Inter", Font.PLAIN, 12));
        table.setModel(new DoctorTableModel(doctors));   //doctors table model used for the table contents

        sortButton.addActionListener(new ActionListener() {                         //actionListener for the sort button
            @Override
            public void actionPerformed(ActionEvent e) {                            //uses a TableRowSorter with a sortkey that sorts the column with the surname
                TableRowSorter<DoctorTableModel> sorter = new TableRowSorter<>(new DoctorTableModel(doctors));
                table.setRowSorter(sorter);

                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.sort();
            }
        });

        northPanel.setLayout(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.SOUTH);
        northPanel.add(clinicNameLabel, BorderLayout.NORTH);
        northPanel.add(nameUser);
        centerPanel.add(table, BorderLayout.CENTER);

// create a JScrollPane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

// add the scroll pane to the JFrame
        frame.add(scrollPane);
        JPanel southPanel = new JPanel();
        frame.add(southPanel, BorderLayout.SOUTH);
        JButton bookConsultationButton = new JButton("Book Consultation");
        bookConsultationButton.setFont(new Font("Inter", Font.PLAIN, 12));
        bookConsultationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                                //actionperformed for the bookconsultation button
                BookConsultationGUI bookGUI = new BookConsultationGUI(doctors, manager);
                frame.dispose();
            }
        });
        southPanel.add(bookConsultationButton);
        northPanel.add(sortButton, BorderLayout.EAST);

        JButton checkAvailabilityButton = new JButton("Check Consultations");           //checkavailability button
        checkAvailabilityButton.setFont(new Font("Inter", Font.PLAIN, 12));
        checkAvailabilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConsultationGUI consultationGUI = new ConsultationGUI(manager, consultations);
                frame.dispose();
            }
        });

        southPanel.add(checkAvailabilityButton);
        if(doctors.size() == 0){                                //the buttons will not work if the doctors are not present
            bookConsultationButton.setEnabled(false);
            checkAvailabilityButton.setEnabled(false);
        }

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // center the frame on the screen
        frame.setVisible(true);
    }

    public void framesetVisible(boolean x){
        frame.setVisible(x);
    }
}




