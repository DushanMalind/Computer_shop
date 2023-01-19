package lk.ijse.computer_Shop.bo.custom;

import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.model.CustomerDTO;
import lk.ijse.computer_Shop.model.ItemDTO;
import lk.ijse.computer_Shop.model.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseBO extends SuperBO {
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException;

    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException;

    public boolean existItem(String id) throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    public String generateNewOrderIdItem() throws SQLException, ClassNotFoundException;

    public ArrayList<CustomerDTO> loadAllCustomerId() throws SQLException, ClassNotFoundException;

    public ArrayList<ItemDTO>loadAllItemId() throws SQLException, ClassNotFoundException;

    public ItemDTO serachItem(String code) throws SQLException, ClassNotFoundException;

    public boolean purchesOrder(OrdersDTO dto);
}
