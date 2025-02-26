package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MenuUtil {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("MenuDBUnit");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void shutdown() {
        if (emf != null) {
            emf.close();
        }
    }
}
