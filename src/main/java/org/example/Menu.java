package org.example;

import lombok.Data;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private int weight;
    private boolean discount;

    public Menu() {}

    public Menu(int id, String name, double price, int weight, boolean discount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }
}
