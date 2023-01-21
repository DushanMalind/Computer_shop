package lk.ijse.computer_Shop.model;

public class SupplyerDTO {
    private String SupId;
    private String name;
    private double unitPrice;
    private String model;


    public SupplyerDTO() {
    }

    public SupplyerDTO(String supId, String name, double unitPrice, String model) {
        SupId = supId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.model = model;
    }

    public String getSupId() {
        return SupId;
    }

    public void setSupId(String supId) {
        SupId = supId;
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
        return "SupplyerDTO{" +
                "SupId='" + SupId + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", model='" + model + '\'' +
                '}';
    }
}
