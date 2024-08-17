package Model;

import java.util.Date;

public class Admission {
    private String patientId;
    private String department;
    private String doctorName;
    private Date admissionDate;

    public Admission(String patientId, String department, String doctorName, Date admissionDate) {
        this.patientId = patientId;
        this.department = department;
        this.doctorName = doctorName;
        this.admissionDate = admissionDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDepartment() {
        return department;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Date getAdmissionDate() {
        return admissionDate;
    }

    @Override
    public String toString() {
        return "Admission{" +
                "patientId='" + patientId + '\'' +
                ", department='" + department + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", admissionDate=" + admissionDate +
                '}';
    }

    // Convert Admission details to a CSV format to be stored in a file
    public String toCSV() {
        return "Admission," + patientId + "," + department + "," + doctorName + "," + admissionDate.getTime();
    }
}

