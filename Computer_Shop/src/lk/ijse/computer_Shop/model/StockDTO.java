package lk.ijse.computer_Shop.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StockDTO {
    private String stockId;
    private String supId;
    private LocalDate date;


    public StockDTO() {
    }

    public StockDTO(String stockId, String supId, LocalDate date) {
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
}
