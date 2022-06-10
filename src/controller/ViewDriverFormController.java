package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Driver;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewDriverFormController {
    public TableView<Driver> tblDrivers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colLicense;
    public TableColumn colContact;
    public TableColumn colButton;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colLicense.setCellValueFactory(new PropertyValueFactory("license"));
        colContact.setCellValueFactory(new PropertyValueFactory("contact"));
        colButton.setCellValueFactory(new PropertyValueFactory("btnV"));


        try {
            viewDrivers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void viewDrivers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Driver");
        ObservableList<Driver> obList = FXCollections.observableArrayList();

        while (result.next()) {

            obList.add(new Driver(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)

            ));
        }
        tblDrivers.setItems(obList);

    }


}
