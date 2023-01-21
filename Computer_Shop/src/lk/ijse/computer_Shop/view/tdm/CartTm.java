package lk.ijse.computer_Shop.view.tdm;

import javafx.scene.control.Button;

public class CartTm {
    private String supId;
    private String name;
    private String model;
    private double unitPrice;
    private int qty;
    private double total;
    private Button btn;

    public CartTm() {
    }

    public CartTm(String supId, String name, String model, double unitPrice, int qty, double total, Button btn) {
        this.supId = supId;
        this.name = name;
        this.model = model;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.total = total;
        this.btn = btn;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "CartTm{" +
                "supId='" + supId + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", total=" + total +
                ", btn=" + btn +
                '}';
    }
}
