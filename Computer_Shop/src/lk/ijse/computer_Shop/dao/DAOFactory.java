package lk.ijse.computer_Shop.dao;

import lk.ijse.computer_Shop.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.computer_Shop.dao.custom.impl.ItemDAOImpl;
import lk.ijse.computer_Shop.dao.custom.impl.OrderDetailsDAOImpl;
import lk.ijse.computer_Shop.dao.custom.impl.OrdersDAOImpl;

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
        CUSTDAO,ITEMS,ORDERS,ORDERDETALIS
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

        }
        return null;
    }
}
