import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

class ConsultationTableModel extends AbstractTableModel {
    private List<Consultation> consultations;

    private List<ImageIcon> imageIcons;

    public ConsultationTableModel(List<Consultation> consultations, List<ImageIcon> imageIcons) {
        this.consultations = consultations;
        this.imageIcons = imageIcons;

        for (ImageIcon icon : imageIcons) {
            if (icon.getImage() == null){
                continue;
            }
            else{
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(309 , 248, Image.SCALE_SMOOTH);
                icon.setImage(scaledImg);
            }

        }

    }

    JButton button = new JButton();

    @Override
    public int getRowCount() {
        return consultations.size();
    }

    @Override
    public int getColumnCount() {
        return 7; // the table has 7 columns: id, date, doctor name, patient name, id, notes and image
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Consultation consultation = consultations.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> consultation.getId();
            case 1 -> consultation.getDateTime().getDayOfMonth() + "/" + consultation.getDateTime().getMonth().getValue() + "/" + consultation.getDateTime().getYear() + " " + consultation.getDateTime().getHour() + ":" + consultation.getDateTime().getMinute();
            case 2 -> consultation.getDoctor().getName() + ' ' + consultation.getDoctor().getSurname();
            case 3 -> consultation.getPatient().getName() + ' ' + consultation.getPatient().getSurname();
            case 4 -> consultation.getPatient().getPatientId();
            case 5 -> {
                try {
                    yield consultation.getNotes();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case 6 -> imageIcons.get(rowIndex);
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Consultation ID";
            case 1 -> "Date/Time";
            case 2 -> "Doctor";
            case 3 -> "Patient";
            case 4 -> "Patient ID";
            case 5 -> "Notes";
            case 6 -> "Image";
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (column == 6) return ImageIcon.class;
        else return String.class;
    }

}
