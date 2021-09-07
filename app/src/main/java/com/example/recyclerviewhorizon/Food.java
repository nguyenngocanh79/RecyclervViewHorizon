package com.example.recyclerviewhorizon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Food {
    private int image;
    private String name, address;
    private ArrayList<String> tag;
    private boolean star;

    public Food(int image, String name, String address, ArrayList<String> tag, boolean star) {
        this.image = image;
        this.name = name;
        this.address = address;
        this.tag = tag;
        this.star = star;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    public ArrayList<String> getTag() {
        return tag;
    }

    public void setTag(ArrayList<String> tag) {
        this.tag = tag;
    }

    public boolean isStar() {
        return star;
    }

    public void setStar(boolean star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "Food{" +
                "image=" + image +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tag=" + tag +
                ", star=" + star +
                '}';
    }

    public static List<Food> getMock(){
        return new ArrayList<>(Arrays.asList(
                new Food(R.drawable.circlek,"Circle K","Address CirCle K",
                        new ArrayList<>(Arrays.asList("Shop","Dessert")), false),
                new Food(R.drawable.cotuyen,"Cô Tuyến","Address Cô Tuyến",
                        new ArrayList<>(Arrays.asList("Shop")), true)


        ));
    }
}