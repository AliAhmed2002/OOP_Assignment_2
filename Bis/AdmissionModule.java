package Bis;

import java.io.*;
import java.util.*;

import Model.Admission;

public class AdmissionModule {

    private String filePath;

    // Constructor to initialize the file path for admissions
    public AdmissionModule(String filePath) {
        this.filePath = filePath;
    }

    // Method to admit a patient
    public void admitPatient(String tid, String hospitalId, String department, String doctorName) {
        // Append the admission details to the admission.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(hospitalId + "," + tid + "," + department + "," + doctorName);
            writer.newLine();
            System.out.println("Patient admitted successfully with TID: " + tid + " under Hospital ID: " + hospitalId);
        } catch (IOException e) {
            System.out.println("Error admitting patient: " + e.getMessage());
        }
    }

    // Method to discharge a patient (remove their record from admission.txt)
    public void dischargePatient(String tid) {
        // Logic to remove the patient from admission.txt
        // You can read the file, filter out the patient by TID, and rewrite the file
        // This is not implemented fully for brevity
        System.out.println("Discharge functionality not fully implemented.");
    }

    // Method to show all admissions for a specific hospital
    public void showAdmissionsByHospital(String hospitalId) {
        try (Scanner fileScanner = new Scanner(new java.io.File(filePath))) {
            boolean found = false;
            System.out.println("\nAdmissions for Hospital ID: " + hospitalId);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] details = line.split(",");
                if (details[0].equals(hospitalId)) {
                    System.out.println("TID: " + details[1] + ", Department: " + details[2] + ", Doctor: " + details[3]);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No admissions found for this hospital.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}