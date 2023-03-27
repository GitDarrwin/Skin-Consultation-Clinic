import javax.swing.table.AbstractTableModel;
import java.util.List;

class DoctorTableModel extends AbstractTableModel {
    private List<Doctor> doctors;

    public DoctorTableModel(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    @Override
    public int getRowCount() {
        return doctors.size();
    }

    @Override
    public int getColumnCount() {
        return 4; // the table has 4 columns: name, surname, specialisation, and licence number
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Doctor doctor = doctors.get(rowIndex);
        switch (columnIndex) {
            case 0: return doctor.getName();
            case 1: return doctor.getSurname();
            case 2: return doctor.getSpecialization();
            case 3: return doctor.getLicenseNumber();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Name";
            case 1 -> "Surname";
            case 2 -> "Specialisation";
            case 3 -> "Licence Number";
            default -> null;
        };
    }
}