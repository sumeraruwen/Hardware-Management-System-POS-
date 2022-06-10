package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import util.Loader;

import java.io.IOException;

public class SignInFormController implements Loader {
    public AnchorPane logInContext;
    public AnchorPane dashBoardFormContext;
    public JFXTextField txtName;
    public JFXPasswordField pwdPassword;


    public void btnLogInOnAction(ActionEvent actionEvent) throws IOException {
        // setUi("DashBoardForm");
        String tempName = txtName.getText();
        String tempPassword = pwdPassword.getText();
        if (tempName.equals("admin") && tempPassword.equals("1234")) {
            setUi("DashBoardForm");
            Image img = new Image("/asserts/check.png");
            Notifications notifications = Notifications.create().title("SUCCESS").text("Log In Successfully").graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
            notifications.show();
        } else if (tempName.equals("sumera") && tempPassword.equals("1234")) {
            setUi("CashierDashForm");
            Image img = new Image("/asserts/check.png");
            Notifications notifications = Notifications.create().title("SUCCESS").text("Log In Successfully").graphic(new ImageView(img))
                    .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
            notifications.show();
        } else {
            Notifications notifications = Notifications.create().title("WARNING").text("Wrong Username or Password").graphic(null)
                    .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
            notifications.showWarning();
        }

        // loadUi("DashBoard");
//setUi("DashBoard");


    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) logInContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
    }

    @Override
    public void loadUi(String location) throws IOException {
        logInContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"));
        logInContext.getChildren().add(parent);
    }
}
