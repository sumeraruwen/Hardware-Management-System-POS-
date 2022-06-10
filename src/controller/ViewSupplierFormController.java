package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Supplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewSupplierFormController {
    public TableView<Supplier> tblSuppliers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colDescription;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colOption;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));


        try {
            viewSuppliers();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    private void viewSuppliers() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier");

        ObservableList<Supplier> obList = FXCollections.observableArrayList();

        while (result.next()) {

            obList.add(
                    new Supplier(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5)


                    )
            );
        }
        tblSuppliers.setItems(obList);


    }
}
