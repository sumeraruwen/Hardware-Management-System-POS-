package controller;


import model.Order;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
    public static String getOrderId() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        if (result.next()) {

            return result.getString(1);
                       /* int tempId = Integer.parseInt(result.getString(1).split("-")[1]);
                        tempId+=1;
                        if(tempId<1000){
                                return "O-0"+tempId;
                        }else{
                                return "O-"+tempId;
                        }*/


        }
        return "K001";
    }

    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Orders VALUES(?,?,?,?,?,?,?)", order.getId(),
                order.getCode(), order.getDate(), order.getTotal(), order.getQty(), order.getVid(), order.getDid());
    }

    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det : details) {
            boolean isDetailsSaved = CrudUtil.execute("INSERT INTO OrderDetail VALUES(?,?,?,?,?)",
                    det.getOid(), det.getIcode(), det.getDescription(), det.getQty(), det.getTotal());

            if (isDetailsSaved) {
                if (!updateQty(det.getIcode(), det.getQty())) {
                    return false;
                }
            } else {
                return false;
            }

        }
        return true;
    }

    private boolean updateQty(String icode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE ITEM SET qtyhand=qtyhand-? WHERE icode=?", qty, icode);

    }


}
