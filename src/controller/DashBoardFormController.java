package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.Loader;

import java.io.IOException;


public class DashBoardFormController implements Loader {
    public AnchorPane dashBoardFormContext;

    public void initialize() throws IOException {
        loadUi("DashBoard");
    }

    public void btnAddItemOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("AddItemForm");
    }

    @Override
    public void loadUi(String location) throws IOException {
        dashBoardFormContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"));
        dashBoardFormContext.getChildren().add(parent);
    }

    public void DashBoardOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("DashBoard");

    }

    public void btnAddSupplierOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("AddSupplierForm");
    }

    public void btnAddDriverOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("AddDriverForm");
    }

    public void btnAddVehicleOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("AddVehicleForm");
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("PlaceOrderForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) dashBoardFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));

    }

    public void btnViewSupplierOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ViewSupplierForm");
    }

    public void btnViewItemOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ViewItemForm");
    }

    public void btnViewVehicleOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ViewVehicleForm");
    }

    public void btnViewDriverOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ViewDriverForm");
    }


    public void btnOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("OrderDetailForm");
    }

    public void btnAddCashierOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("AddCashierForm");
    }
}
