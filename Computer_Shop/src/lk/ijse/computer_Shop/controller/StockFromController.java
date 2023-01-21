package lk.ijse.computer_Shop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.computer_Shop.bo.Factory;
import lk.ijse.computer_Shop.bo.SuperBO;
import lk.ijse.computer_Shop.bo.custom.StockBO;
import lk.ijse.computer_Shop.model.StockDTO;
import lk.ijse.computer_Shop.view.tdm.StockTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockFromController {

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSave1;

    @FXML
    private TableColumn<?, ?> colData;

    @FXML
    private TableColumn<?, ?> colStock;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<StockTm> tblStock;

    StockBO stockBO = (StockBO) Factory.getFactory().getBo(Factory.BOTypes.STOCK);

    public void initialize(){
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colData.setCellValueFactory(new PropertyValueFactory<>("date"));

        lordAllStock();
    }

    private void lordAllStock() {
        tblStock.getItems().clear();

        try {
            ArrayList<StockDTO>allStock=stockBO.getAllStock();
            for (StockDTO s:allStock){
                tblStock.getItems().add(new StockTm(s.getStockId(),s.getSupId(),s.getDate()));
            }

        }catch (SQLException e){

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
