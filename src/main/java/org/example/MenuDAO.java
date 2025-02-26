package org.example;

import java.util.List;

public interface MenuDAO {
    void addItem(Menu menu);
    List<Menu> getItems();
    List<Menu> getItemsByPrice(double from, double to);
    List<Menu> getItemsWithDiscount();
    List<Menu> getItemsMaxWeightKg();
}
