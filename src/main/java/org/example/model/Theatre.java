package org.example.model;

public class Theatre {
    private Long id;
    private String name;
    private String location;

    public Theatre() {

    }

    public Theatre(Long id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public Theatre(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Theatre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
