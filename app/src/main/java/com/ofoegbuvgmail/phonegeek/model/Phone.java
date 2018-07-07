package com.ofoegbuvgmail.phonegeek.model;

import java.util.List;

public class Phone {

    private String RAM;
    private String color;
    private String imageUrl;
    private String jumiaLink;
    private String model;
    private String price;
    private List<String> specifications;
    private String review;

    public Phone() {
    }


    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getJumiaLink() {
        return jumiaLink;
    }

    public void setJumiaLink(String jumiaLink) {
        this.jumiaLink = jumiaLink;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<String> specifications) {
        this.specifications = specifications;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
