package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuService {

    private final MenuDAO menuDAO;
    private final Scanner scanner = new Scanner(System.in);

    public MenuService() {
        menuDAO = new MenuDAOImpl();
    }

    public void addItem(){
        Menu menu = new Menu();
        System.out.println("Enter the name of the item: ");
        menu.setName(scanner.nextLine());
        menu.setPrice(getPrice());
        menu.setWeight(getWeight());
        menu.setDiscount(getDiscount());

        menuDAO.addItem(menu);
    }

    private double getPrice() {
        double price;
        while (true) {
            System.out.println("Enter the price of the item: ");
            String input = scanner.nextLine();
            if (input == null || input.trim().isEmpty()) {
                System.out.println("-> Invalid input!\n");
                continue;
            }
            String strPrice = input.trim().replace(',', '.');
            try {
                price = Double.parseDouble(strPrice);
                return price;
            } catch (NumberFormatException e) {
                System.out.println("-> Invalid input! " + strPrice + "\n");
            }
        }
    }

    private int getWeight() {
        int weight;
        while (true) {
            System.out.println("Enter the weight of the item (in grams): ");
            String input = scanner.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.println("-> Invalid input!\n");
                continue;
            }
            String strWeight = input.trim().replace(',', '.');
            try {
                weight = (int) (Double.parseDouble(strWeight));
                return weight;
            } catch (NumberFormatException e) {
                System.out.println("-> Invalid input! " + strWeight + "\n");
            }
        }
    }

    private boolean getDiscount(){
        System.out.println("Do you want to add a discount? (yes/no)");
        String answer = scanner.nextLine().trim().toLowerCase();
        return answer.equals("yes") || answer.equals("y");
    }

    public void showMenu(){
        printMenu(menuDAO.getItems());
    }

    public void showWithPriceRange(){
        double minPrice = -1;
        double maxPrice = -1;

        while (minPrice >= maxPrice || minPrice < 0 || maxPrice < 0) {
            System.out.println("Enter the minimum price of the item: ");
            minPrice = checkPrice();
            System.out.println("Enter the maximum price of the item: ");
            maxPrice = checkPrice();

            if (minPrice >= maxPrice) {
                System.out.println("-> Minimum price cannot be greater than or equal to maximum price. Please enter valid prices.");
            }
        }

        List<Menu> result =  menuDAO.getItemsByPrice(minPrice, maxPrice);
        printMenu(result);
    }

    private double checkPrice() {
        double price;
        while (true) {
            System.out.println("Price -> ");
            String input = scanner.nextLine();
            if (input == null || input.trim().isEmpty()) {
                System.out.println("-> Invalid input!\n");
                continue;
            }
            String strPrice = input.trim().replace(',', '.');
            try {
                price = Double.parseDouble(strPrice);
                return price;
            } catch (NumberFormatException e) {
                System.out.println("-> Invalid input! " + strPrice + "\n");
            }
        }
    }

    public void showWithDiscount(){
        List<Menu> result = menuDAO.getItemsWithDiscount();
        printMenu(result);
    }

    public void placeOrder(){
        List<Menu> listLessKg = menuDAO.getItemsMaxWeightKg();
        System.out.println("Available dishes with weight less than 1 kg:");
        printMenu(listLessKg);

        List<Menu> selectedItems = new ArrayList<>();
        double totalWeight = 0;

        while (totalWeight < 1000) { // 1000 г = 1 кг
            System.out.println("Enter the ID of the dish you want to add to the order (or type 'done' to finish):");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) {
                break;
            }

            try {
                long dishId = Long.parseLong(input);
                Menu selectedDish = findDishById(dishId, listLessKg);
                if (selectedDish != null) {
                    double dishWeight = selectedDish.getWeight();

                    if (totalWeight + dishWeight <= 1000) {
                        selectedItems.add(selectedDish);
                        totalWeight += dishWeight;
                        System.out.println("Dish added: " + selectedDish.getName() + ", Total weight: " + totalWeight + " grams.");
                    } else {
                        System.out.println("You can't add this dish. Total weight would exceed 1 kg.");
                    }
                } else {
                    System.out.println("Dish not found with ID: " + dishId);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format, please try again.");
            }
        }

        System.out.println("Your order:");
        printMenu(selectedItems);
    }

    private Menu findDishById(long dishId, List<Menu> list) {
        for (Menu menuItem : list) {
            if (menuItem.getId() == dishId) {
                return menuItem;
            }
        }
        return null;
    }

    private void printMenu(List<Menu> list){
        for (Menu item : list) {
            System.out.println("ID: " + item.getId() + ", Name: " + item.getName() + ", Weight: " + item.getWeight() + ", " + item.getPrice() + "$ " + (item.isDiscount() ? " discounted price" : " price without discount"));
        }
    }
}
