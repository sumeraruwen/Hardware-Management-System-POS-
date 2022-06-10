package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetailFormController {

    public TableColumn colId;
    public TableColumn colCode;
    public TableColumn colDate;
    public TableColumn colTotal;
    public TableColumn colQty;
    public TableColumn colVid;
    public TableColumn colDid;
    public TableView<OrderDetails> tblOrderDetails;
    public TableColumn colDescription;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory("oid"));
        colCode.setCellValueFactory(new PropertyValueFactory("icode"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));


        try {
            viewOrderDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void viewOrderDetails() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM OrderDetail");

        ObservableList<OrderDetails> obList2 = FXCollections.observableArrayList();

        while (result.next()) {

            obList2.add(
                    new OrderDetails(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getInt(4),
                            result.getDouble(5)


                    )
            );
        }
        tblOrderDetails.setItems(obList2);


    }

}
