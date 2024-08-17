package Utility;

import java.io.*;
import java.util.*;

import Model.Hospital;
import Model.Patient;
import Model.Pharmacy;
import Model.User;

import java.io.*;
import java.util.*;

public class LoginSystem {
    private Map<String, User> hospitals;
    private Map<String, User> pharmacies;
    private Map<String, User> patients;
    private Map<String, String> passwords;

    private static final String HOSPITAL_FILE = "hospitals.txt";
    private static final String PHARMACY_FILE = "pharmacies.txt";
    private static final String PATIENT_FILE = "patients.txt";
    private static final String PASSWORD_FILE = "passwords.txt";
    private static final String ADMISSION_FILE = "admission.txt";

    public LoginSystem() {
        this.hospitals = new HashMap<>();
        this.pharmacies = new HashMap<>();
        this.patients = new HashMap<>();
        this.passwords = new HashMap<>();
        loadUsersFromFile();
    }

    public void register(String id, String name, String role, String password, Scanner scanner) {
        if (hospitals.containsKey(id) || pharmacies.containsKey(id) || patients.containsKey(id)) {
            System.out.println("User with this ID already exists!");
            return;
        }

        User user;
        switch (role.toLowerCase()) {
            case "hospital":
                System.out.print("Enter Department: ");
                String department = scanner.nextLine();
                System.out.print("Enter Number of Beds: ");
                int numberOfBeds = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Enter Specializations (comma-separated): ");
                String specializationsInput = scanner.nextLine();
                List<String> specializations = Arrays.asList(specializationsInput.split(","));
                user = new Hospital(id, name, department, numberOfBeds, specializations);
                hospitals.put(id, user);
                saveUsersToFile(HOSPITAL_FILE, hospitals);
                break;

            case "pharmacy":
                System.out.print("Enter License Number: ");
                String licenseNumber = scanner.nextLine();
                System.out.print("Enter Address: ");
                String address = scanner.nextLine();
                System.out.print("Enter Contact Number: ");
                String contactNumber = scanner.nextLine();
                user = new Pharmacy(id, name, licenseNumber, address, contactNumber);
                pharmacies.put(id, user);
                saveUsersToFile(PHARMACY_FILE, pharmacies);
                break;

            case "patient":
                user = new Patient(id, name);
                patients.put(id, user);
                saveUsersToFile(PATIENT_FILE, patients);
                break;

            default:
                System.out.println("Invalid role! Please choose from Hospital, Pharmacy, or Patient.");
                return;
        }

        passwords.put(id, password);
        savePasswordsToFile();
        System.out.println("Registration successful for " + role + ": " + name);
    }

    public User login(String id, String role, String password) {
        User user = null;
        switch (role.toLowerCase()) {
            case "hospital":
                user = hospitals.get(id);
                break;
            case "pharmacy":
                user = pharmacies.get(id);
                break;
            case "patient":
                user = patients.get(id);
                break;
            default:
                System.out.println("Invalid role! Please choose from Hospital, Pharmacy, or Patient.");
                return null;
        }

        if (user == null) {
            System.out.println("User not found! Please register first.");
            return null;
        } else if (!passwords.get(id).equals(password)) {
            System.out.println("Incorrect password! Please try again.");
            return null;
        } else {
            System.out.println("Login successful! Welcome, " + user.getName() + " (" + role + ")");
            return user;
        }
    }

    public void showAllHospitals() {
        if (hospitals.isEmpty()) {
            System.out.println("No hospitals registered.");
        } else {
            System.out.println("List of Hospitals:");
            for (User user : hospitals.values()) {
                System.out.println(user);
            }
        }
    }

    public void showAllPharmacies() {
        if (pharmacies.isEmpty()) {
            System.out.println("No pharmacies registered.");
        } else {
            System.out.println("List of Pharmacies:");
            for (User user : pharmacies.values()) {
                System.out.println(user);
            }
        }
    }

    private void saveUsersToFile(String fileName, Map<String, User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : users.values()) {
                writer.write(userToString(user));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    private void savePasswordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PASSWORD_FILE))) {
            for (Map.Entry<String, String> entry : passwords.entrySet()) {
                writer.write(entry.getKey() + ";" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving passwords to file: " + e.getMessage());
        }
    }

    private void loadUsersFromFile() {
        loadUsersFromSpecificFile(HOSPITAL_FILE, hospitals);
        loadUsersFromSpecificFile(PHARMACY_FILE, pharmacies);
        loadUsersFromSpecificFile(PATIENT_FILE, patients);
        loadPasswordsFromFile();
    }

    private void loadUsersFromSpecificFile(String fileName, Map<String, User> usersMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = stringToUser(line);
                if (user != null) {
                    usersMap.put(user.getId(), user);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found for " + fileName + ", starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
        }
    }

    private void loadPasswordsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PASSWORD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    passwords.put(parts[0], parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing password data found, starting fresh.");
        } catch (IOException e) {
            System.out.println("Error loading passwords from file: " + e.getMessage());
        }
    }

    private String userToString(User user) {
        if (user instanceof Hospital) {
            Hospital hospital = (Hospital) user;
            return String.format("Hospital;%s;%s;%s;%d;%s", hospital.getId(), hospital.getName(),
                    hospital.getDepartment(), hospital.getNumberOfBeds(), String.join(",", hospital.getSpecializations()));
        } else if (user instanceof Pharmacy) {
            Pharmacy pharmacy = (Pharmacy) user;
            return String.format("Pharmacy;%s;%s;%s;%s;%s", pharmacy.getId(), pharmacy.getName(),
                    pharmacy.getLicenseNumber(), pharmacy.getAddress(), pharmacy.getContactNumber());
        } else if (user instanceof Patient) {
            Patient patient = (Patient) user;
            return String.format("Patient;%s;%s", patient.getId(), patient.getName());
        }
        return null;
    }

    private User stringToUser(String data) {
        String[] parts = data.split(";");
        String role = parts[0];
        String id = parts[1];
        String name = parts[2];

        switch (role) {
            case "Hospital":
                String department = parts[3];
                int numberOfBeds = Integer.parseInt(parts[4]);
                List<String> specializations = Arrays.asList(parts[5].split(","));
                return new Hospital(id, name, department, numberOfBeds, specializations);
            case "Pharmacy":
                String licenseNumber = parts[3];
                String address = parts[4];
                String contactNumber = parts[5];
                return new Pharmacy(id, name, licenseNumber, address, contactNumber);
            case "Patient":
                return new Patient(id, name);
            default:
                return null;
        }
    }
}
