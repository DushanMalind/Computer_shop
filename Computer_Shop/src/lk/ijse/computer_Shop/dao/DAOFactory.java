package lk.ijse.computer_Shop.dao;

import lk.ijse.computer_Shop.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.computer_Shop.dao.custom.impl.ItemDAOImpl;

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
        CUSTDAO,ITEMS
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case CUSTDAO:
                return new CustomerDAOImpl();
            case ITEMS:
                return new ItemDAOImpl();
        }
        return null;
    }
}
