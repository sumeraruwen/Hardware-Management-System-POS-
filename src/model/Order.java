package model;

public class Order {
  private String oid;
  private String code;
  private String date;
  private double total;
  private int qty;
  private String vid;
  private String did;

    public Order() {
    }

    public Order(String oid, String code, String date, double total, int qty, String vid, String did) {
        this.oid = oid;
        this.code = code;
        this.date = date;
        this.total = total;
        this.qty = qty;
        this.vid = vid;
        this.did = did;
    }

    public String getId() {
        return oid;
    }

    public void setId(String id) {
        this.oid = oid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", total=" + total +
                ", qty=" + qty +
                ", vid='" + vid + '\'' +
                ", did='" + did + '\'' +
                '}';
    }
}
