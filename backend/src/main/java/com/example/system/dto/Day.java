package com.example.system.dto;

public class Day {
    private String name;
    private String classroom;
    private String principal;
    private String color;

    public Day(String name, String classroom, String principal, String color) {
        this.name = name;
        this.classroom = classroom;
        this.principal = principal;
        this.color = color;
    }

    public Day() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
