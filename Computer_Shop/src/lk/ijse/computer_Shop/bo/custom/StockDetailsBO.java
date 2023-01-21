package lk.ijse.computer_Shop.bo.custom;

import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.model.OrdersDTO;
import lk.ijse.computer_Shop.model.StockDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StockDetailsBO extends SuperBO {
    public ArrayList<StockDetailDTO> getAllStockDetails()throws SQLException, ClassNotFoundException;
}
