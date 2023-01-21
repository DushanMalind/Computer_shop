package lk.ijse.computer_Shop.bo.custom;

import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.model.SupplyerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplyerBO extends SuperBO {
    public ArrayList<SupplyerDTO> getAllCustomer()throws SQLException, ClassNotFoundException;

    public boolean addCustomer(SupplyerDTO dto)throws SQLException, ClassNotFoundException;

    public  boolean upDateCustomer(SupplyerDTO dto)throws SQLException, ClassNotFoundException;

    public boolean existCustomer(String id)throws SQLException, ClassNotFoundException;

    public boolean deleteCustomer(String id)throws SQLException, ClassNotFoundException;

    public String genaRateNewIdSupplyer() throws SQLException, ClassNotFoundException;
}
