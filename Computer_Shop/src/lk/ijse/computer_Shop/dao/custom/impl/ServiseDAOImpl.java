package lk.ijse.computer_Shop.dao.custom.impl;

import lk.ijse.computer_Shop.dao.SQLUtil;
import lk.ijse.computer_Shop.dao.custom.ServiseDAO;
import lk.ijse.computer_Shop.entity.Servise;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiseDAOImpl implements ServiseDAO {
    @Override
    public ArrayList<Servise> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Servise>allServise=new ArrayList<>();
        ResultSet rst= SQLUtil.execute("SELECT *FROM servise");
        while (rst.next()){
            allServise.add(new Servise(rst.getString("serviseId"),rst.getString("name"),rst.getString("empId"),rst.getDouble("price")));
        }
        return allServise;
    }

    @Override
    public boolean add(Servise entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO servise (serviseId,name,empId,price) VALUES (?,?,?,?)",entity.getServiseId(),entity.getName(),entity.getEmpId(),entity.getPrice());
    }

    @Override
    public boolean update(Servise entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE servise SET name=?, empId=?, price=? WHERE serviseId=?",entity.getName(),entity.getEmpId(),entity.getPrice(),entity.getServiseId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT serviseId FROM servise WHERE serviseId=?", id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT serviseId FROM servise ORDER BY serviseId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("serviseId");
            int newItemId = Integer.parseInt(id.replace("S00-", "")) + 1;
            return String.format("S00-%03d", newItemId);
        } else {
            return "S00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM servise WHERE serviseId=?", id);
    }

    @Override
    public Servise search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT *FROM servise WHERE serviseId=?",id+"");
        rst.next();
        return new Servise(id + "",rst.getString("name"),rst.getString("empId"),rst.getDouble("price"));
    }
}
