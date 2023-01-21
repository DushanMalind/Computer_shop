package lk.ijse.computer_Shop.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane mealPackagesIdMain;
    public static void navigation(Routes routes,AnchorPane mealPackagesIdMain) throws IOException {
        Navigation.mealPackagesIdMain=mealPackagesIdMain;
        Navigation.mealPackagesIdMain.getChildren().clear();
        Stage window=(Stage) Navigation.mealPackagesIdMain.getScene().getWindow();

        switch (routes){
            case MAINFROM:
                window.setTitle("Main From");
                initUI("main_from.fxml");
                break;
            case PLACE:
                window.setTitle("Main From");
                initUI("PlaceStockFrom.fxml");
                break;

            default:
                new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
        }
    }
    private static void initUI(String location) throws IOException {
        Navigation.mealPackagesIdMain.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/computer_shop/view/" + location)));
    }
}
