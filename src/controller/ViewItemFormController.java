package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewItemFormController {
    public TableView tblItems;
    public TableColumn colCode;
    public TableColumn colId;
    public TableColumn colDescription;
    public TableColumn colQtyHand;
    public TableColumn colPrice;


    public void initialize() {
        colCode.setCellValueFactory(new PropertyValueFactory("code"));
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQtyHand.setCellValueFactory(new PropertyValueFactory("qtyHand"));
        colPrice.setCellValueFactory(new PropertyValueFactory("price"));

        try {
            viewItems();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void viewItems() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Item");
        ObservableList<Item> obList = FXCollections.observableArrayList();


        while (result.next()) {

            obList.add(new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getDouble(5)

            ));
        }
        tblItems.setItems(obList);


    }


}
