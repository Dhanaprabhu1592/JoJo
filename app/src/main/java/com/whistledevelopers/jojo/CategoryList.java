package com.whistledevelopers.jojo;

public class CategoryList {
    String name;
    String area;
    String number;
    String experience;

    public CategoryList(String name, String area, String number, String experience) {
        this.name = name;
        this.area = area;
        this.number = number;
        this.experience = experience;


    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        area = area;
    }


}
