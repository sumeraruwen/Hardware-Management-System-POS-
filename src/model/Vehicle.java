package model;

public class Vehicle {
     private String id;
     private String number;
     private String type;
     private Double weight;

    public Vehicle() {
    }

    public Vehicle(String id, String number, String type, Double weight) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.weight = weight;
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

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", weight=" + weight +
                '}';
    }
}
