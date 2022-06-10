package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Vehicle;
import util.CrudUtil;
import view.tm.VehicleTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewVehicleFormController {
    public TableView<Vehicle> tblVehicles;
    public TableColumn colNumber;
    public TableColumn colType;
    public TableColumn colWeight;
    public TableColumn colId;
    public TableColumn colButton;

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colNumber.setCellValueFactory(new PropertyValueFactory("number"));
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        colWeight.setCellValueFactory(new PropertyValueFactory("weight"));
        colButton.setCellValueFactory(new PropertyValueFactory("btn"));

        try {
            viewVehicles();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void viewVehicles() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Vehicle");
        ObservableList<Vehicle> obList = FXCollections.observableArrayList();

        Button btn = new Button("Delete");
        VehicleTM tm = new VehicleTM(
                colId.getText(),
                colNumber.getText(),
                colType.getText(),
                colWeight.getWidth(),
                btn

        );


        while (result.next()) {

            obList.add(new Vehicle(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4)


            ));
        }
        tblVehicles.setItems(obList);

    }


}
