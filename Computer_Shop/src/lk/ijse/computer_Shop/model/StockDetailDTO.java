package lk.ijse.computer_Shop.model;

public class StockDetailDTO {
    private String stockId;
    private String supId;
    private int qty;
    private double unitPrice;

    public StockDetailDTO() {
    }


    public StockDetailDTO(String stockId, String supId, int qty, double unitPrice) {
        this.stockId = stockId;
        this.supId = supId;
        this.qty = qty;
        this.unitPrice = unitPrice;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "StockDetailDTO{" +
                "stockId='" + stockId + '\'' +
                ", supId='" + supId + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
