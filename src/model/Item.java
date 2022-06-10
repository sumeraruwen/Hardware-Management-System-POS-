package model;

public class Item {
    private String Code;
     private String id;
     private String description;
     private int qtyHand;
     private double price;

    public Item() {
    }

    public Item(String code, String id, String description, int qtyHand, double price) {
        Code = code;
        this.id = id;
        this.description = description;
        this.qtyHand = qtyHand;
        this.price = price;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyHand() {
        return qtyHand;
    }

    public void setQtyHand(int qtyHand) {
        this.qtyHand = qtyHand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "Code='" + Code + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", qtyHand=" + qtyHand +
                ", price=" + price +
                '}';
    }
}
