package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Cashier;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.Loader;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddCashierFormController implements Loader {


    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtPassword;
    public AnchorPane cashierContext;
    public JFXButton btnAddCashier;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        //------------------------Validations in Cashier Form-------------------------------------

        Pattern idPattern = Pattern.compile("^(CAS0)[0-9]{1,4}$");
        Pattern namePattern = Pattern.compile("^[A-z]{3,10}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9/,]{4,20}$");
        Pattern passwordPattern = Pattern.compile("^[0-9]{4}$");

        map.put(txtId, idPattern);
        map.put(txtName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtPassword, passwordPattern);

    }

    //--------------------Add Cashier--------------------------------------
    public void btnAddCashierOnAction(ActionEvent actionEvent) {
        Cashier ca = new Cashier(txtId.getText(), txtName.getText(), txtAddress.getText(), txtPassword.getText());

        try {

            if (CrudUtil.execute("INSERT INTO Cashier VALUES(?,?,?,?)", ca.getId(), ca.getName(), ca.getAddress(), ca.getPassword())) {
                Image img = new Image("/asserts/check.png");
                Notifications notifications = Notifications.create().title("SUCCESS").text("Add Cashier Successfully").graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
                notifications.show();

                //new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    //------------------------View Cashier---------------------------------------

    public void btnViewCashierOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("ViewCashierForm");
    }

    @Override
    public void loadUi(String location) throws IOException {
        cashierContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"));
        cashierContext.getChildren().add(parent);
    }

    public void txt_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map, btnAddCashier);


        if (keyEvent.getCode() == KeyCode.ENTER) {

            Object response = ValidationUtil.validate(map, btnAddCashier);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    //-------------------------------Update Cashier----------------------------------------------

    public void btnUpdateCashierOnAction(ActionEvent actionEvent) {
        Cashier ca = new Cashier(
                txtId.getText(), txtName.getText(), txtAddress.getText(), txtPassword.getText()
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE Cashier SET name=? , address=? , password=? WHERE id=?", ca.getName(), ca.getAddress(),
                    ca.getPassword(), ca.getId());
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }


        } catch (SQLException | ClassNotFoundException e) {

        }


    }
    //--------------------------------Delete Cashier---------------------------------------

    public void btnDeleteCashierOnAction(ActionEvent actionEvent) {

        try {
            if (CrudUtil.execute("DELETE FROM Cashier WHERE id=?", txtId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {

        }


    }

    //-------------------------Search Cashier---------------------------------------
    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Cashier WHERE id=?", txtId.getText());
            if (result.next()) {
                txtName.setText(result.getString(2));
                txtAddress.setText(result.getString(3));
                txtPassword.setText(result.getString(4));

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
