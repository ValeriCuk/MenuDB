package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class MenuDAOImpl implements MenuDAO {

    private final EntityManager em;

    public MenuDAOImpl() {
        this.em = MenuUtil.getEntityManager();
    }

    @Override
    public void addItem(Menu menu) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(menu);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Menu> getItems() {
        try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
            Root<Menu> root = cq.from(Menu.class);
            cq.select(root);

            List<Menu> menuList = em.createQuery(cq).getResultList();
            return menuList;
        }catch (RuntimeException e){
            throw e;
        }
    }

    @Override
    public List<Menu> getItemsByPrice(double from, double to) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
            Root<Menu> root = cq.from(Menu.class);

            Predicate pricePredicate = cb.between(root.get("price"), from, to);
            cq.select(root).where(pricePredicate);

            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Menu> getItemsWithDiscount() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
            Root<Menu> root = cq.from(Menu.class);

            Predicate discountPredicate = cb.isTrue(root.get("discount"));
            cq.select(root).where(discountPredicate);
            return em.createQuery(cq).getResultList();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Menu> getItemsMaxWeightKg() {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Menu> cq = cb.createQuery(Menu.class);
            Root<Menu> root = cq.from(Menu.class);

            Predicate weightPredicate = cb.lessThanOrEqualTo(root.get("weight"), 1000);
            cq.select(root).where(weightPredicate);
            return em.createQuery(cq).getResultList();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
