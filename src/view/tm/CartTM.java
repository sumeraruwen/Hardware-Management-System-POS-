package view.tm;

import javafx.scene.control.Button;


public class CartTM {
    private String code;
    private String description;
    private double price;
    private int qty;
    private String id;
    private double total;
    private Button btn;

    public CartTM() {
    }

    public CartTM(String code, String description, double price, int qty, String id, double total, Button btn) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.id = id;
        this.total = total;
        this.btn = btn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", id='" + id + '\'' +
                ", total=" + total +
                ", btn=" + btn +
                '}';
    }
}
