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
import lk.ijse.computer_Shop.bo.custom.StockDetailsBO;
import lk.ijse.computer_Shop.model.StockDetailDTO;
import lk.ijse.computer_Shop.view.tdm.StockDetailsTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockDetailsFromController {

    public AnchorPane root;
    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSave1;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colStockId;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colUnitprice;

    @FXML
    private TableView<StockDetailsTm> tblStockDetails;

    StockDetailsBO stockDetailsBO = (StockDetailsBO) Factory.getFactory().getBo(Factory.BOTypes.STOCKDETAILS);

    public void initialize(){
        colStockId.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitprice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        lordAllStockDetails();

    }

    private void lordAllStockDetails(){
        tblStockDetails.getItems().clear();

        try {
            ArrayList<StockDetailDTO>allStock=stockDetailsBO.getAllStockDetails();
            for (StockDetailDTO s:allStock){
                tblStockDetails.getItems().add(new StockDetailsTm(s.getStockId(),s.getSupId(),s.getQty(),s.getUnitPrice()));
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
