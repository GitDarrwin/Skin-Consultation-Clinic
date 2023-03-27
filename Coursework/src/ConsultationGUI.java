import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ConsultationGUI {

    private WestminsterSkinConsultationManager manager;         //manager created to reference back to the original manager instance

    private List<Consultation> consultations;               //consultations list created to reference back to the original list in the manager class

    private Patient patient;
    public ConsultationGUI(WestminsterSkinConsultationManager manager, List<Consultation> consultations){

        this.manager = manager; //called existing manager
        List<ImageIcon> imageIcons = new ArrayList<>();

        for(Consultation consultation : consultations){
            if (consultation.getIcon() == null){
                continue;
            }
            imageIcons.add(consultation.getIcon());
        }

        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("Check Consultations");
        frame.setSize(1220, 740);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Image icon = new ImageIcon("hospital.png").getImage();
        frame.setIconImage(icon);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {      //closing method for the frame
                manager.GUI();
                frame.dispose();
            }
        });

        JLabel titleLabel = new JLabel("Consultations");
        titleLabel.setFont(new Font("Inter", Font.PLAIN, 18));



        JTable table = new JTable();
        table.setFont(new Font("Inter", Font.PLAIN,12));;
        table.setModel(new ConsultationTableModel(consultations, imageIcons));

        table.setRowHeight(200);

        JPanel northPanel = new JPanel();
        frame.add(northPanel, BorderLayout.NORTH);
        JLabel clinicNameLabel = new JLabel("Westminster Skin Clinic");
        clinicNameLabel.setFont(new Font("Inter", Font.ITALIC,13));;
        JPanel centerPanel = new JPanel();
        frame.add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new GridLayout(10, 1));
        northPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        northPanel.setLayout(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.SOUTH);
        northPanel.add(clinicNameLabel, BorderLayout.WEST);
        centerPanel.add(table, BorderLayout.CENTER);

// create a JScrollPane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

// add the scroll pane to the JFrame
        frame.add(scrollPane);
        JPanel southPanel = new JPanel();
        frame.add(southPanel, BorderLayout.SOUTH);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Inter", Font.PLAIN, 13));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.GUI();
                frame.dispose();
            }
        });

        frame.add(backButton, BorderLayout.SOUTH);
    }
}
