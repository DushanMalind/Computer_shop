package lk.ijse.computer_Shop.bo.custom;

import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.model.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdersBO extends SuperBO {
    public ArrayList<OrdersDTO> getAllCustomer()throws SQLException, ClassNotFoundException;
}
