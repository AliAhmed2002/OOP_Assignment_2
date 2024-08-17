package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetails {
    private int orderId;
    private List<medicines> allmedicines;
    private String patientName;
    public String Orderfilename = "OrderDetails.txt";
    public String patientId;

    public OrderDetails(int orderId) {
        this.orderId = orderId;

        this.allmedicines = new ArrayList<>();
    }

    public int getOrderId() {
        return orderId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<medicines> getAllmedicines() {
        return allmedicines;
    }

    public void setAllmedicines(List<medicines> allmedicines) {
        this.allmedicines = allmedicines;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void addMedicine(medicines medicine) {
        allmedicines.add(medicine);
    }

    public double calculateTotalBill() {
        double total = 0;
        for (medicines med : allmedicines) {
            total += med.getPrice();
        }
        return total;
    }


    public void saveOrderToFile() {
        System.out.println("Saving order with ID: " + orderId);
        try (PrintWriter writer = new PrintWriter(new FileWriter(Orderfilename,true))) {
            writer.println("Order ID: " + orderId);
            writer.println("Patient id: " + patientId); // Ensure you have patientId available
            writer.println("Patient Name: " + patientName);
            writer.println("Medicines:");

            double totalAmount = 0;
            for (medicines med : allmedicines) {
                writer.println(med.getName() + " - Rs " + med.getPrice());
                totalAmount += med.getPrice();
            }

            writer.println(); // Blank line for readability
            writer.println("Total Bill: Rs " + totalAmount); // Print total amount
        } catch (IOException e) {
            System.out.println("File Not Found"+e.getMessage());
        }
    }

}
