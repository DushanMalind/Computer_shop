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
import lk.ijse.computer_Shop.bo.custom.OrderDetailsBO;
import lk.ijse.computer_Shop.model.OrderDetailDTO;
import lk.ijse.computer_Shop.view.tdm.OrderDetailTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsFromController {

    @FXML
    private JFXButton btnSave;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colOrdId;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitprice;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<OrderDetailTm> tblOrderDetails;

    OrderDetailsBO orderDetailsBO = (OrderDetailsBO) Factory.getFactory().getBo(Factory.BOTypes.ORDERSDETAILS);
    public void initialize() {
        colOrdId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitprice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        lordAllCustomer();
    }
    private void lordAllCustomer(){
        tblOrderDetails.getItems().clear();

        try {
            ArrayList<OrderDetailDTO> allOrderDetails= orderDetailsBO.getAllOrderDetails();
            for (OrderDetailDTO o :allOrderDetails){
                tblOrderDetails.getItems().add(new OrderDetailTm(o.getOid(),o.getItemCode(),o.getQty(),o.getUnitPrice()));
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
