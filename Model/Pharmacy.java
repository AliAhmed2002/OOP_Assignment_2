package Model;

import java.util.ArrayList;
import java.util.List;

public class Pharmacy extends User {
    private String licenseNumber;
    private String address;
    private String contactNumber;
    private List<Order> orders;
    private List<String> medicineInventory;

    public Pharmacy(String id, String name, String licenseNumber, String address, String contactNumber) {
        super(id, name, "Pharmacy");
        this.licenseNumber = licenseNumber;
        this.address = address;
        this.contactNumber = contactNumber;
        this.orders = new ArrayList<>();
        this.medicineInventory = new ArrayList<>();
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void addMedicine(String medicine) {
        medicineInventory.add(medicine);
        System.out.println(medicine + " added to inventory.");
    }

    public void confirmOrder(Order order) {
        if (orders.contains(order)) {
            order.setStatus("Confirmed");
            System.out.println("Order " + order.getTid() + " confirmed.");
        }
    }

    public void showOrders() {
        System.out.println("Orders for " + getName() + ":");
        for (Order order : orders) {
            System.out.println("Order TID: " + order.getTid() + ", Status: " + order.getStatus());
        }
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void performRoleSpecificOperations() {
        // Implement pharmacy-specific operations here
    }

	@Override
	public String toString() {
		return "Pharmacy [ID=" +getId() +", Name=" + getName() +",licenseNumber=" + licenseNumber + ", address=" + address + ", contactNumber=" + contactNumber
				+ ", orders=" + orders + ", medicineInventory=" + medicineInventory + "]";
	}
    
    
}
