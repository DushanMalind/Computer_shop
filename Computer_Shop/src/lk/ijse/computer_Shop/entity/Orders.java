package lk.ijse.computer_Shop.entity;

import java.time.LocalDate;

public class Orders {
    private String ordId;
    private LocalDate date;
    private String cusId;

    public Orders() {
    }

    public Orders(String ordId, LocalDate date, String cusId) {
        this.ordId = ordId;
        this.date = date;
        this.cusId = cusId;
    }

    public String getOrdId() {
        return ordId;
    }

    public void setOrdId(String ordId) {
        this.ordId = ordId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "ordId='" + ordId + '\'' +
                ", date=" + date +
                ", cusId='" + cusId + '\'' +
                '}';
    }
}
