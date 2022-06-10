package model;

public class OrderDetails {
     private String id;
     private String icode;
     private String description;
      private int qty;
      private double total;

     public OrderDetails() {
     }

     public OrderDetails(String id, String icode, String description, int qty, double total) {
          this.id = id;
          this.icode = icode;
          this.description = description;
          this.qty = qty;
          this.total = total;
     }

     public String getOid() {
          return id;
     }

     public void setOid(String oid) {
          this.id = oid;
     }

     public String getIcode() {
          return icode;
     }

     public void setIcode(String icode) {
          this.icode = icode;
     }

     public String getDescription() {
          return description;
     }

     public void setDescription(String description) {
          this.description = description;
     }

     public int getQty() {
          return qty;
     }

     public void setQty(int qty) {
          this.qty = qty;
     }

     public double getTotal() {
          return total;
     }

     public void setTotal(double total) {
          this.total = total;
     }

     @Override
     public String toString() {
          return "OrderDetails{" +
                  "id='" + id + '\'' +
                  ", icode='" + icode + '\'' +
                  ", description='" + description + '\'' +
                  ", qty=" + qty +
                  ", total=" + total +
                  '}';
     }
}
