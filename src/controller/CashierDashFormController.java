package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.Loader;

import java.io.IOException;

public class CashierDashFormController implements Loader {
    public AnchorPane cashierDashFormContext;
    public AnchorPane cashierDashContext;

    public void initialize() throws IOException {
        loadUi("PlaceOrderForm");

    }


    public void btnViewVehicleOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ViewVehicleForm");
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("PlaceOrderForm");
    }

    public void btnViewItemOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ViewItemForm");
    }

    public void btnViewSupplierOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ViewSupplierForm");
    }

    public void btnViewDriverOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ViewDriverForm");
    }

    @Override
    public void loadUi(String location) throws IOException {
        cashierDashFormContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"));
        cashierDashFormContext.getChildren().add(parent);
    }


    public void btnOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("OrderDetailForm");
    }

    public void btnSignOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignInForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) cashierDashContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
    }
}
