package controller;

import model.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemCrudController {
    public static ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT icode FROM Item");
        ArrayList<String> ids = new ArrayList<>();

        while (result.next()) {
            ids.add(result.getString(1));

        }
        return ids;
    }

    public static Item getItem(String id) throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM Item WHERE icode=?", id);
        if (result.next()) {
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getDouble(5)


            );
        }
        return null;

    }


}
