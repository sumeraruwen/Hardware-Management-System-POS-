package model;

import javafx.scene.control.Button;

public class Driver {
    private String id;
    private String name;
    private String address;
  private String license;
    private String contact;

    public Driver() {
    }

    public Driver(String id, String name, String address, String license, String contact) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.license = license;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", license='" + license + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}