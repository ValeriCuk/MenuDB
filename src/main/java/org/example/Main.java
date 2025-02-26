package org.example;

import java.util.Scanner;

public class Main {

    private static final MenuService ms = new MenuService();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            while (true) {
                System.out.println("1: add item to menu");
                System.out.println("2: show menu");
                System.out.println("3: show with price range");
                System.out.println("4: show with discount");
                System.out.println("5: place an order (< 1kg)");
                System.out.print("-> ");

                String s = sc.nextLine();
                switch (s) {
                    case "1":
                        ms.addItem();
                        break;
                    case "2":
                        ms.showMenu();
                        break;
                    case "3":
                        ms.showWithPriceRange();
                        break;
                    case "4":
                        ms.showWithDiscount();
                        break;
                    case "5":
                        ms.placeOrder();
                        break;
                    default:
                        return;
                }
            }
        }finally {
            MenuUtil.getEntityManager().close();
            MenuUtil.shutdown();
        }

    }
}