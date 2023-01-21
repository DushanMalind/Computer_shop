package lk.ijse.computer_Shop.dao.custom.impl;

import lk.ijse.computer_Shop.dao.SQLUtil;
import lk.ijse.computer_Shop.dao.custom.SupplyerDAO;
import lk.ijse.computer_Shop.entity.Supplyer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplyerDAOImpl implements SupplyerDAO {
    @Override
    public ArrayList<Supplyer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Supplyer> allSuplyer=new ArrayList<>();
        ResultSet rst= SQLUtil.execute("SELECT *FROM Supplyer");
        while (rst.next()){
            Supplyer supplyer=new Supplyer(rst.getString("supId"), rst.getString("name"), rst.getDouble("unitPrice"), rst.getString("model"));
            allSuplyer.add(supplyer);
        }
        return allSuplyer;
    }

    @Override
    public boolean add(Supplyer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Supplyer(supId,name,unitPrice,model) VALUES (?,?,?,?)",entity.getSupId(),entity.getName(),entity.getUnitPrice(),entity.getModel());
    }

    @Override
    public boolean update(Supplyer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE supplyer SET model=?, unitPrice=?, name=? WHERE supId=?",entity.getModel(),entity.getUnitPrice(),entity.getName(),entity.getSupId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT supId FROM Supplyer WHERE supId=?",id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT supId FROM  Supplyer  ORDER BY supId DESC LIMIT 1;");
        if (rst.next()){
            String id=rst.getString("supId");
            int newCustomerId=Integer.parseInt(id.replace("S00-",""))+1;
            return String.format("S00-%03d",newCustomerId);
        }else {
            return "S00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Supplyer WHERE supId=?",id);
    }

    @Override
    public Supplyer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT *FROM Supplyer WHERE supId=?",id);
        rst.next();
        return new Supplyer(id+"",rst.getString("name"),rst.getDouble("unitPrice"),rst.getString("model"));
    }
}
