package lk.ijse.computer_Shop.model;

import java.util.ArrayList;

public class PlaceStock {
    private String stockId;
    private String supId;
    private ArrayList<CartDeatilStock> orderDetails = new ArrayList<>();

    public PlaceStock() {
    }

    public PlaceStock(String stockId, String supId, ArrayList<CartDeatilStock> orderDetails) {
        this.stockId = stockId;
        this.supId = supId;
        this.orderDetails = orderDetails;
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

    public ArrayList<CartDeatilStock> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<CartDeatilStock> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "PlaceStock{" +
                "stockId='" + stockId + '\'' +
                ", supId='" + supId + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
