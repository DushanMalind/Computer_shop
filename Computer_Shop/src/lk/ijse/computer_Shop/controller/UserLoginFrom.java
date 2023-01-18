package lk.ijse.computer_Shop.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lk.ijse.computer_Shop.util.Navigation;
import lk.ijse.computer_Shop.util.Routes;

import java.io.IOException;

public class UserLoginFrom {

    public ProgressBar processBar;
    public ProgressIndicator processdiactor;
    public TextField txtUserName;
    public TextField txtPassword;
    public AnchorPane roots;
    public JFXButton btnLogin;


    private void processBar(ProgressIndicator p) {
        double value = p.getProgress();
        if (value < 0) {
            value = 2.1;
        } else {
            value = value + 0.1;
            if (value >= 1.0) {
                value = 1.0;
            }
        }
        p.setProgress(value);
    }

    public void btnSingOnAction(ActionEvent actionEvent) {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();
        try {
            if (userName.contains("user") && password.contains("123")) {
//                processBar(processdiactor);
//                processBar(processBar);
                txtUserName.requestFocus();
                txtPassword.setOnAction(event -> btnLogin.requestFocus());

//                roots = FXMLLoader.load(this.getClass().getResource("../view/main_from.fxml"));
                Navigation.navigation(Routes.MAINFROM,roots);
                new Alert(Alert.AlertType.INFORMATION, "Your login").show();
            } else if (userName.contains("admin") && password.contains("123")) {
//                processBar(processdiactor);
//                processBar(processBar);
                new Alert(Alert.AlertType.INFORMATION, "Your login").show();
            } else {
                txtUserName.setStyle("-fx-border-color: red;-fx-border-width: 2px;");
                txtPassword.setStyle("-fx-border-color: red;-fx-border-width: 2px;");
                new Alert(Alert.AlertType.WARNING, "Try Again! UserName Or Password Invalid").show();
            }

        } catch (NullPointerException | IOException e) {

        }
    }
}
