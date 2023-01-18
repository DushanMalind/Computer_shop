package lk.ijse.computer_Shop.entity;

public class Item {
    private String itemId;
    private String description;
    private double unitPrice;
    private int qtyOnhand;

    public Item() {
    }

    public Item(String itemId, String description, double unitPrice, int qtyOnhand) {
        this.itemId = itemId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnhand = qtyOnhand;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQtyOnhand() {
        return qtyOnhand;
    }

    public void setQtyOnhand(int qtyOnhand) {
        this.qtyOnhand = qtyOnhand;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnhand=" + qtyOnhand +
                '}';
    }
}
