package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.PurchaseBO;
import lk.ijse.computer_Shop.model.CustomerDTO;
import lk.ijse.computer_Shop.model.ItemDTO;
import lk.ijse.computer_Shop.model.OrdersDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseBOImpl implements PurchaseBO {
    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemDTO searchItem(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewOrderIdItem() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<CustomerDTO> loadAllCustomerId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<ItemDTO> loadAllItemId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ItemDTO serachItem(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean purchesOrder(OrdersDTO dto) {
        return false;
    }
}
