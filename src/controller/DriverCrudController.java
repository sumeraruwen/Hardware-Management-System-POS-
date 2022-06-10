package controller;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DriverCrudController {
    public static ArrayList<String> getDriverIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT did FROM Driver");
        ArrayList<String> dids = new ArrayList<>();

        while (result.next()) {
            dids.add(result.getString(1));

        }
        return dids;
    }


}
