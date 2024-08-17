package Bis;

import java.util.*;

import Model.Patient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PatientRegistrationModule {

    private String filePath;

    // Constructor to initialize the file path for patient registration
    public PatientRegistrationModule(String filePath) {
        this.filePath = filePath;
    }

    // Method to register a new patient
    public void registerPatient() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();

        
        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        
        System.out.print("Enter Patient Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Patient Contact Number: ");
        String contactNumber = scanner.nextLine();

        // Generate a unique TID for the patient
        String tid = generateTID();

        // Append the patient details to the bis.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(tid + "," + name + "," + age + "," + address + "," + contactNumber);
            writer.newLine();
            System.out.println("Patient registered successfully with TID: " + tid);
        } catch (IOException e) {
            System.out.println("Error registering patient: " + e.getMessage());
        }
    }

    // Method to generate a unique TID for the patient
    private String generateTID() {
        return "TID" + System.currentTimeMillis();  // TID based on current time for uniqueness
    }

    // Method to check if a patient is registered based on TID
    public boolean isPatientRegistered(String tid) {
        // Read bis.txt to check if the TID exists
        try (Scanner fileScanner = new Scanner(new java.io.File(filePath))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] details = line.split(",");
                if (details[0].equals(tid)) {
                    return true; // TID found
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return false; // TID not found
    }
    public Patient getPatientDetails(String custid) {
        try (Scanner fileScanner = new Scanner(new java.io.File("patients.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] details = line.split(";");
                if (details.length > 1 && details[1].trim().equals(custid.trim())){
                    // Ensure there are enough fields and the TID matches
                    String patientName = details[2].trim();
//                    String patientname = details[1];
//                    String patientid = custid;
//                    System.out.println("Patient ID: " + patientid);
                    System.out.println("Patient Name: " + patientName);

                    return new Patient(custid, patientName);
                }

            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return null; // Patient not found
    }
}
