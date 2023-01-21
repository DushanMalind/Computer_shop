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
import lk.ijse.computer_Shop.bo.custom.OrdersBO;
import lk.ijse.computer_Shop.bo.custom.PurchaseBO;
import lk.ijse.computer_Shop.bo.custom.impl.PurchaseBOImpl;
import lk.ijse.computer_Shop.model.CustomerDTO;
import lk.ijse.computer_Shop.model.OrdersDTO;
import lk.ijse.computer_Shop.view.tdm.OrdersTm;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class OrdersFromController {

    public AnchorPane root;
    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSave1;

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colOrdesId;

    @FXML
    private TableView<OrdersTm> tblOrders;

    OrdersBO ordersBO = (OrdersBO) Factory.getFactory().getBo(Factory.BOTypes.OREDERS);

    public void initialize(){
        colOrdesId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        lordAllCustomer();
    }

    private void lordAllCustomer(){
        tblOrders.getItems().clear();

        try {
            ArrayList<OrdersDTO> allOrders= ordersBO.getAllCustomer();
            for (OrdersDTO o:allOrders){
                tblOrders.getItems().add(new OrdersTm(o.getOrderId(),LocalDate.now(),o.getCustomerId()));
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
