package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cashier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewCashierFormController {


    public TableView<Cashier> tblCashiers;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colPassword;


    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colPassword.setCellValueFactory(new PropertyValueFactory("password"));

        try {
            viewCashiers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void viewCashiers() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM Cashier");
        ObservableList<Cashier> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(new Cashier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)


            ));

        }
        tblCashiers.setItems(obList);

    }

}
