package lk.ijse.computer_Shop.dao.custom.impl;

import lk.ijse.computer_Shop.dao.SQLUtil;
import lk.ijse.computer_Shop.dao.custom.ItemDAO;
import lk.ijse.computer_Shop.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItem=new ArrayList<>();
        ResultSet rst= SQLUtil.execute("SELECT *FROM Item");
        while (rst.next()){
            allItem.add(new Item(rst.getString("itemId"),rst.getString("description"),rst.getDouble("unitPrice"),rst.getInt("qtyOnhand")));
        }
        return allItem;
    }

    @Override
    public boolean add(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO Item (itemId,description,unitPrice,qtyOnhand) VALUES (?,?,?,?)",entity.getItemId(),entity.getDescription(),entity.getUnitPrice(),entity.getQtyOnhand());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE Item SET description=?, unitPrice=?, qtyOnhand=? WHERE itemId=?",entity.getDescription(),entity.getUnitPrice(),entity.getQtyOnhand(),entity.getItemId());
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT itemId FROM Item WHERE itemId=?",id);
        return rst.next();
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT itemId FROM Item ORDER BY itemId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("itemId");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM Item WHERE itemId=?",id);
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM Item WHERE itemId=?",id+"");
        rst.next();
        return new Item(id+"",rst.getString("description"),rst.getDouble("unitPrice"),rst.getInt("qtyOnhand"));
    }

}
