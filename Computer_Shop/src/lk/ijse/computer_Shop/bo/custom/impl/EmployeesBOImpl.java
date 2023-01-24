package lk.ijse.computer_Shop.bo.custom.impl;

import lk.ijse.computer_Shop.bo.custom.EmployeesBO;
import lk.ijse.computer_Shop.dao.DAOFactory;
import lk.ijse.computer_Shop.dao.SuperDAO;
import lk.ijse.computer_Shop.dao.custom.EmployeesDAO;
import lk.ijse.computer_Shop.entity.Employees;
import lk.ijse.computer_Shop.model.EmployeesDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeesBOImpl implements EmployeesBO {
    EmployeesDAO employeesDAO = (EmployeesDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYESS);
    @Override
    public ArrayList<EmployeesDTO> loadAllEmployees() throws SQLException, ClassNotFoundException {
        ArrayList<Employees>allEntity=employeesDAO.getAll();
        ArrayList<EmployeesDTO>allDTO=new ArrayList<>();
        for (Employees e :allEntity){
            allDTO.add(new EmployeesDTO(e.getEmpId(),e.getEmpName(),e.getAddress(),e.getCusId()));
        }
        return allDTO;
    }

    @Override
    public boolean updateEmployees(EmployeesDTO dto) throws SQLException, ClassNotFoundException {
        return employeesDAO.update(new Employees(dto.getEmpId(),dto.getEmpName(),dto.getAddress(),dto.getCusId()));
    }

    @Override
    public boolean deleteEmployees(String code) throws SQLException, ClassNotFoundException {
        return employeesDAO.delete(code);
    }

    @Override
    public boolean saveEmployees(EmployeesDTO dto) throws SQLException, ClassNotFoundException {
        return employeesDAO.add(new Employees(dto.getEmpId(),dto.getEmpName(),dto.getAddress(),dto.getCusId()));
    }

    @Override
    public boolean existEmployees(String code) throws SQLException, ClassNotFoundException {
        return employeesDAO.exist(code);
    }

    @Override
    public String generateNewIdEmployees() throws SQLException, ClassNotFoundException {
        return employeesDAO.generateNewID();
    }
}
