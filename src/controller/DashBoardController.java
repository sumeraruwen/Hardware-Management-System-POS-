package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Order;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashBoardController {
    public Label lblSales;
    public CategoryAxis caId;
    public NumberAxis naSales;
    public BarChart<String, Number> barchart;
    public Label lblTotalOrders;
    public Label lblTotalItems;
    public Label lblTotalSales;
    public AnchorPane dashBoardContext;
    public AreaChart lineChart;
    public AreaChart areaChart;
    ObservableList<Order> tmList = FXCollections.observableArrayList();

    public void initialize() {
        try {
            orders();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            items();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            sales();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        calculateTotal();
       /* String url = "jdbc:mysql://localhost:3306/Hardware";
        Connection connection= null;
        Statement stm = null;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Hardware","root","1234");
            stm = connection.createStatement();

            String sql ="SELECT oid,price FROM Orders";

            ResultSet result = stm.executeQuery(sql);
            ArrayList<String> id = new ArrayList<String>();
            ArrayList<Double> price = new ArrayList<Double>();

            while (result.next()){
                id.add(result.getString(1));
                price.add(result.getDouble(2));
            }
            result.close();

            CategoryAxis xAxis =  new CategoryAxis();
            xAxis.setLabel("id");

            NumberAxis yAxis = new NumberAxis();
            yAxis.setLabel("price");

            BarChart barChart = new BarChart(xAxis,yAxis);
            XYChart.Series dataSeries1 = new XYChart.Series();
            dataSeries1.setName("Order Details");


            for(int i=0;i<id.size();i++){
                dataSeries1.getData().add(new XYChart.Data(id.get(i),price.get(i)));
            }



            barChart.getData().add(dataSeries1);



        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }*/
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        //series.setName("Order Summary");

        series.getData().add(new XYChart.Data<>("P001", 6000));
        series.getData().add(new XYChart.Data<>("P002", 5000));
        series.getData().add(new XYChart.Data<>("P003", 4000));
        series.getData().add(new XYChart.Data<>("P004", 7000));

        barchart.getData().add(series);

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        //series.setName("Order Summary");

        series2.getData().add(new XYChart.Data<>("P001", 1000));
        series2.getData().add(new XYChart.Data<>("P002", 3000));
        series2.getData().add(new XYChart.Data<>("P003", 2000));
        series2.getData().add(new XYChart.Data<>("P004", 4000));
        series2.getData().add(new XYChart.Data<>("P005", 3000));


        areaChart.getData().add(series2);


    }

    private void orders() throws SQLException, ClassNotFoundException {
        ResultSet result = DBConnection.getInstance().
                getConnection().prepareStatement("SELECT COUNT(id) FROM Orders ").executeQuery();
        if (result.next()) {
            int customerCount = result.getInt(1);
            lblTotalOrders.setText(String.valueOf(customerCount));
        }
    }

    private void items() throws SQLException, ClassNotFoundException {
        ResultSet result = DBConnection.getInstance().
                getConnection().prepareStatement("SELECT SUM(qty) FROM OrderDetail").executeQuery();
        if (result.next()) {
            int customerCount = result.getInt(1);
            lblTotalItems.setText(String.valueOf(customerCount));
        }
    }

    private void sales() throws SQLException, ClassNotFoundException {
        ResultSet result = DBConnection.getInstance().
                getConnection().prepareStatement("SELECT SUM(total) FROM OrderDetail").executeQuery();
        if (result.next()) {
            int customerCount = result.getInt(1);
            lblTotalSales.setText(String.valueOf(customerCount));
        }
    }

    private void calculateTotal() {
        double total = 0;


        //   lblSales.setText(String.valueOf(total));


    }

    public void btnSignOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignInForm");
    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage) dashBoardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
    }

}
