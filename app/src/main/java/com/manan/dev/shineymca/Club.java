package com.manan.dev.shineymca;

public class Club {
    public Club(String name, String image) {
        this.name = name;
        this.image = image;
    }

    String name;
    String image;

    public Club() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
