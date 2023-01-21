package lk.ijse.computer_Shop.view.tdm;

public class StockDetailsTm {
    private String stockId;
    private String supId;
    private String name;
    private String model;
    private int qty;
    private double unitPrice;
    private double total;


    public StockDetailsTm() {
    }

    public StockDetailsTm(String stockId, String supId, int qty, double unitPrice) {
        this.stockId = stockId;
        this.supId = supId;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public StockDetailsTm(String supId, String name, String model, double unitPrice, int qty, double total) {
        this.supId = supId;
        this.name = name;
        this.model = model;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.total = total;
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

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    @Override
    public String toString() {
        return "StockDetailsTm{" +
                "supId='" + supId + '\'' +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", total=" + total +
                ", stockId='" + stockId + '\'' +
                '}';
    }
}
