package Model;

public class Order {
    private String tid;
    private Patient patient;
    private String medicine;
    private String status;

    public Order(String tid, Patient patient, String medicine) {
        this.tid = tid;
        this.patient = patient;
        this.medicine = medicine;
        this.status = "Pending";
    }

    public String getTid() {
        return tid;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
