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
import model.Driver;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddDriverFormController {
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtLicense;
    public JFXTextField txtContact;
    public JFXButton btnAddDriver;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        //---------------------------Validations in Driver Form------------------------------------

        Pattern idPattern = Pattern.compile("^(D00)[0-9]{1,3}$");
        Pattern namePattern = Pattern.compile("^[A-z]{3,10}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9/,]{4,20}$");
        Pattern licensePattern = Pattern.compile("^^[A-Z]{1}[0-9]{8}$");
        Pattern contactPattern = Pattern.compile("^(076|077|071|075)[0-9]{7}$");

        map.put(txtId, idPattern);
        map.put(txtName, namePattern);
        map.put(txtAddress, addressPattern);
        map.put(txtLicense, licensePattern);
        map.put(txtContact, contactPattern);

    }
    //---------------------------------Add Driver-----------------------------------

    public void btnAddDriverOnAction(ActionEvent actionEvent) {
        Driver d = new Driver(txtId.getText(), txtName.getText(), txtAddress.getText(), txtLicense.getText(), txtContact.getText());

        try {

            if (CrudUtil.execute("INSERT INTO Driver VALUES(?,?,?,?,?)", d.getId(), d.getName()
                    , d.getAddress(), d.getLicense(), d.getContact())) {
                Image img = new Image("/asserts/check.png");
                Notifications notifications = Notifications.create().title("SUCCESS").text("Add Driver Successfully").graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
                notifications.show();
                // new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    //-----------------------------------Update Driver----------------------------------

    public void btnUpdateVehicleOnAction(ActionEvent actionEvent) {
        Driver d = new Driver(
                txtId.getText(), txtName.getText(), txtAddress.getText(), txtLicense.getText(), txtContact.getText()
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE Driver SET dname=? , address=? , lnum=?,contact=? WHERE did=?", d.getName(), d.getAddress(),
                    d.getLicense(), d.getContact(), d.getId());
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }


        } catch (SQLException | ClassNotFoundException e) {

        }

    }

    //----------------------------Delete Driver-----------------------------------------------
    public void btnDeleteVehicleOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Driver WHERE did=?", txtId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    //------------------------------Search Driver-----------------------------------
    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Driver WHERE did=?", txtId.getText());
            if (result.next()) {
                txtName.setText(result.getString(2));
                txtAddress.setText(result.getString(3));
                txtLicense.setText(result.getString(4));
                txtContact.setText(result.getString(5));
            } else {
                Notifications notifications = Notifications.create().title("WARNING").text("Empty Result").graphic(null)
                        .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
                notifications.showWarning();
                // new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void txt_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map, btnAddDriver);


        if (keyEvent.getCode() == KeyCode.ENTER) {

            Object response = ValidationUtil.validate(map, btnAddDriver);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }
}
