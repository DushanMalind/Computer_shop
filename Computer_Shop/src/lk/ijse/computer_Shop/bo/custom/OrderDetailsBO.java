package lk.ijse.computer_Shop.bo.custom;

import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.model.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsBO extends SuperBO {
    public ArrayList<OrderDetailDTO> getAllOrderDetails()throws SQLException, ClassNotFoundException;
}
