package lk.ijse.computer_Shop.view.tdm;

public class StockDetailsTm {
    private String stockId;
    private String supId;
    private int qty;
    private String description;
    private double unitPrice;
    private double total;

    public StockDetailsTm() {
    }

    public StockDetailsTm(String stockId, String supId, int qty, String description, double unitPrice) {
        this.stockId = stockId;
        this.supId = supId;
        this.qty = qty;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public StockDetailsTm(String stockId, String supId, int qty, String description, double unitPrice, double total) {
        this.stockId = stockId;
        this.supId = supId;
        this.qty = qty;
        this.description = description;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "StockDetailsTm{" +
                "stockId='" + stockId + '\'' +
                ", supId='" + supId + '\'' +
                ", qty=" + qty +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
