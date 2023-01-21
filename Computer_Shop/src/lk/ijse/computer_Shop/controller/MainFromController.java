package lk.ijse.computer_Shop.controller;

import com.sun.nio.sctp.Notification;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.computer_Shop.util.Navigation;
import lk.ijse.computer_Shop.util.Routes;

import java.io.IOException;


public class MainFromController {

    @FXML
    private ImageView imgCustomer;

    @FXML
    private ImageView imgEmployess;

    @FXML
    private ImageView imgItem;

    @FXML
    private ImageView imgOrderDeatils;

    @FXML
    private ImageView imgOrderPlace;

    @FXML
    private ImageView imgOrders;

    @FXML
    private ImageView imgPlaceOrder;

    @FXML
    private ImageView imgServise;

    @FXML
    private ImageView imgStock;

    @FXML
    private ImageView imgStockDeatils;

    @FXML
    private ImageView imgSupplyer;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblHead;

    @FXML
    private AnchorPane root;

    public void initialize(){
        FadeTransition fadeTransition=new FadeTransition(Duration.millis(3000),root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(2000), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblHead.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView){
            ImageView icon=(ImageView) event.getSource();

            switch (icon.getId()){
                case "imgCustomer":
                    lblHead.setText("Customer From");
                    lblDescription.setText("Click to add, edit, delete, search or view customers");
                    break;
                case "imgItem":
                    lblHead.setText("Item From");
                    lblDescription.setText("Click to add, edit, delete, search or view Item");
                    break;
                case "imgOrders":
                    lblHead.setText("Orders From");
                    lblDescription.setText("Click to add, edit, delete, search or view Orders");
                    break;
                case "imgOrderDeatils":
                    lblHead.setText("Order Details From");
                    lblDescription.setText("Click to add, edit, delete, search or view Order Details");
                    break;
                case "imgPlaceOrder":
                    lblHead.setText("Place Order From");
                    lblDescription.setText("Click to add, edit, delete, search or view Place Order");
                    break;
                case "imgSupplyer":
                    lblHead.setText("Supplier From");
                    lblDescription.setText("Click to add, edit, delete, search or view Supplier");
                    break;
                case "imgStock":
                    lblHead.setText("Stock From");
                    lblDescription.setText("Click to add, edit, delete, search or view Stock");
                    break;
                case "imgStockDeatils":
                    lblHead.setText("Stock Details From");
                    lblDescription.setText("Click to add, edit, delete, search or view Stock Details");
                    break;
                case "imgOrderPlace":
                    lblHead.setText("Order Place From");
                    lblDescription.setText("Click to add, edit, delete, search or view Order Place");
                    break;
                case "imgEmployess":
                    lblHead.setText("Employees  From");
                    lblDescription.setText("Click to add, edit, delete, search or view Employees");
                    break;
                case "imgServise":
                    lblHead.setText("Services From");
                    lblDescription.setText("Click to add, edit, delete, search or view Services");
                    break;
            }
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(4000), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    @FXML
    private void navigate(MouseEvent event) throws IOException,NullPointerException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            Parent root = null;


            switch (icon.getId()) {
                case "imgCustomer":
                    root = FXMLLoader.load(this.getClass().getResource("../view/customerFrom.fxml"));
                    break;
                case "imgItem":
                    root = FXMLLoader.load(this.getClass().getResource("../view/itemFrom.fxml"));
                    break;
                case "imgPlaceOrder":
                    root = FXMLLoader.load(this.getClass().getResource("../view/placeOrderfrom.fxml"));
                    break;
                case "imgOrders":
                    root = FXMLLoader.load(this.getClass().getResource("../view/ordersFrom.fxml"));
                    break;
                case "imgOrderDeatils":
                    root = FXMLLoader.load(this.getClass().getResource("../view/OrderDetailsFrom.fxml"));
                    break;
                case "imgSupplyer":
                    root = FXMLLoader.load(this.getClass().getResource("../view/supplyerFrom.fxml"));
                    break;
                case "imgOrderPlace":
                    root = FXMLLoader.load(this.getClass().getResource("../view/PlaceStockFrom.fxml"));
                    break;
                default:
                    new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
            }


                try {
                    if (root!= null) {
                        Stage primary = (Stage) this.root.getScene().getWindow();
                        Scene scene = new Scene(root);
                        primary.setScene(scene);
                        primary.centerOnScreen();

                        TranslateTransition tt = new TranslateTransition(Duration.millis(4000), scene.getRoot());
                        tt.setFromX(-scene.getWidth());
                        tt.setToX(0);
                        tt.play();
                    }
                }catch (NullPointerException e){
//                    new Alert(Alert.AlertType.WARNING, "Not suitable").show();
                }

        }
    }

}
