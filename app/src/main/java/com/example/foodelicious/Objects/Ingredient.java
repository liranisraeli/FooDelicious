package com.example.foodelicious.Objects;

public class Ingredient {

    private String name;
    private int amount;

    public Ingredient() {
        this.name = "";
        this.amount = 0;
    }

    public String getName() {
        return name;
    }

    public Ingredient setName(String name) {
        this.name = name;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public Ingredient setAmount(int amount) {
        this.amount = amount;
        return this;
    }
    public void addAmount(int amount) {
        this.amount += amount;
    }
}
