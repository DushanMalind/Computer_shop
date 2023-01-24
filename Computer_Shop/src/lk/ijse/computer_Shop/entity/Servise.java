package lk.ijse.computer_Shop.entity;

public class Servise {
    private String serviseId;
    private String name;
    private String empId;
    private double price;

    public Servise() {
    }

    public Servise(String serviseId, String name, String empId, double price) {
        this.serviseId = serviseId;
        this.name = name;
        this.empId = empId;
        this.price = price;
    }

    public String getServiseId() {
        return serviseId;
    }

    public void setServiseId(String serviseId) {
        this.serviseId = serviseId;
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
        return "Servise{" +
                "serviseId='" + serviseId + '\'' +
                ", name='" + name + '\'' +
                ", empId='" + empId + '\'' +
                ", price=" + price +
                '}';
    }
}
