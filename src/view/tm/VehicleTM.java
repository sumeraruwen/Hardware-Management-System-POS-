package view.tm;

import javafx.scene.control.Button;

public class VehicleTM {
    private String id;
    private String number;
    private String type;
    private Double weight;
    private Button btn;

    public VehicleTM() {
    }

    public VehicleTM(String id, String number, String type, Double weight, Button btn) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.weight = weight;
        this.btn = btn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "VehicleTM{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", weight=" + weight +
                ", btn=" + btn +
                '}';
    }
}
