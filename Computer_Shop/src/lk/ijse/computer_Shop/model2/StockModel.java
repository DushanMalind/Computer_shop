package lk.ijse.computer_Shop.model2;


import lk.ijse.computer_Shop.dao.SQLUtil;
import lk.ijse.computer_Shop.model.StockDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockModel {
    public static boolean save(StockDTO order) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO stock VALUES(?,?,?)";
        return SQLUtil.execute(sql,order.getStockId(),order.getSupId(),order.getDate());
    }

    public static String generateNextOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT stockId FROM stock ORDER BY stockId DESC LIMIT 1;");
        if (rst.next()){
            String id=rst.getString("stockId");
            int newCustomerId=Integer.parseInt(id.replace("W00-",""))+1;
            return String.format("W00-%03d",newCustomerId);
        }else {
            return "W00-001";
        }
    }

    /*private static String generateNextOrderId(String currentOrderId) {
        if (currentOrderId != null) {
            String[] split = currentOrderId.split("W0");
            int id = Integer.parseInt(split[1]);
            id += 1;
            return "W0" + id;
        }
        return "W001";

    }*/
}
