package lk.ijse.computer_Shop.view.tdm;

public class SupplyerTm {
    private String id;
    private String name;
    private double unitPrice;
    private String model;



    public SupplyerTm() {
    }

    public SupplyerTm(String id, String name, double unitPrice, String model) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.model = model;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "SupplyerTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", model='" + model + '\'' +
                '}';
    }
}
