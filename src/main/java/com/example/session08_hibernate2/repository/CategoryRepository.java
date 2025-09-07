package com.example.session08_hibernate2.repository;

import com.example.session08_hibernate2.model.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class CategoryRepository {
    @Autowired
    private SessionFactory sessionFactory;
    public List<Category> getAllCategories(int offset, int limit, String searchName) {
        Session session = sessionFactory.openSession();
        List<Category> categories = session.createQuery("FROM Category c WHERE (:search is null or c.cateName LIKE :search )", Category.class)
                .setParameter("search","%"+searchName+"%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        session.close();
        return categories;
    }

    public long countTotalElement(String search){
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("select count(c) from Category c where (:search is null or c.cateName like :search)",Long.class)
                    .setParameter("search","%"+search+"%")
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean saveCategory(Category category) {
        Session session = sessionFactory.openSession();
        try {
            session.saveOrUpdate(category);
            session.close();
            return true;
        }catch (Exception e) {
            session.close();
            return false;
        }
    }


    public boolean updateCategory(Category category) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(category);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            return false;
        }finally {
            session.close();
        }
    }



    public Category getCategoryById(long id) {
        Session session = sessionFactory.openSession();
        try {
            Category category = session.get(Category.class, id);
            session.close();
            return category;
        }catch (Exception e) {
            session.close();
            return null;
        }
    }

    public boolean deleteCategoryById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Category category = getCategoryById(id);
            if(category != null) {
                session.delete(category);
                transaction.commit();
                return true;
            }else {
                transaction.rollback();
                return false;
            }
        }catch (Exception e) {
            return false;
        }finally {
            session.close();
        }
    }
}
