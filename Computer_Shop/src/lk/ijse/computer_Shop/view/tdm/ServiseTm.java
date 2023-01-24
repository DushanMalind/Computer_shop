package lk.ijse.computer_Shop.view.tdm;

public class ServiseTm {
    private String servId;
    private String name;
    private String empId;
    private double price;

    public ServiseTm() {
    }

    public ServiseTm(String servId, String name, String empId, double price) {
        this.servId = servId;
        this.name = name;
        this.empId = empId;
        this.price = price;
    }

    public String getServId() {
        return servId;
    }

    public void setServId(String servId) {
        this.servId = servId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ServiseTm{" +
                "servId='" + servId + '\'' +
                ", name='" + name + '\'' +
                ", empId='" + empId + '\'' +
                ", price=" + price +
                '}';
    }
}
