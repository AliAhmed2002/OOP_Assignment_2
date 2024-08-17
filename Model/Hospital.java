package Model;

import java.util.ArrayList;
import java.util.List;

public class Hospital extends User {
    private String department;
    private int numberOfBeds;
    private List<String> specializations;
    private List<Patient> patients;

    public Hospital(String id, String name, String department, int numberOfBeds, List<String> specializations) {
        super(id, name, "Hospital");
        this.department = department;
        this.numberOfBeds = numberOfBeds;
        this.specializations = specializations;
        this.patients = new ArrayList<>();
    }

    public String getDepartment() {
        return department;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public List<String> getSpecializations() {
        return specializations;
    }

    public void admitPatient(Patient patient) {
        if (patients.size() < numberOfBeds) {
            patients.add(patient);
            System.out.println("Patient " + patient.getName() + " admitted successfully.");
        } else {
            System.out.println("No available beds to admit the patient.");
        }
    }

    public void showPatients() {
        System.out.println("Patients admitted in " + getName() + " (" + department + "):");
        for (Patient patient : patients) {
            System.out.println(patient.getName());
        }
    }

    @Override
    public void performRoleSpecificOperations() {
        // Implement hospital-specific operations here
    }

	@Override
	public String toString() {
		return "Hospital [ID=" +getId() +", Name=" + getName()+", department=" + department + ", numberOfBeds=" + numberOfBeds + ", specializations="
				+ specializations + ", patients=" + patients + "]";
	}
	
	
    
    
}
