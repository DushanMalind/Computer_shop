package lk.ijse.computer_Shop.bo;

import lk.ijse.computer_Shop.bo.custom.impl.CustomerBOImpl;

public class Factory {
    private static Factory factory;

    private  Factory(){
    }

    public static Factory getFactory(){
        if (factory==null){
            factory=new Factory();
        }
        return factory;
    }

    public enum BOTypes{
        CUSTOMER
    }

    public SuperBO getBo(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return new CustomerBOImpl();
        }
        return null;
    }
}

