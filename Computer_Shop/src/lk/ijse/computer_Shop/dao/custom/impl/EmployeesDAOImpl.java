package lk.ijse.computer_Shop.dao.custom.impl;

import lk.ijse.computer_Shop.dao.SQLUtil;
import lk.ijse.computer_Shop.dao.custom.EmployeesDAO;
import lk.ijse.computer_Shop.entity.Employees;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeesDAOImpl implements EmployeesDAO {
    @Override
    public ArrayList<Employees> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employees> allEMP=new ArrayList<>();
        ResultSet rst= SQLUtil.execute("SELECT *FROM employees");
        while (rst.next()){
            allEMP.add(new Employees(rst.getString("empId"),rst.getString("empName"),rst.getString("address"), rst.getString("cusId")));
        }
        return allEMP;
    }

    @Override
    public boolean add(Employees entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employees (empId, empName, address, cusId) VALUES (?,?,?,?)",entity.getEmpId(),entity.getEmpName(),entity.getAddress(),entity.getCusId());
    }

    @Override
    public boolean update(Employees entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE employees SET empName=?, address=?, cusId=? WHERE empId=?",entity.getEmpName(),entity.getAddress(),entity.getCusId(),entity.getEmpId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT empId FROM employees WHERE empId=?",id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT empId FROM employees ORDER BY empId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("empId");
            int newItemId = Integer.parseInt(id.replace("E00-", "")) + 1;
            return String.format("E00-%03d", newItemId);
        } else {
            return "E00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM employees WHERE empId=?", id);
    }

    @Override
    public Employees search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst  = SQLUtil.execute("SELECT * FROM employees WHERE empId=?",code+"");
        rst.next();
        return new Employees(code + "",rst.getString("empName"),rst.getString("address"), rst.getString("cusId"));
    }
}
