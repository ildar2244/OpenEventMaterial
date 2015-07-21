package ru.javaapp.openeventmaterial.dao;

import android.graphics.Bitmap;

/**
 * Created by User on 17.07.2015.
 */
public class Events {

    private int id;
    private int categoryId;
    private int coastId;
    private int managerId;
    private int placeId;
    private int blocked;

    private Bitmap image;

    private String name;
    private String date;
    private String time;
    private String description;
    private String coastLink;
    private String address;

    public Events() {}

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getCoastLink() {
        return coastLink;
    }

    public void setCoastLink(String coastLink) {
        this.coastLink = coastLink;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int isBlocked() {
        return blocked;
    }

    public void setBlocked(int blocked) {
        this.blocked = blocked;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCoastId() {
        return coastId;
    }

    public void setCoastId(int coastId) {
        this.coastId = coastId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

}
