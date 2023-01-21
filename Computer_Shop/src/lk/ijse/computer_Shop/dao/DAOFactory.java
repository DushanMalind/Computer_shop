package lk.ijse.computer_Shop.dao;

import lk.ijse.computer_Shop.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){
    }

    public static DAOFactory getDaoFactory(){
        if (daoFactory==null){
            daoFactory=new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes{
        CUSTDAO,ITEMS,ORDERS,ORDERDETALIS,SUPPLYER,STOCK,STOCKDEATAILS
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTDAO:
                return new CustomerDAOImpl();
            case ITEMS:
                return new ItemDAOImpl();
            case ORDERS:
                return new OrdersDAOImpl();
            case ORDERDETALIS:
                return new OrderDetailsDAOImpl();
            case SUPPLYER:
                return new SupplyerDAOImpl();
            case STOCK:
                return new StockDAOImpl();
            case STOCKDEATAILS:
                return new StockDetailsDAOImpl();


        }
        return null;
    }
}
