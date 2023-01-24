package lk.ijse.computer_Shop.bo.custom;

import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.model.ServiseDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SerivseBO extends SuperBO {
    public ArrayList<ServiseDTO> loadAllSerivse() throws SQLException, ClassNotFoundException;

    public boolean updateSerivse(ServiseDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteSerivse(String code) throws SQLException, ClassNotFoundException;

    public boolean saveSerivse(ServiseDTO dto) throws SQLException, ClassNotFoundException;

    public boolean existSerivse(String code) throws SQLException, ClassNotFoundException;

    public String generateNewIdSerivse() throws SQLException, ClassNotFoundException;
}
