package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleCrudController {
    public static ArrayList<String> getVehicleIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT vid FROM Vehicle");
        ArrayList<String> vids = new ArrayList<>();

        while (result.next()) {
            vids.add(result.getString(1));

        }
        return vids;
    }


}
