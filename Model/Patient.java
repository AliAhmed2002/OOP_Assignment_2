package Model;

import Bis.PatientRegistrationModule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Patient extends User {

	public String patientid;

	public Patient(String id, String name) {

		super(id, name, "Patient");
		this.patientid = id;
	}

	public Patient(String id, String name, int age, String address) {
		super(id, name, "Patient");
	}


	public String getPatientid() {
		return patientid;
	}

	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}

	@Override
	public void performRoleSpecificOperations() {
		// TODO Auto-generated method stub

	}

	public static void patientMenu(PatientRegistrationModule patientRegistration) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n--- Patient Menu ---");
			System.out.println("1. View Order Bills");
			System.out.println("2. Logout");
			System.out.print("Choose an option: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // consume newline

			switch (choice) {
				case 1:
					System.out.print("Enter your patient ID: ");
					String patientId = scanner.nextLine();
					// Check if the patient exists
//					Patient patient = patientRegistration.getPatientDetails(patientid);
//					showPatientBills(patientId);

//					if (patient != null) {
//						showPatientBills(Patientid);
//					} else {
//						System.out.println("Patient ID not found.");
//					}
					if (patientId != null && !patientId.trim().isEmpty()) {
						// Check if the patient exists
						Patient patient = patientRegistration.getPatientDetails(patientId);

						if (patient != null) {
							showPatientBills(patientId);
						} else {
							System.out.println("Patient ID not found.");
						}
					} else {
						System.out.println("Patient ID cannot be null or empty.");
					}
					break;

				case 2:
					System.out.println("Logging out...");
					return; // Exit the patient menu

				default:
					System.out.println("Invalid option! Please choose again.");
			}
		}
	}

	public static void showPatientBills(String custid) {
		try (Scanner fileScanner = new Scanner(new java.io.File("orderdetails.txt"))) {
			double totalAmount = 0;
			boolean found = false;
			boolean readingMedicines = false;

			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();

				if (line.startsWith("Patient id: " + custid)) {
					if (found) {
						// Handle multiple records by printing a separator or new line
						System.out.println("-----------");
					}
					found = true;
					System.out.println("Patient ID: " + custid);
					System.out.println(fileScanner.nextLine()); // Print patient name
					System.out.println(fileScanner.nextLine()); // Print "Medicines:"
					readingMedicines = true;
				} else if (readingMedicines) {
					if (line.trim().isEmpty()) {
						// End of medicines section
						readingMedicines = false;
					} else if (line.startsWith("Total Bill:")) {
						// Print total bill
						System.out.println(line);
						// Extract and add total amount
						String[] parts = line.split("Rs ");
						if (parts.length > 1) {
							totalAmount = Double.parseDouble(parts[1].trim());
						}
					} else {
						// Handle medicine line
						try {
							String[] parts = line.split(" - Rs ");
							if (parts.length > 1) {
								totalAmount += Double.parseDouble(parts[1].trim());
							} else {
								System.out.println("Invalid format for medicine line: " + line);
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid number format in line: " + line);
						}
					}
				}
			}

			if (found) {
				System.out.println("Total Amount: Rs " + totalAmount);
			} else {
				System.out.println("No bills found for this patient.");
			}
		} catch (IOException e) {
			System.err.println("Error reading order details: " + e.getMessage());
		}
	}
}


