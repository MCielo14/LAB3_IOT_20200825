package com.example.lab3_20200825;


import com.google.gson.annotations.SerializedName;

public class PrimeNumber {
    @SerializedName("number")
    private int number;

    @SerializedName("order")
    private int order;

    // Getters y setters
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}


