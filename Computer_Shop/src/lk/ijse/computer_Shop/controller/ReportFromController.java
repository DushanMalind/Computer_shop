package lk.ijse.computer_Shop.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.computer_Shop.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;

public class ReportFromController {

    @FXML
    private AnchorPane root;

    @FXML
    void btnOnActionCustomer(ActionEvent event) {
        InputStream stream=this.getClass().getResourceAsStream("lk/ijse/computer_Shop/lk.ijse.computer_Shop.report/customer.jrxml");

        HashMap<String,Object> hm=new HashMap<>();
        hm.put("Hello","ma");

        try {
            JasperReport jasperReport= JasperCompileManager.compileReport(stream);
            try {
                JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,hm,DBConnection.getDbConnection().getConnection());
//                JasperPrintManager.printReport(jasperPrint,true);
                JasperViewer.viewReport(jasperPrint);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }catch (JRException e){
        }
    }

    @FXML
    void btnOnActionItem(ActionEvent event) {
        InputStream stream=this.getClass().getResourceAsStream("lk/ijse/computer_Shop/report/customer.jrxml");

        HashMap<String,Object>hm=new HashMap<>();
        hm.put("chair","ma");

        try {
            JasperReport jasperReport= JasperCompileManager.compileReport(stream);
            try {
                JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,hm, DBConnection.getDbConnection().getConnection());
                /* JasperPrintManager.printReport(jasperPrint,true);*/
                JasperViewer.viewReport(jasperPrint);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }catch (JRException e){

        }
    }

    @FXML
    void btnOnActionServise(ActionEvent event) {

    }

    @FXML
    void btnOnActionSupplyer(ActionEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/computer_Shop/view/main_from.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

}
