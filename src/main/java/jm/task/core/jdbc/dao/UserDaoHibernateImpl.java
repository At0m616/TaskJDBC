package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String qr = "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL, name VARCHAR(20), lastName VARCHAR(20), age TINYINT);";
            session.createSQLQuery(qr).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            Objects.requireNonNull(transaction).rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String sql = ("DROP TABLE IF EXISTS user");
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            Objects.requireNonNull(transaction).rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            Objects.requireNonNull(tx).rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = :Id")
                    .setParameter("Id", id)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            Objects.requireNonNull(tx).rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tx = null;
        List<User> users = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Query<User> query = session.createQuery("From User");
            users = query.list();
            tx.commit();
        } catch (Exception e) {
            Objects.requireNonNull(tx).rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tx = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createQuery("DELETE from User")
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            Objects.requireNonNull(tx).rollback();
            e.printStackTrace();
        }

    }
}
