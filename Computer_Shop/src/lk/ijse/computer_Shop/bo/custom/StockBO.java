package lk.ijse.computer_Shop.bo.custom;

import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.model.OrdersDTO;
import lk.ijse.computer_Shop.model.StockDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface StockBO extends SuperBO {
    public ArrayList<StockDTO> getAllStock()throws SQLException, ClassNotFoundException;
}
