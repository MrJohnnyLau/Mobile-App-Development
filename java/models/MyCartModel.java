package com.example.assignment_workshop_app.models;

public class MyCartModel {

    String currentTime;
    String currentDate;
    String workshopName;
    String workshopPrice;
    String totalQuantity;
    int totalPrice;

    public MyCartModel () {

    }

    public MyCartModel(String currentTime, String currentDate, String workshopName, String workshopPrice, String totalQuantity, int totalPrice) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.workshopName = workshopName;
        this.workshopPrice = workshopPrice;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getWorkshopPrice() {
        return workshopPrice;
    }

    public void setWorkshopPrice(String workshopPrice) {
        this.workshopPrice = workshopPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
