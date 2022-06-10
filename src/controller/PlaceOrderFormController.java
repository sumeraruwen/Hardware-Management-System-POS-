package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Item;
import model.Order;
import model.OrderDetails;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;
import view.tm.CartTM;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PlaceOrderFormController {
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox<String> cmbItemId;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyHand;
    public JFXTextField txtPrice;
    public JFXComboBox<String> cmbVehicleId;
    public JFXComboBox<String> cmbDriverId;
    public JFXTextField txtQty;
    public TableView<CartTM> tblCart;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colPrice;
    public TableColumn colQty;
    public TableColumn colVid;
    public TableColumn colCharge;
    public TableColumn colTotal;
    public TableColumn colButton;
    public JFXTextField txtCharge;
    public Label lblTotal;
    public Label lblTotalCost;
    public Label lblCharge;
    public Label txtOrderId;
    public AnchorPane orderFormContext;
    public Label lblTotalWV;
    public AnchorPane placeOrderFormContext;
    public Label lblTotalItem;
    ObservableList<CartTM> tmList = FXCollections.observableArrayList();
    private JTable placeOrderBO;

    public void initialize() {
        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colVid.setCellValueFactory(new PropertyValueFactory("id"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));
        colButton.setCellValueFactory(new PropertyValueFactory("btn"));


        loadDateAndTime();
        setItemIds();
        setVehicleIds();
        setDriverIds();


        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setItemDetails(newValue);
        });

    }

    private void setDriverIds() {
        try {
            ObservableList<String> diIdObList = FXCollections.observableList(
                    DriverCrudController.getDriverIds()
            );
            cmbDriverId.setItems(diIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setVehicleIds() {
        try {
            ObservableList<String> viIdObList = FXCollections.observableList(
                    VehicleCrudController.getVehicleIds()
            );
            cmbVehicleId.setItems(viIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void setItemDetails(String selectedItemId) {
        try {

            Item i = ItemCrudController.getItem(selectedItemId);

            if (i != null) {
                txtDescription.setText(i.getDescription());
                txtPrice.setText(String.valueOf(i.getPrice()));
                txtQtyHand.setText(String.valueOf(i.getQtyHand()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());

        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void setItemIds() {
        try {
            ObservableList<String> iIdObList = FXCollections.observableList(
                    ItemCrudController.getItemIds()
            );
            cmbItemId.setItems(iIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        double price = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = price * qty;


        CartTM isExists = isExists(cmbItemId.getValue());

        if (isExists != null) {
            for (CartTM temp : tmList
            ) {
                if (temp.equals(isExists)) {
                    temp.setQty((temp.getQty() + qty));
                    temp.setTotal(temp.getTotal() + total);
                }
            }
        } else {

            Button btn = new Button("Delete");

            CartTM tm = new CartTM(
                    cmbItemId.getValue(),
                    txtDescription.getText(),
                    price,
                    qty,
                    cmbVehicleId.getValue(),
                    total,
                    btn


            );

            btn.setOnAction(e -> {

                tmList.remove(tm);
                calculateTotal();

            });

            tmList.add(tm);
            tblCart.setItems(tmList);
        }
        tblCart.refresh();
        calculateTotal();

    }

    private CartTM isExists(String itemCode) {
        for (CartTM tm : tmList) {
            if (tm.getCode().equals(itemCode)) {
                return tm;
            }

        }
        return null;

    }

    private void calculateTotal() {
        double total = 0;
        double charge = 0;
        int item = 0;
        for (CartTM tm : tmList) {
            total += tm.getTotal();
            item += tm.getQty();

        }


        lblTotalCost.setText(String.valueOf(total));
        lblCharge.setText(String.valueOf(txtCharge.getText()));
        lblTotalWV.setText(String.valueOf(total + txtCharge.getText()));
        lblTotalItem.setText(String.valueOf(item));
        double totalWV = total * 1 + charge;

        lblTotalWV.setText(String.valueOf(totalWV));
    }


    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Order order = new Order(
                setnextId(),
                cmbItemId.getValue(),
                lblDate.getText(),
                Double.parseDouble(lblTotalCost.getText()),
                Integer.parseInt(txtQty.getText()),
                cmbVehicleId.getValue(),
                cmbDriverId.getValue()

        );

        ArrayList<OrderDetails> details = new ArrayList<>();

        for (CartTM tm : tmList) {
            details.add(
                    new OrderDetails(
                            setnextId(),
                            tm.getCode(),
                            tm.getDescription(),
                            tm.getQty(),
                            tm.getTotal()

                    )

            );

        }
        Connection connection = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = new OrderCrudController().saveOrder(order);

            if (isOrderSaved) {
                boolean isDetailsSaved = new OrderCrudController().saveOrderDetails(details);
                if (isDetailsSaved) {
                    connection.commit();
                    Image img = new Image("/asserts/check.png");
                    Notifications notifications = Notifications.create().title("SUCCESS").text("Place Order Successfully").graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(4)).position(Pos.BOTTOM_RIGHT);
                    notifications.show();

                    // new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();


                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Error").show();
                }
            } else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }


        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);

        } finally {

            connection.setAutoCommit(true);

        }

    }

    private String setnextId() throws SQLException, ClassNotFoundException {
        String id = OrderCrudController.getOrderId();
        String[] arr = id.split("K", 2);
        String num = "0";
        int digits = 0;
        for (String a : arr) {
            num = a;
        }
        digits = Integer.parseInt(num);
        int implement = digits + 1;
        return String.format("K" + "%0" + 3 + "d", (digits + 1));

    }


    public void btnOrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        loadUi("OrderDetailForm");
    }

    public void loadUi(String location) throws IOException {
        orderFormContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"));
        orderFormContext.getChildren().add(parent);
    }

    public void clearText() {
        txtDescription.clear();
        txtPrice.clear();
        txtQtyHand.clear();
        txtQty.clear();


    }

    public void printBillEvent(MouseEvent mouseEvent) {


        // ObservableList<CartTM>tableRecords=tblCart.getItems();
        double totalCost = Double.parseDouble(lblTotalCost.getText());
        double charge = Double.parseDouble(lblCharge.getText());
        int totalItem = Integer.parseInt(lblTotalItem.getText());

        HashMap paraMap = new HashMap();
        paraMap.put("totalcost", totalCost);
        paraMap.put("charge", charge);
        paraMap.put("item", totalItem);

        try {

            JasperReport compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/Hardware1.jasper"));
            ObservableList<CartTM> tableRecords = tblCart.getItems();

            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, paraMap, new JRBeanCollectionDataSource(tableRecords));

            //Then the report is ready.. let's view it
            JasperViewer.viewReport(jasperPrint, false);


        } catch (JRException e) {
            e.printStackTrace();
        }


    }
}
