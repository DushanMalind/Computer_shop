package lk.ijse.computer_Shop.bo.custom;

import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.model.EmployeesDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeesBO extends SuperBO {
    public ArrayList<EmployeesDTO> loadAllEmployees() throws SQLException, ClassNotFoundException;

    public boolean updateEmployees(EmployeesDTO dto) throws SQLException, ClassNotFoundException;

    public boolean deleteEmployees(String code) throws SQLException, ClassNotFoundException;

    public boolean saveEmployees(EmployeesDTO dto) throws SQLException, ClassNotFoundException;

    public boolean existEmployees(String code) throws SQLException, ClassNotFoundException;

    public String generateNewIdEmployees() throws SQLException, ClassNotFoundException;
}
