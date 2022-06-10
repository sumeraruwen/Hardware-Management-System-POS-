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
import model.Vehicle;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddVehicleFormController {
    public JFXTextField txtNumber;
    public JFXTextField txtType;
    public JFXTextField txtWeight;
    public JFXTextField txtId;
    public JFXButton btnAddVehicle;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {
        //-----------------------Validations in Vehicle Form----------------------------

        btnAddVehicle.setDisable(true);
        Pattern idPattern = Pattern.compile("^(V00)[0-9]{1,3}$");
        Pattern numberPattern = Pattern.compile("^[A-Z0-9-/]{7,9}$");
        Pattern typePattern = Pattern.compile("^[A-z]{3,10}$");
        Pattern weightPattern = Pattern.compile("^[1-90-9.]{3,9}$");

        map.put(txtId, idPattern);
        map.put(txtNumber, numberPattern);
        map.put(txtType, typePattern);
        map.put(txtWeight, weightPattern);

    }

    //----------------------------Add Vehicle---------------------------------
    public void btnAddVehicleOnAction(ActionEvent actionEvent) {
        Vehicle v = new Vehicle(txtId.getText(), txtNumber.getText(), txtType.getText(), Double.parseDouble(txtWeight.getText()));

        try {

            if (CrudUtil.execute("INSERT INTO Vehicle VALUES(?,?,?,?)", v.getId(), v.getNumber(), v.getType(), v.getWeight())) {
                Image img = new Image("/asserts/check.png");
                Notifications notifications = Notifications.create().title("SUCCESS").text("Add Vehicle Successfully").graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
                notifications.show();
                // new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
                clearText();
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();

            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    //----------------------------Search Vehicle--------------------------------
    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Vehicle WHERE vid=?", txtId.getText());
            if (result.next()) {
                txtNumber.setText(result.getString(2));
                txtType.setText(result.getString(3));
                txtWeight.setText(String.valueOf(result.getDouble(4)));
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
        ValidationUtil.validate(map, btnAddVehicle);


        if (keyEvent.getCode() == KeyCode.ENTER) {

            Object response = ValidationUtil.validate(map, btnAddVehicle);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    //-------------------Clear Text Fields----------------------------------
    public void clearText() {
        txtId.clear();
        txtNumber.clear();
        txtType.clear();
        txtWeight.clear();
        txtId.requestFocus();
    }

    //---------------------------Update Vehicle----------------------------------
    public void btnUpdateVehicleOnAction(ActionEvent actionEvent) {

        Vehicle v = new Vehicle(
                txtId.getText(), txtNumber.getText(), txtType.getText(),
                Double.parseDouble(txtWeight.getText())
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE Vehicle SET vnum=? , type=? , weight=? WHERE vid=?", v.getNumber(), v.getType(), v.getWeight(), v.getId());
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }


        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    //------------------------Delete Vehicle------------------------------
    public void btnDeleteVehicleOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Vehicle WHERE vid=?", txtId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {

        }
    }


    public void btnSignOutOnAction(ActionEvent actionEvent) {
    }
}
