package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import model.Supplier;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddSupplierFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtDescription;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXButton btnAddSupplier;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        //-----------------------Validations in Supplier Form----------------------------

        Pattern idPattern = Pattern.compile("^(S00)[0-9]{1,3}$");
        Pattern namePattern = Pattern.compile("^[A-z]{3,10}$");
        Pattern descriptionPattern = Pattern.compile("^[A-z0-9-/,.]{4,20}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9/,]{4,20}$");
        Pattern contactPattern = Pattern.compile("^(076|077|071|075)[0-9]{7}$");

        map.put(txtId, idPattern);
        map.put(txtName, namePattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtAddress, addressPattern);
        map.put(txtContact, contactPattern);


    }

    //----------------------------------Add Supplier----------------------------------
    public void btnAddSupplierOnAction(ActionEvent actionEvent) {
        Supplier s = new Supplier(txtId.getText(), txtName.getText(), txtDescription.getText(),
                txtAddress.getText(), txtContact.getText());


        try {

            if (CrudUtil.execute("INSERT INTO Supplier VALUES(?,?,?,?,?)", s.getId(), s.getName(), s.getDescription(), s.getAddress(), s.getContact())) {
                Image img = new Image("/asserts/check.png");
                Notifications notifications = Notifications.create().title("SUCCESS").text("Add Supplier Successfully").graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
                notifications.show();

                // new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    //---------------------Update Supplier-----------------------------------------
    public void btnUpdateVehicleOnAction(ActionEvent actionEvent) {
        Supplier s = new Supplier(
                txtId.getText(), txtName.getText(), txtDescription.getText(), txtAddress.getText(), txtContact.getText()
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE Supplier SET cname=? ,description=?, address=? , contact=? WHERE sid=?", s.getName(), s.getDescription(), s.getAddress(),
                    s.getContact(), s.getId());
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }


        } catch (SQLException | ClassNotFoundException e) {

        }

    }

    //-------------------------------------Delete Supplier------------------------------------------
    public void btnDeleteVehicleOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Supplier WHERE sid=?", txtId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {

        }

    }

    //------------------------------Search Supplier-----------------------------------
    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Supplier WHERE sid=?", txtId.getText());
            if (result.next()) {
                txtName.setText(result.getString(2));
                txtDescription.setText(result.getString(3));
                txtAddress.setText(result.getString(4));
                txtContact.setText(result.getString(5));
            } else {
                Notifications notifications = Notifications.create().title("WARNING").text("Empty Result").graphic(null)
                        .hideAfter(Duration.seconds(3)).position(Pos.BOTTOM_RIGHT);
                notifications.showWarning();
                // new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void txt_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map, btnAddSupplier);


        if (keyEvent.getCode() == KeyCode.ENTER) {

            Object response = ValidationUtil.validate(map, btnAddSupplier);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }
}
