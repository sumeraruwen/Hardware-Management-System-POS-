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
import model.Item;
import org.controlsfx.control.Notifications;
import util.CrudUtil;
import util.ValidationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AddItemFormController {
    public JFXTextField txtCode;
    public JFXTextField txtId;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyHand;
    public JFXTextField txtPrice;
    public JFXButton btnAddItem;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize() {

        //----------------------Validations in Item Form----------------------------------------------

        Pattern codePattern = Pattern.compile("^(P00)[0-9]{1,3}$");
        Pattern idPattern = Pattern.compile("^(S00)[0-9]{1,3}$");
        Pattern descriptionPattern = Pattern.compile("^[A-z0-9-/ ,.]{4,20}$");
        Pattern qtyPattern = Pattern.compile("^[0-9]{1,6}$");
        Pattern pricePattern = Pattern.compile("^[0-9.00]{1,9}$");

        map.put(txtCode, codePattern);
        map.put(txtId, idPattern);
        map.put(txtDescription, descriptionPattern);
        map.put(txtQtyHand, qtyPattern);
        map.put(txtPrice, pricePattern);


    }

    //------------------Add Product--------------------------------------
    public void btnAddItemOnAction(ActionEvent actionEvent) {
        Item i = new Item(txtCode.getText(), txtId.getText(), txtDescription.getText(), Integer.parseInt(txtQtyHand.getText()), Double.parseDouble(txtPrice.getText()));

        try {

            if (CrudUtil.execute("INSERT INTO Item VALUES(?,?,?,?,?)", i.getCode(), i.getId(), i.getDescription(), i.getQtyHand(), i.getPrice())) {
                Image img = new Image("/asserts/check.png");
                Notifications notifications = Notifications.create().title("SUCCESS").text("Add Product Successfully").graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
                notifications.show();
                // new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    //------------------------------Update Product-------------------------------------
    public void btnUpdateVehicleOnAction(ActionEvent actionEvent) {
        Item i = new Item(
                txtCode.getText(), txtId.getText(), txtDescription.getText(), Integer.parseInt(txtQtyHand.getText()), Double.parseDouble(txtPrice.getText())
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE Item SET sid=? ,description=?, qtyhand=? , price=? WHERE icode=?",
                    i.getId(), i.getDescription(), i.getQtyHand(),
                    i.getPrice(), i.getCode());
            if (isUpdated) {
                Image img = new Image("/asserts/check.png");
                Notifications notifications = Notifications.create().title("SUCCESS").text("Update Product Successfully").graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
                notifications.show();
                // new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }


        } catch (SQLException | ClassNotFoundException e) {

        }

    }

    //----------------------------Delete Product-----------------------------------------------------
    public void btnDeleteVehicleOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("DELETE FROM Item WHERE icode=?", txtCode.getText())) {
                Image img = new Image("/asserts/check.png");
                Notifications notifications = Notifications.create().title("SUCCESS").text("Delete Product Successfully").graphic(new ImageView(img))
                        .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
                notifications.show();
                // new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {

        }


    }

    //-------------------------Search Product---------------------------------------
    public void txtSearchOnAction(ActionEvent actionEvent) {
        try {
            ResultSet result = CrudUtil.execute("SELECT * FROM Item WHERE icode=?", txtCode.getText());
            if (result.next()) {
                txtId.setText(result.getString(2));
                txtDescription.setText(result.getString(3));
                txtQtyHand.setText(result.getString(4));
                txtPrice.setText(result.getString(5));
            } else {
                Notifications notifications = Notifications.create().title("WARNING").text("Empty Result").graphic(null)
                        .hideAfter(Duration.seconds(3)).position(Pos.BOTTOM_RIGHT);
                notifications.showWarning();
                //  new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void txt_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map, btnAddItem);


        if (keyEvent.getCode() == KeyCode.ENTER) {

            Object response = ValidationUtil.validate(map, btnAddItem);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }
}
