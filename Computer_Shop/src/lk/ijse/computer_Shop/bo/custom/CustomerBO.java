package lk.ijse.computer_Shop.bo.custom;

import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    public ArrayList<CustomerDTO> gelAllCustomer() throws SQLException, ClassNotFoundException;

    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

    public  boolean existCustomer(String id) throws SQLException, ClassNotFoundException;

    public  boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

    public String genaRateNewId() throws SQLException, ClassNotFoundException;
}
