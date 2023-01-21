package lk.ijse.computer_Shop.entity;

import java.time.LocalDate;

public class Stock {
    private String stockId;
    private String supId;
    private LocalDate date;

    public Stock() {
    }

    public Stock(String stockId, String supId, LocalDate date) {
        this.stockId = stockId;
        this.supId = supId;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "stockId='" + stockId + '\'' +
                ", supId='" + supId + '\'' +
                ", date=" + date +
                '}';
    }
}
