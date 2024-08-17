import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Bis.AdmissionModule;
import Bis.PatientRegistrationModule;
import Model.Patient;
import Model.User;
import Utility.LoginSystem;
import Model.medicines;
import Model.OrderDetails;

public class Driver {

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        PatientRegistrationModule patientRegistration = new PatientRegistrationModule("patients.txt");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the Hospital and Pharmacy Integrated Scheme");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Show All Hospitals");
            System.out.println("4. Show All Pharmacies");
            System.out.println("5. Exit");
//            System.out.println("6 . Place an order ");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Role (Hospital/Pharmacy/Patient): ");
                    String role = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

                    loginSystem.register(id, name, role, password, scanner);
                    break;

                case 2:
                    System.out.print("Enter ID: ");
                    String loginId = scanner.nextLine();
                    System.out.print("Enter Role (Hospital/Pharmacy/Patient): ");
                    String loginRole = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String loginPassword = scanner.nextLine();

                    User user = loginSystem.login(loginId, loginRole, loginPassword);
                    if (user != null) {
                        // Additional logic can be added here for different roles
                        switch (loginRole.toLowerCase()) {
                            case "hospital":
                            	System.out.println("Performing Hospital actions...");
                            	hospitalMenu(loginId,patientRegistration);
                                break;
                            case "pharmacy":
                                // Pharmacy-specific actions
                                System.out.println("Performing Pharmacy actions...");
                                pharmacyMenu(loginId ,patientRegistration);
                                break;
                            case "patient":
                                // Patient-specific actions
                                System.out.println("Performing Patient actions...");
                                patientRegistration.getPatientDetails(loginId);
                                Patient patient = patientRegistration.getPatientDetails(loginId);

                                if (patient != null) {
                                    // Use the existing patient instance
                                    patient.patientMenu(patientRegistration);
                                } else {
                                    System.out.println("Patient ID not found.");
                                }
                                break;
                        }
                    }
                    break;

                case 3:
                    loginSystem.showAllHospitals();
                    break;

                case 4:
                    loginSystem.showAllPharmacies();
                    break;

                case 5:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    System.exit(0);
//                case 6:
//                    System.out.print("Enter your patient id: ");
//                    String custid = scanner.nextLine();


//                    if (patient != null) {
//                        System.out.println("Welcome to Order Placement Page");
//                        medicines paracetamol = new medicines("Paracetamol", 10.0);
//                        medicines ibuprofen = new medicines("Ibuprofen", 15.0);
//
//                        OrderDetails order1 = new OrderDetails();
//                        order1.addMedicine(paracetamol);
//                        order1.addMedicine(ibuprofen);
//                        order1.setPatientName(patient.getName());
//
//                        try{order1.saveOrderToFile(new FileWriter("orderdetails.txt"));}
//                        catch (IOException e) {
//                            System.out.println("Error saving order " + e.getMessage());
//                        }
//                        System.out.println("Order has been placed for : "+ patient.getName());
//                    }
//                    else {System.out.println("No Patient found");}

                    break;

                default:
                    System.out.println("Invalid option! Please choose again.");
            }
        }
    }
    public static int generateOrderId() {
        // Logic to generate a unique order ID
        return (int) (System.currentTimeMillis() & 0xfffffff); // Example logic
    }


    private static void pharmacyMenu(String pharmacyId, PatientRegistrationModule patientRegistration) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Pharmacy Management Menu ---");
            System.out.println("1. Create Bill for a Patient");
            System.out.println("2. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter Patient ID for billing:");
                    String custid = scanner.nextLine();
                    Patient patient = patientRegistration.getPatientDetails(custid);
                    if (patient != null) {
                        System.out.println("Patient Found !! ");
                        System.out.println("Patient Name: " + patient.getName());
                        System.out.println("Kindly Confirm : 1 to proceed. 2. To Re-enter");
                        int confirm = scanner.nextInt();
                        scanner.nextLine(); // consume newline

                        if (confirm == 1) {
                            System.out.println("Proceeding...");

                            OrderDetails order = new OrderDetails(generateOrderId());
                            boolean moreItems = true;

                            while (moreItems) {
                                System.out.println("Enter medicine name: ");
                                String medName = scanner.nextLine();

                                System.out.println("Enter medicine price: ");
                                double medPrice = scanner.nextDouble();
                                scanner.nextLine(); // Consume the newline character left in the buffer

                                medicines medicine = new medicines(medName, medPrice);
                                order.addMedicine(medicine);

                                System.out.println("Add more items? (yes/no): ");
                                moreItems = scanner.nextLine().equalsIgnoreCase("yes");
                            }

                            // Set the patient name and save the order
                            order.setPatientName(patient.getName());
                            order.setPatientId(patient.getId());
                            order.saveOrderToFile();

                            System.out.println("Billing completed and saved.");
                        } else {
                            System.out.println("Billing canceled.");
                        }
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 2:
                    System.out.println("Returning to Main Menu...");
                    return;

                default:
                    System.out.println("Invalid option! Please choose again.");
            }
        }
    }


    private static void hospitalMenu(String hospitalId, PatientRegistrationModule patientRegistration) {
        Scanner scanner = new Scanner(System.in);
        AdmissionModule admissionModule = new AdmissionModule("admission.txt");

        while (true) {
            System.out.println("\n--- Hospital Management Menu ---");
            System.out.println("1. Register New Patient");
            System.out.println("2. Admit Patient");
            System.out.println("3. Discharge Patient");
            System.out.println("4. View All Admissions");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    patientRegistration.registerPatient();  // Register a new patient
                    break;

                case 2:
                    System.out.print("Enter Patient TID: ");
                    String tid = scanner.nextLine();
                    if (patientRegistration.isPatientRegistered(tid)) {
                        System.out.print("Enter Department: ");
                        String department = scanner.nextLine();
                        System.out.print("Enter Doctor Name: ");
                        String doctorName = scanner.nextLine();
                        admissionModule.admitPatient(tid, hospitalId, department, doctorName); // Pass hospitalId here
                    } else {
                        System.out.println("Invalid TID! The patient must be registered first.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Patient TID to discharge: ");
                    String dischargeTid = scanner.nextLine();
                    admissionModule.dischargePatient(dischargeTid);
                    break;

                case 4:
                    admissionModule.showAdmissionsByHospital(hospitalId);  // Show admissions for this hospital
                    break;

                case 5:
                    System.out.println("Returning to Main Menu...");
                    return;  // Exit the hospital menu and return to the main menu

                default:
                    System.out.println("Invalid option! Please choose again.");
            }
        }
    }
}